package com.sequenceiq.cloudbreak.converter.v2.cli;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.model.FileSystemResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.adls.AdlsGen2FileSystem;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.adls.AdlsFileSystem;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.gcs.GcsFileSystem;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.s3.S3FileSystem;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.wasb.WasbFileSystem;
import com.sequenceiq.cloudbreak.api.model.v2.StorageLocationResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.adls.AdlsGen2CloudStorageParameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.adls.AdlsCloudStorageParameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.gcs.GcsCloudStorageParameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.s3.S3CloudStorageParameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.wasb.WasbCloudStorageParameters;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.cloudbreak.domain.FileSystem;
import com.sequenceiq.cloudbreak.domain.StorageLocation;
import com.sequenceiq.cloudbreak.domain.StorageLocations;

@Component
public class FileSystemToFileSystemResponseConverter extends AbstractConversionServiceAwareConverter<FileSystem, FileSystemResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemToFileSystemResponseConverter.class);

    @Override
    public FileSystemResponse convert(FileSystem source) {
        FileSystemResponse response = new FileSystemResponse();
        response.setId(source.getId());
        response.setName(source.getName());
        response.setDefaultFs(source.isDefaultFs());
        response.setLocations(getStorageLocationRequests(source));
        try {
            if (source.getType().isAdls()) {
                AdlsCloudStorageParameters adls = getConversionService()
                        .convert(source.getConfigurations().get(AdlsFileSystem.class), AdlsCloudStorageParameters.class);
                adls.setCredential(null);
                response.setAdls(adls);
            } else if (source.getType().isGcs()) {
                response.setGcs(getConversionService().convert(source.getConfigurations().get(GcsFileSystem.class), GcsCloudStorageParameters.class));
            } else if (source.getType().isS3()) {
                response.setS3(getConversionService().convert(source.getConfigurations().get(S3FileSystem.class), S3CloudStorageParameters.class));
            } else if (source.getType().isWasb()) {
                response.setWasb(getConversionService().convert(source.getConfigurations().get(WasbFileSystem.class), WasbCloudStorageParameters.class));
            } else if (source.getType().isAdlsGen2()) {
                response.setAdlsGen2(getConversionService().convert(source.getConfigurations().get(AdlsGen2FileSystem.class),
                        AdlsGen2CloudStorageParameters.class));
            }
        } catch (IOException ioe) {
            LOGGER.info("Something happened while we tried to obtain/convert file system", ioe);
        }
        response.setType(source.getType().name());
        return response;
    }

    private Set<StorageLocationResponse> getStorageLocationRequests(FileSystem source) {
        Set<StorageLocationResponse> locations = new HashSet<>();
        try {
            if (source.getLocations() != null && source.getLocations().getValue() != null) {
                StorageLocations storageLocations = source.getLocations().get(StorageLocations.class);
                if (storageLocations != null) {
                    for (StorageLocation storageLocation : storageLocations.getLocations()) {
                        locations.add(getConversionService().convert(storageLocation, StorageLocationResponse.class));
                    }
                }
            } else {
                locations = new HashSet<>();
            }
        } catch (IOException e) {
            locations = new HashSet<>();
        }
        return locations;
    }

}

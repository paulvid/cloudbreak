package com.sequenceiq.cloudbreak.converter.v2.filesystem;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.model.filesystem.S3FileSystem;
import com.sequenceiq.cloudbreak.api.model.v2.filesystem.S3CloudStorageParameters;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;

@Component
public class S3CloudStorageParametersToS3FileSystemConverter
        extends AbstractConversionServiceAwareConverter<S3CloudStorageParameters, S3FileSystem> {

    @Override
    public S3FileSystem convert(S3CloudStorageParameters source) {
        S3FileSystem fileSystemConfigurations = new S3FileSystem();
        fileSystemConfigurations.setInstanceProfile(source.getInstanceProfile());
        return fileSystemConfigurations;
    }
}

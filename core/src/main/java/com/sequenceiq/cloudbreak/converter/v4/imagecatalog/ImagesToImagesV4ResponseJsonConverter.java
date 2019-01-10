package com.sequenceiq.cloudbreak.converter.v4.imagecatalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.imagecatalog.responses.BaseImageV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.imagecatalog.responses.ImageV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.imagecatalog.responses.ImagesV4Response;
import com.sequenceiq.cloudbreak.api.model.imagecatalog.ManagementPackEntry;
import com.sequenceiq.cloudbreak.api.model.imagecatalog.StackDetailsJson;
import com.sequenceiq.cloudbreak.api.model.imagecatalog.StackRepoDetailsJson;
import com.sequenceiq.cloudbreak.cloud.model.AmbariRepo;
import com.sequenceiq.cloudbreak.cloud.model.catalog.Image;
import com.sequenceiq.cloudbreak.cloud.model.catalog.Images;
import com.sequenceiq.cloudbreak.cloud.model.catalog.StackDetails;
import com.sequenceiq.cloudbreak.cloud.model.catalog.StackRepoDetails;
import com.sequenceiq.cloudbreak.cloud.model.component.DefaultHDFEntries;
import com.sequenceiq.cloudbreak.cloud.model.component.DefaultHDPEntries;
import com.sequenceiq.cloudbreak.cloud.model.component.ManagementPackComponent;
import com.sequenceiq.cloudbreak.cloud.model.component.StackInfo;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.cloudbreak.service.DefaultAmbariRepoService;

@Component
public class ImagesToImagesV4ResponseJsonConverter extends AbstractConversionServiceAwareConverter<Images, ImagesV4Response> {

    @Inject
    private DefaultHDPEntries defaultHDPEntries;

    @Inject
    private DefaultHDFEntries defaultHDFEntries;

    @Inject
    private DefaultAmbariRepoService defaultAmbariRepoService;

    @Override
    public ImagesV4Response convert(Images source) {
        ImagesV4Response res = new ImagesV4Response();
        List<BaseImageV4Response> baseImages = getBaseImageResponses(source);
        res.setBaseImages(baseImages);

        List<ImageV4Response> hdpImages = new ArrayList<>();
        for (Image hdpImg : source.getHdpImages()) {
            ImageV4Response hdpImgJson = new ImageV4Response();
            copyImageFieldsToJson(hdpImg, hdpImgJson);
            hdpImgJson.setStackDetails(convertStackDetailsToJson(hdpImg.getStackDetails(), hdpImg.getOsType()));
            hdpImages.add(hdpImgJson);
        }
        res.setHdpImages(hdpImages);

        List<ImageV4Response> hdfImages = new ArrayList<>();
        for (Image hdfImg : source.getHdfImages()) {
            ImageV4Response hdfImgJson = new ImageV4Response();
            copyImageFieldsToJson(hdfImg, hdfImgJson);
            hdfImgJson.setStackDetails(convertStackDetailsToJson(hdfImg.getStackDetails(), hdfImg.getOsType()));
            hdfImages.add(hdfImgJson);
        }
        res.setHdfImages(hdfImages);
        res.setSupportedVersions(source.getSuppertedVersions());
        return res;
    }

    private List<BaseImageV4Response> getBaseImageResponses(Images source) {
        List<StackDetailsJson> defaultHdpStacks = getDefaultStackInfos(defaultHDPEntries.getEntries().values());
        List<StackDetailsJson> defaultHdfStacks = getDefaultStackInfos(defaultHDFEntries.getEntries().values());
        List<BaseImageV4Response> baseImages = source.getBaseImages().stream()
                .filter(image -> defaultAmbariRepoService.getDefault(image.getOsType()) != null)
                .map(image -> {
                    BaseImageV4Response imgJson = new BaseImageV4Response();
                    copyImageFieldsToJson(image, imgJson);
                    imgJson.setHdpStacks(defaultHdpStacks);
                    imgJson.setHdfStacks(defaultHdfStacks);
                    AmbariRepo ambariRepo = defaultAmbariRepoService.getDefault(image.getOsType());
                    imgJson.setVersion(ambariRepo.getVersion());
                    Map<String, String> repoJson = new HashMap<>();
                    repoJson.put("baseurl", ambariRepo.getBaseUrl());
                    repoJson.put("gpgkey", ambariRepo.getGpgKeyUrl());
                    imgJson.setRepo(repoJson);
                    return imgJson;
                })
                .collect(Collectors.toList());
        return baseImages;
    }

    private List<StackDetailsJson> getDefaultStackInfos(Iterable<? extends StackInfo> defaultStackInfos) {
        List<StackDetailsJson> result = new ArrayList<>();
        for (StackInfo info : defaultStackInfos) {
            StackDetailsJson json = new StackDetailsJson();
            StackRepoDetailsJson repoJson = new StackRepoDetailsJson();
            Map<String, String> stackRepo = info.getRepo().getStack();
            if (stackRepo != null) {
                repoJson.setStack(stackRepo);
            }
            Map<String, String> utilRepo = info.getRepo().getUtil();
            if (utilRepo != null) {
                repoJson.setUtil(utilRepo);
            }
            json.setRepo(repoJson);
            json.setVersion(info.getVersion());
            Map<String, List<ManagementPackEntry>> mpacks = new HashMap<>();
            for (Map.Entry<String, List<ManagementPackComponent>> mp : info.getRepo().getMpacks().entrySet()) {
                mpacks.put(mp.getKey(), mp.getValue().stream().map(mpack -> {
                    ManagementPackEntry mpackEntry = new ManagementPackEntry();
                    mpackEntry.setMpackUrl(mpack.getMpackUrl());
                    return mpackEntry;
                }).collect(Collectors.toList()));
            }
            json.setMpacks(mpacks);
            result.add(json);
        }
        return result;
    }

    private void copyImageFieldsToJson(Image source, ImageV4Response json) {
        json.setDate(source.getDate());
        json.setDescription(source.getDescription());
        json.setOs(source.getOs());
        json.setOsType(source.getOsType());
        json.setUuid(source.getUuid());
        json.setVersion(source.getVersion());
        json.setDefaultImage(source.isDefaultImage());
        json.setPackageVersions(source.getPackageVersions());
        if (source.getRepo() != null) {
            json.setRepo(new HashMap<>(source.getRepo()));
        } else {
            json.setRepo(new HashMap<>());
        }
        json.setImageSetsByProvider(new HashMap<>(source.getImageSetsByProvider()));
    }

    private StackDetailsJson convertStackDetailsToJson(StackDetails stackDetails, String osType) {
        StackDetailsJson json = new StackDetailsJson();
        json.setVersion(stackDetails.getVersion());
        json.setRepo(convertStackRepoDetailsToJson(stackDetails.getRepo()));
        Map<String, List<ManagementPackEntry>> mpacks = new HashMap<>();
        mpacks.put(osType, stackDetails.getMpackList().stream().map(mp -> {
            ManagementPackEntry mpackEntry = new ManagementPackEntry();
            mpackEntry.setMpackUrl(mp.getMpackUrl());
            return mpackEntry;
        }).collect(Collectors.toList()));
        json.setMpacks(mpacks);
        if (!stackDetails.getMpackList().isEmpty()) {
            // Backward compatibility for the previous version of UI
            json.getRepo().getStack().put(com.sequenceiq.cloudbreak.cloud.model.component.StackRepoDetails.MPACK_TAG,
                    stackDetails.getMpackList().get(0).getMpackUrl());
        }
        return json;
    }

    private StackRepoDetailsJson convertStackRepoDetailsToJson(StackRepoDetails repo) {
        StackRepoDetailsJson json = new StackRepoDetailsJson();
        json.setStack(new HashMap<>(repo.getStack()));
        json.setUtil(new HashMap<>(repo.getUtil()));
        return json;
    }
}

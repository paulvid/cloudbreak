package com.sequenceiq.cloudbreak.converter.spi;

import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.s3.S3FileSystem;
import com.sequenceiq.cloudbreak.cloud.model.filesystem.CloudS3View;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class S3FileSystemToCloudS3ViewConverterTest {

    private static final String TEST_INSTANCE_PROFILE = "i-123123rfa23";

    private S3FileSystemToCloudS3ViewConverter underTest;

    @Before
    public void setUp() {
        underTest = new S3FileSystemToCloudS3ViewConverter();
    }

    @Test
    public void testConvertWhenPassingS3FileSystemThenEveryNecessaryParametersShouldBePassed() {
        CloudS3View expected = new CloudS3View();
        expected.setInstanceProfile(TEST_INSTANCE_PROFILE);

        CloudS3View result = underTest.convert(createSource());

        assertEquals(expected, result);
    }

    private S3FileSystem createSource() {
        S3FileSystem s3FileSystem = new S3FileSystem();
        s3FileSystem.setInstanceProfile(TEST_INSTANCE_PROFILE);
        return s3FileSystem;
    }

}
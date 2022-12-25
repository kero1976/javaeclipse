package aws.s3;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import aws.TestBase;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class FileManagerTest extends TestBase {

    @Mock
    S3Client s3clientMock;

    @Mock
    ResponseBytes<GetObjectResponse> objectBytesMock;

    @Mock
    PutObjectResponse responseMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_get_ok_nomock() throws IOException {
        S3Client s3client = S3Client.builder().build();

        FileManager fileManager = new FileManager(s3client, "u10.jp");
        fileManager.get("index.html", tempFile.toPath());
        assertThat(tempFile.length()).isEqualTo(109);
    }

    @Test
    void test_get_ok_mock() throws IOException {
        when(s3clientMock.getObjectAsBytes((GetObjectRequest)any())).thenReturn(objectBytesMock);
        when(objectBytesMock.asByteArray()).thenReturn(new byte[] {0x41});

        FileManager fileManager = new FileManager(s3clientMock, "u10.jp");
        fileManager.get("index.html", tempFile.toPath());
        assertThat(tempFile.length()).isEqualTo(1);
    }

    @Test
    void test_get_ng_noFile_nomock() throws IOException {
        S3Client s3client = S3Client.builder().build();
        FileManager fileManager = new FileManager(s3client, "u10.jp");

        assertThatThrownBy(() -> {
            fileManager.get("index.html2", tempFile.toPath());
        }).isInstanceOfSatisfying(NoSuchKeyException.class, e -> {
            assertThat(e.getMessage()).isNotBlank();
        });
    }

    @Test
    void test_get_ng_noFile_mock() throws IOException {
        when(s3clientMock.getObjectAsBytes((GetObjectRequest)any())).thenThrow(NoSuchKeyException.builder().build());

        FileManager fileManager = new FileManager(s3clientMock, "u10.jp");

        assertThatThrownBy(() -> {
            fileManager.get("index.html2", tempFile.toPath());
        }).isInstanceOfSatisfying(NoSuchKeyException.class, e -> {
            assertThat(e.getMessage()).isBlank();
        });
    }

    @Test
    void test_put_ok_nomock() throws IOException, URISyntaxException {
        S3Client s3client = S3Client.builder().build();
        FileManager fileManager = new FileManager(s3client, "u10.jp");
        fileManager.put("log4j.xml", toPath("/log4j2.xml"));
    }

    @Test
    void test_put_ok_mock() throws IOException, URISyntaxException {
         when(s3clientMock.putObject((PutObjectRequest)any(), (RequestBody)any())).thenReturn(responseMock);

        FileManager fileManager = new FileManager(s3clientMock, "u10.jp");
        fileManager.put("log4j2a.xml", toPath("/log4j2.xml"));
    }
}

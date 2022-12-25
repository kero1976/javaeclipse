package aws.s3;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

public class FileManagerGetTest extends FileManagerTestBase {

	@Test
	void test_get_ok_nomock() throws IOException {
		new FileManager(s3client, BUCKET).get("index.html", tempFile.toPath());
		assertThat(tempFile.length()).isEqualTo(109);
	}

	@Test
	void test_get_ok_mock() throws IOException {
		when(s3clientMock.getObjectAsBytes((GetObjectRequest) any())).thenReturn(objectBytesMock);
		when(objectBytesMock.asByteArray()).thenReturn(new byte[] { 0x41 });
		
		new FileManager(s3clientMock, BUCKET).get("index.html", tempFile.toPath());
		assertThat(tempFile.length()).isEqualTo(1);
	}

	@Test
	void test_get_ng_noFile_nomock() throws IOException {
		assertThatThrownBy(() -> {
			new FileManager(s3client, BUCKET).get("index.html2", tempFile.toPath());
		}).isInstanceOfSatisfying(NoSuchKeyException.class, e -> {
			assertThat(e.getMessage()).isNotBlank();
		});
	}

	@Test
	void test_get_ng_noFile_mock() throws IOException {
		when(s3clientMock.getObjectAsBytes((GetObjectRequest) any())).thenThrow(NoSuchKeyException.builder().build());

		assertThatThrownBy(() -> {
			new FileManager(s3clientMock, BUCKET).get("index.html2", tempFile.toPath());
		}).isInstanceOfSatisfying(NoSuchKeyException.class, e -> {
			assertThat(e.getMessage()).isBlank();
		});
	}
	
	@Test
	void test_get_ng_ioerror_mock() throws IOException, URISyntaxException {
		when(s3clientMock.getObjectAsBytes((GetObjectRequest) any())).thenReturn(objectBytesMock);
		when(objectBytesMock.asByteArray()).thenReturn(new byte[] { 0x41 });

		assertThatThrownBy(() -> {
			new FileManager(s3clientMock, BUCKET).get("index.html2", Paths.get(""));
		}).isInstanceOfSatisfying(IOException.class, e -> {
			assertThat(e.getMessage()).isBlank();
		});
	}
}

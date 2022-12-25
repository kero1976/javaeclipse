package aws.s3;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class FileManagerPutTest extends FileManagerTestBase {

	@Test
	void test_put_ok_nomock() throws IOException, URISyntaxException {
		new FileManager(s3client, BUCKET).put("log4j.xml", toPath("/log4j2.xml"));
	}

	@Test
	void test_put_ok_mock() throws IOException, URISyntaxException {
		when(s3clientMock.putObject((PutObjectRequest) any(), (RequestBody) any())).thenReturn(responseMock);

		new FileManager(s3clientMock, BUCKET).put("log4j2a.xml", toPath("/log4j2.xml"));
	}

	@Test
	void test_put_ng_mock() throws IOException, URISyntaxException {
		when(s3clientMock.putObject((PutObjectRequest) any(), (RequestBody) any()))
				.thenThrow(NoSuchKeyException.builder().build());

		assertThatThrownBy(() -> {
			new FileManager(s3clientMock, BUCKET).put("log4j2a.xml", toPath("/log4j2.xml"));
		}).isInstanceOfSatisfying(NoSuchKeyException.class, e -> {
			assertThat(e.getMessage()).isBlank();
		});
	}

	@Test
	void test_put_ng_ioerror_mock() throws IOException, URISyntaxException {
		when(s3clientMock.putObject((PutObjectRequest) any(), (RequestBody) any())).thenReturn(responseMock);

		assertThatThrownBy(() -> {
			 new FileManager(s3clientMock, BUCKET).put("log4j2a.xml", Paths.get(""));
		}).isInstanceOfSatisfying(IOException.class, e -> {
			assertThat(e.getMessage()).isBlank();
		});
	}
}

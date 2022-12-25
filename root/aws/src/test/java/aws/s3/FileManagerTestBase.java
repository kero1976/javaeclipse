package aws.s3;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import aws.TestBase;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

abstract class FileManagerTestBase extends TestBase {

	@Mock
	S3Client s3clientMock;

	@Mock
	ResponseBytes<GetObjectResponse> objectBytesMock;

	@Mock
	PutObjectResponse responseMock;

	protected static final String BUCKET = "u10.jp";
	
	protected S3Client s3client = S3Client.builder().build();
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

}

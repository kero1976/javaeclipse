package aws.s3;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

public class CoreTest {

    @Test
    void test_get_ok() {
        S3Client s3 = S3Client.builder().build();
        Core core = new Core(s3, "u10.jp");
        byte[] data = core.get("index.html");
        assertThat(data).isNotEmpty();
    }

    @Test
    void test_get_ng() {
        S3Client s3 = S3Client.builder().build();
        Core core = new Core(s3, "u10.jp");

        assertThatThrownBy(() -> {
            core.get("index.html2");
        }).isInstanceOfSatisfying(NoSuchKeyException.class, e -> {
            assertThat(e.getMessage()).isNotBlank();
        });
    }

    @Test
    void test_put_ok() {
        S3Client s3 = S3Client.builder().build();
        Core core = new Core(s3, "u10.jp");
        byte[] data = new byte[] {0x41,0x42};
        core.put("abc", data);

    }
}

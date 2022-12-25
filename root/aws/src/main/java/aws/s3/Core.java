package aws.s3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

/**
 * S3からオブジェクトをバイナリでGET/PUTする.
 * @author kero
 *
 */
public class Core {

    private static final Logger LOG = LogManager.getLogger(Core.class);

    private S3Client client;
    private String bucketName;

    public Core(S3Client client, String bucketName) {
        this.client = client;
        this.bucketName = bucketName;
    }

    /**
     * データの取得.
     * @param keyName キー
     * @return
     */
    public byte[] get(String keyName) {
        LOG.debug(String.format("START (keyname:%s)", keyName));
        try {
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = client.getObjectAsBytes(objectRequest);
            byte[] result =  objectBytes.asByteArray();
            LOG.debug(String.format("SUCCESS (size:%d)", result.length));
            return result;
        }catch(Exception e) {
            String msg = String.format("FAIL %s", e.toString());
            LOG.debug(msg, e);
            throw e;
        }
    }

    public void put(String keyName, byte[] data) {
        LOG.debug(String.format("START (keyname:%s, datasize:%d)", keyName, data.length));
        try {
            PutObjectRequest  objectRequest = PutObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();

            PutObjectResponse response = client.putObject(objectRequest, RequestBody.fromBytes(data));

            LOG.debug(String.format("SUCCESS %s", response.eTag()));

        }catch(Exception e) {
            String msg = String.format("FAIL %s", e.toString());
            LOG.debug(msg, e);
            throw e;
        }
    }
}

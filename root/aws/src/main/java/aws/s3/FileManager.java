package aws.s3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.services.s3.S3Client;

public class FileManager {

    private static final Logger LOG = LogManager.getLogger(FileManager.class);

    Core core;

    public FileManager(S3Client client, String bucketName) {
        core = new Core(client, bucketName);
    }

    public void get(String key, Path localfile) throws IOException {
        LOG.debug(String.format("START (key:%s, localfile:%s)", key, localfile));
        try {
            byte[] data = core.get(key);
            Files.write(localfile, data);
            LOG.debug("SUCCESS");
        }catch(IOException e) {
            String msg = String.format("FAIL %s", e.toString());
            LOG.debug(msg, e);
            throw e;
        }
    }

    public void put(String key, Path localfile) throws IOException {
        LOG.debug(String.format("START (key:%s, localfile:%s)", key, localfile));
        try {
            byte[] data = Files.readAllBytes(localfile);
            core.put(key,data);
            LOG.debug("SUCCESS");
        }catch(IOException e) {
            String msg = String.format("FAIL %s", e.toString());
            LOG.debug(msg, e);
            throw e;
        }
    }
}

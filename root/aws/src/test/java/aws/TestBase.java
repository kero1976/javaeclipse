package aws;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public abstract class TestBase {

    protected File tempFile;

    /**
     * 以下の名前でtempディレクトリ以下に一時ファイルを作成する.
     * {テストクラス名}ランダム文字(テストメソッド名}
     * @param info
     * @throws IOException
     */
    @BeforeEach
    void setup(TestInfo info) throws IOException {
        String className = info.getTestClass().get().getSimpleName();
        String testName = info.getDisplayName();
        tempFile = File.createTempFile(className, testName);
    }

    /**
     * 作成した一時ファイルを削除する.
     */
    @AfterEach
    void cleanup() {
        tempFile.deleteOnExit();
    }

    public Path toPath(String testFilePath) throws URISyntaxException {
        return Paths.get(getClass().getResource(testFilePath).toURI());
    }
}

package aws;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    /**
     * リソースファイルのパスを取得する.
     * @param testFilePath
     * @return
     * @throws URISyntaxException
     */
    public Path toResourcePath(String testFilePath) throws URISyntaxException {
        return Paths.get(getClass().getResource(testFilePath).toURI());
    }
    
    /**
     * 一時ファイルに文字列を追加.
     * @param s
     */
    protected void addText(String s) {
		try(BufferedWriter bw = Files.newBufferedWriter(tempFile.toPath(), StandardCharsets.UTF_8)){
			bw.write(s);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 一時ファイルにバイナリデータを追加.
     * @param b
     */
    protected void addByte(byte[] b) {
		try(FileOutputStream fos = new FileOutputStream(tempFile)){
			fos.write(b);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    protected byte[] readBytes() throws IOException {
    	return Files.readAllBytes(tempFile.toPath());
    }
    
    /**
     * 一時ファイルの内容をバイナリで表示.
     * @throws IOException 
     */
    protected void printBytes() throws IOException {
    	byte[] data = readBytes();
    	StringBuilder buff = new StringBuilder();
    	for(byte b: data) {
    		buff.append(String.format("%02x ", b));
    	}
    	System.out.println(buff.toString());
    }
}

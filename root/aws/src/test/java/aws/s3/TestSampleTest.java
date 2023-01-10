package aws.s3;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import aws.TestBase;

/**
 * テストデータの作成の確認用
 * テンポラリにファイルを作っているので、それに文字データをセットする。
 * @author kero
 *
 */
public class TestSampleTest  extends TestBase{

	@Test
	void test_1() {
		System.out.println("test1開始時" + tempFile.length());
		addByte("a".getBytes());
		System.out.println("test1終了時" + tempFile.length());
	}
	
	@Test
	void test_2() throws IOException {
		System.out.println("test2開始時" + tempFile.length());
		addByte("bb".getBytes());
		System.out.println("test2終了時" + tempFile.length());
		System.out.println(tempFile.toString());
		byte[] data = readBytes();
		System.out.println(Arrays.toString(data));
	}
	

}

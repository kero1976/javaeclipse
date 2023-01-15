package aws.s3;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

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

		File f1 = new File("A");
		File f2 = new File("B");
		File f3 = new File("A");
		assertThat(f1).isEqualTo(f3);
		System.out.println(f1);
		System.out.println(f2);
		System.out.println(f3);
	}

	
	
}

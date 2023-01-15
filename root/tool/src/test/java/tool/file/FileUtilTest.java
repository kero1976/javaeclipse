package tool.file;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class FileUtilTest {

	@Test
	void test() throws IOException {
		File input = File.createTempFile("input", "");
		FileUtil.addText(input.toPath(), "入力ファイル");
		File output = File.createTempFile("output", "");
		FileUtil.addText(output.toPath(), "出力ファイル");
		FileUtil.appendFile(input.toPath(), output.toPath());
		assertThat(input).hasContent("入力ファイル");
		assertThat(output).hasContent("出力ファイル入力ファイル");
//		input.deleteOnExit();
//		output.deleteOnExit();
	}
}

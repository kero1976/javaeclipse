package tool.file;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

	public static void appendFile(Path input, Path output) throws IOException {
		try(InputStreamReader reader = new InputStreamReader(new FileInputStream(input.toFile()));
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output.toFile(), true))) {
			int data;
			while((data = reader.read()) != -1) {
				writer.write(data);
			}
		}
	}
	
	public static void addText(Path filePath, String s) throws IOException {
		try(BufferedWriter bw = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)){
			bw.write(s);
		}
	}
}

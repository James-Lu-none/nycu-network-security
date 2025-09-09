package nycu.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class fileOp {
	public static byte[] readFile(File file) throws IOException {
		byte[] data;
		
		data = new byte[(int)file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(data);
		fis.close();
		return data;
	}
	
	public static void writeFile(byte[] data, String fileName) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(data);
		fos.close();
	}
}

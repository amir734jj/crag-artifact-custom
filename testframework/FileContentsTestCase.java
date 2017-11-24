package testframework;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

public abstract class FileContentsTestCase extends TestCase {
  public static void assertFileContents(File file, String result) {
    assertFileContents("", file, result);
  }
  
  public static void assertFileContents(String msg, File file, String result) {
    try {
      FileInputStream input = new FileInputStream(file);
      byte buffer[] = new byte[1024];
      StringBuffer s = new StringBuffer();
      try {
        while(input.available() != 0) {
          int index = input.read(buffer);
          s.append(new String(buffer, 0, index));
        }
      } catch (IOException e) {
        fail("Error reading " + file.getName());
      }
      assertEquals(msg, s.toString().replaceAll("( |\n|\t|\r)", ""), result.replaceAll("( |\n|\t|\r)", ""));
    } catch (FileNotFoundException e) {
      fail("File " + file.getName() + " not found");
    }
  }
}

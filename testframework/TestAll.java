package testframework;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.*;

import junit.framework.*;

public class TestAll {
  public TestAll() {
  }

  public static String testAllPath() {
    String[] paths = System.getProperty("java.class.path").split(
        File.pathSeparator);
    for (int i = 0; i < paths.length; i++) {
      String path = paths[i];
      File f = new File(path, "testframework/TestAll.class");
      if (f.exists()) {
        return path;
      }
    }
    return null;
  }

  public static Test suite(String... params) {
    TestSuite result = new TestSuite();
    String path = testAllPath();
    if (path != null) {
      addDirectory(result, new File(path), path.length() + 1, params);
    }
    return result;
  }

  public static void addDirectory(TestSuite suite, File directory,
      int splitIndex, String... params) {
    String as[] = directory.list();
    for (int i = 0; i < as.length; i++) {
      File candidate = new File(directory, as[i]);
      if (candidate.isFile()) {
        String s = candidate.getPath();
        if (s.endsWith(".class")) {
          String className = s.substring(splitIndex, s.length() - 6)
              .replace(File.separatorChar, '.');
          try {
            Class classCand = Class.forName(className);
            Class iter = classCand;
            while (iter != null
                && !iter.getName().equals(
                    "junit.framework.TestCase")) {
              iter = iter.getSuperclass();
            }
            if (iter != null) {
              if (!Modifier.isAbstract(classCand.getModifiers())) {
                if (params.length == 0 || Arrays.stream(params).anyMatch(p -> Pattern.compile(p, Pattern.CASE_INSENSITIVE).matcher(candidate.getName()).matches())) {
                  System.out.println("including: " + candidate.getName());
                  suite.addTestSuite(classCand); 
                }
              }
            }
          } catch (ClassNotFoundException e) {
          }
        }
      } else if (candidate.isDirectory()) {
        addDirectory(suite, candidate, splitIndex, params);
      }
    }
  }

  public static void main(String args[]) {
    if (args.length >= 1 && args[0].equals("-text")) {
      junit.textui.TestRunner.run(TestAll.suite(Arrays.copyOfRange(args, 1, args.length)));
    } else {
      junit.swingui.TestRunner.run(TestAll.class);
    }
  }
}

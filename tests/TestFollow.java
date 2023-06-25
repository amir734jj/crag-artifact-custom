package tests;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import testframework.FileContentsTestCase;

import junit.framework.TestCase;
/*
 * Created on 2004-sep-14
 */

/**
 * @author eva
 */
import AST.*;
public class TestFollow extends FileContentsTestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  
  public void testJavaFollow() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/grammar.cfg"));

      long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
      long startTime = System.nanoTime();

      CFG root = g.CFGrammar();
      StringBuffer s = new StringBuffer();
      for(int i = 0; i < root.getNumRule(); i++) {
        Rule r = root.getRule(i);
        s.append(r.getNDecl().getID() + ", Follow: ");
        // r.getNDecl().follow();
          TreeSet set = new TreeSet();
          set.addAll(r.getNDecl().follow());
          for (Iterator iter = set.iterator(); iter.hasNext();) {
          String element = (String) iter.next();
          s.append("\"" + element + "\" ");
        }
          s.append("\n");
      }
      long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
      long endTime = System.nanoTime();
      double duration = (endTime - startTime) / 1000000.0 / 1000.0;

      long actualMemUsed=afterUsedMem-beforeUsedMem;
      System.out.println("memory: " + actualMemUsed * 1e-6);
      System.out.println("time: " + duration);

      // assertFileContents(new File("TestGrammars/TestJavaGrammarFollowResult"), s.toString());
      // System.out.print(s.toString());
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }

}

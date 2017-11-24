package tests;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import testframework.FileContentsTestCase;

import junit.framework.TestCase;
/*
 * Created on 2004-sep-14
 */

/**
 * @author eva
 */
import AST.*;
public class TestNullable extends FileContentsTestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  
  public void testSimpleNullable() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestEmptyProd"));
      CFG root = g.CFGrammar();
      assertTrue("Nullable error in A", root.lookup("A").nullable());
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }
  
  public void testAppelNullable() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestAppelGrammar"));
      CFG root = g.CFGrammar();
      assertTrue("Nullable error in X", root.lookup("X").nullable());
      assertTrue("Nullable error in Y", root.lookup("Y").nullable());
      assertFalse("Nullable error in Z", root.lookup("Z").nullable());
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }
  
  public void testJavaNullable() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestJavaGrammarSorted"));
      CFG root = g.CFGrammar();
      StringBuffer s = new StringBuffer();
      for(int i = 0; i < root.getNumRule(); i++) {
        Rule r = root.getRule(i);
        s.append(r.getNDecl().getID() + ", nullable: " + r.getNDecl().nullable() + "\n");
      }
      assertFileContents(new File("TestGrammars/TestJavaGrammarNullableResult"), s.toString());
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
    
  }

}

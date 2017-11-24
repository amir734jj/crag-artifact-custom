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
public class TestParser extends FileContentsTestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  
  public void testParseSimple() {
    parseTest("TestGrammars/TestGrammarSimple");
  }
  
  public void testParseEmptyProd() {
    parseTest("TestGrammars/TestEmptyProd");
  }
  
  public void testParseAlternativeProd() {
    parseTest("TestGrammars/TestAlternativeProd");
  }
  
  public void testParseEmptyAlternativeProd() {
    parseTest("TestGrammars/TestEmptyAlternativeProd");
  }
  
  public void testParseAlternativeEmptyProd() {
    parseTest("TestGrammars/TestAlternativeEmptyProd");
  }
  
  public void testManyProds() {
    parseTest("TestGrammars/TestManyProds");
  }

  private void parseTest(String fileName) {
    try {
      CFGrammar g = new CFGrammar(new FileReader(new File(fileName)));
      CFG root = g.CFGrammar();
      assertFileContents("Parsed file and pretty print dont match", new File(fileName), root.pp());
      
    } catch (ParseException e) {
      fail("Could not parse file " + fileName);
    } catch (FileNotFoundException e) {
      fail("File " + fileName + " not found");
    }
  }

}

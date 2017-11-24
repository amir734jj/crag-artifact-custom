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
public class TestFirst extends FileContentsTestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  
  public void testSimpleFirst() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestEmptyProd"));
      CFG root = g.CFGrammar();
      assertTrue("First of A is not empty", root.lookup("A").first().isEmpty());
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }
  
  public void testAppelFirst() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestAppelGrammar"));
      CFG root = g.CFGrammar();
      Set ySet = new HashSet();
      ySet.add("c");
      Set xSet = new HashSet();
      xSet.add("a");
      xSet.add("c");
      Set zSet = new HashSet();
      zSet.add("a");
      zSet.add("c");
      zSet.add("d");
      assertTrue("First of X does not equal {a,c}", root.lookup("X").first().equals(xSet));
      assertTrue("First of Y does not equal {c}", root.lookup("Y").first().equals(ySet));
      assertTrue("First of Z does not equal {a,c,d}", root.lookup("Z").first().equals(zSet));
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }
  
  public void testFirstEmptyAlternatives() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestFirstEmptyAlternatives"));
      CFG root = g.CFGrammar();
      Set aSet = new HashSet();
      aSet.add("b");
      aSet.add("c");
      aSet.add("d");
      aSet.add("f");
      Set bSet = new HashSet();
      bSet.add("b");
      Set cSet = new HashSet();
      cSet.add("c");
      Set dSet = new HashSet();
      dSet.add("d");
      Set eSet = new HashSet();
      Set fSet = new HashSet();
      fSet.add("f");
      assertTrue("First of A does not equal {a,b, c,d,f}", root.lookup("A").first().equals(aSet));
      assertTrue("First of B does not equal {b}", root.lookup("B").first().equals(bSet));
      assertTrue("First of C does not equal {c}", root.lookup("C").first().equals(cSet));
      assertTrue("First of D does not equal {d}", root.lookup("D").first().equals(dSet));
      assertTrue("First of E does not equal { }", root.lookup("E").first().equals(eSet));
      assertTrue("First of F does not equal {f}", root.lookup("F").first().equals(fSet));
  
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }

  public void testJavaFirst() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestJavaGrammarSorted"));
      CFG root = g.CFGrammar();
      StringBuffer s = new StringBuffer();
      for(int i = 0; i < root.getNumRule(); i++) {
        Rule r = root.getRule(i);
        s.append(r.getNDecl().getID() + ", First: ");
          TreeSet set = new TreeSet();
          set.addAll(r.getNDecl().first());
          for (Iterator iter = set.iterator(); iter.hasNext();) {
          String element = (String) iter.next();
          s.append("\"" + element + "\" ");
        }
          s.append("\n");
      }
      assertFileContents(new File("TestGrammars/TestJavaGrammarFirstResult"), s.toString());
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }

}

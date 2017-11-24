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
  
  
  public void testSimpleFollow() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestEmptyProd"));
      CFG root = g.CFGrammar();
      assertTrue("Follow of A is not empty", root.lookup("A").follow().isEmpty());
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }
  
  
  
  public void testAppelFollow() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestAppelGrammar"));
      CFG root = g.CFGrammar();
      Set emptySet = new HashSet();
      Set nonEmptySet = new HashSet();
      nonEmptySet.add("a");
      nonEmptySet.add("d");
      nonEmptySet.add("c");
      assertTrue("Follow of X does not equal {a,c,d}", root.lookup("X").follow().equals(nonEmptySet));
      assertTrue("Follow of Y does not equal {a,c,d}", root.lookup("Y").follow().equals(nonEmptySet));
      assertTrue("Follow of Z does not equal {}", root.lookup("Z").follow().equals(emptySet));
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }
  
  public void testAppelCollectUses() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestAppelGrammar"));
      CFG root = g.CFGrammar();
      assertTrue(root.lookup("X").occurences().size()==1);
      assertTrue(root.lookup("Y").occurences().size()==2);
      assertTrue(root.lookup("Z").occurences().size()==1);      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }
  public void testAppelsuffix() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestAppelGrammar"));
      CFG root = g.CFGrammar();
      NUse x = (NUse) root.lookup("X").occurences().iterator().next();
      assertFalse(x.nullableSuffix());
      Set xFirstSuffix = new HashSet();
      xFirstSuffix.add("c");
      xFirstSuffix.add("d");
      xFirstSuffix.add("a");
      assertTrue(x.firstSuffix().equals(xFirstSuffix));
      NUse z = (NUse) root.lookup("Z").occurences().iterator().next();
      assertTrue(z.nullableSuffix());
      assertTrue(z.firstSuffix().isEmpty());
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }

  public void testJavaFollow() {
    try {
      CFGrammar g = new CFGrammar(new FileReader("TestGrammars/TestJavaGrammarSorted"));
      CFG root = g.CFGrammar();
      StringBuffer s = new StringBuffer();
      for(int i = 0; i < root.getNumRule(); i++) {
        Rule r = root.getRule(i);
        s.append(r.getNDecl().getID() + ", Follow: ");
          TreeSet set = new TreeSet();
          set.addAll(r.getNDecl().follow());
          for (Iterator iter = set.iterator(); iter.hasNext();) {
          String element = (String) iter.next();
          s.append("\"" + element + "\" ");
        }
          s.append("\n");
      }
      assertFileContents(new File("TestGrammars/TestJavaGrammarFollowResult"), s.toString());
      
    } catch (ParseException e) {
      fail("Could not parse file ");
    } catch (FileNotFoundException e) {
      fail("file not found");
    }
  }

}

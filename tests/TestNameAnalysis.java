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
public class TestNameAnalysis extends FileContentsTestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  
  public void testDecl() {
    NUse useA = new NUse("A");
    NUse useB = new NUse("B");
    NDecl declA = new NDecl("A");
    CFG root = new CFG(
      new List().add(
        new Rule(
          declA,
          new List().add(
            new Prod(
              new List().add(useA).add(useB)
            )
          )
        )
      )
    );
    assertTrue(useA.decl() == declA);
    assertTrue(useB.decl() == null);
  }

}

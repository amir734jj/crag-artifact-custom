# CRAG artifact #

This artifact supports the following article on Circular Reference Attribute Grammars:

* Eva Magnusson and Görel Hedin: [Circular Reference Attributed Grammars - their Evaluation and Applications](https://doi.org/10.1016/j.scico.2005.06.005), SCP, 68:1(21-37), Aug 2007.

The artifact demonstrates how circular reference attributes can be used to compute Nullable, First, and Follow for context-free grammars.

## System requirements ##
You only need to have Java and Ant installed.

Other tools are bundled with the project (JavaCC, JastAdd, JUnit).

## Build and run ##

Build:

    ant

Run test cases:

    ant test

Clean up (remove generated files):

    ant clean

## The example explained ##

A language `CFG` is defined that allows context-free grammars to be written on Backus-Naur Form. JastAdd aspects with circular attributes are used for defining the following properties:

* `nullable()`: *true* if a nonterminal can derive the empty string.
* `first()`: the set of terminal symbols that derivations of a nonterminal can start with
* `follow()`: the set of terminal symbols that can follow a nonterminal in a derivation

The properties `nullable()`, `first()`, and `follow()` can be defined using equations that are mutually recursive. For example, the value of `nullable()` for a nonterminal depends on the value of `nullable()` for other nonterminals, possibly including itself. Similarly, `first()` and `follow()` are recursively defined. Additionally, `first()` depends on `nullable()`, and `follow()` depends on both `first()` and `nullable()`.

The mutually recursive equations can be mapped in a straight-forward way to circular attributes.

The potential values of a circular attribute must be arranged in a lattice of finite height, and all equations must be monotonic. I.e., a new approximation, computed using a given approximation, must always be at the same or a higher level in the lattice. The computation can then be performed with a terminating iteration.

The `nullable()` attribute makes use of a boolean lattice with *FALSE* at the bottom and *TRUE* at the top, and *OR* as the monotonic operation.

The `first()` and `follow()` attributes make use of set lattices with the *empty* set at the bottom and the set of *all terminal symbols* in the grammar at the top, and *UNION* as the monotonic operation.

The implementation also makes use of a reference attribute `decl()` that binds a use of a nonterminal (an `NUse`) to the appropriate declared nonterminal (an `NDecl`). An `NDecl` occurs on the left-hand side of a grammar rule, and an `NUse` occurs on the right-hand side.

## Files to look at ##

### Specification ###

* **Grammars**
    * `CFG.ast`: Abstract grammar for the CFG language.
    * `CFGrammar.jjt`: Parsing grammar for the CFG language. Contains semantic actions to build the abstract syntax tree. Written in the JJTree format which is a preprocessor for the parser generator JavaCC.
* **Aspects**
    * `NameAnalysis.jrag`: Defines the *decl()* attribute that binds a nonterminal use (*NUse*) to its declaration (*NDecl*).
    * `Nullable.jrag`: Defines the *nullable()* attribute.
    * `First.jrag`: Defines the *first()* attribute.
    * `Follow.jrag`: Defines the *follow()* attribute.
    * `PrettyPrint.jrag`: Defines a String attribute *pp()* that is a prettyprinted representation of the CFG.

### Tested grammars ###

The directory `TestGrammars` contains a number of context-free grammars which are used in the test cases.

* `TestGrammarSimple`: A very simple context-free grammar.
* Grammars for testing **special cases** of empty and alternative productions:
    * `TestEmptyProd`
    * `TestEmptyAlternativeProd`
    * `TestAlternativeEmptyProd`
    * `TestAlternativeEmptyProd`
    * `TestFirstEmptyAlternatives`
* `TestAppelGrammar`: A small but nontrivial example grammar, taken from Andrew Appel's book *Modern Compiler Implementation in Java*.
* `TestManyProds`: A small grammar used for testing the prettyprinting functionality.
* **Java grammar**: A context-free grammar for Java 1.2 taken from the JavaCC project. The grammar is sorted alphabetically in order to facilitate comparison with expected results.
    * `TestJavaGrammarSorted`: The Java 1.2 grammar
    * `TestJavaGrammarNullableResult`: The expected result for the computation of `nullable()` for each nonterminal.
    * `TestJavaGrammarFirstResult`: The expected result for `first()`.
    * `TestJavaGrammarFollowResult`: The expected result for `follow()`.

### Test cases ###

* `testframework` is a directory containing general classes for testing.
    * `FileContentsTestCase.java` compares the contents of two files, ignoring whitespace.
    * `TestAll.java` is used for running all test cases. It shows an interactive panel with the test results.

* `tests` is a directory containing JUnit test cases. The test cases make use of the tested grammars described above.
    * `TestNullable.java`: Test cases for the *nullable()* attribute.
    * `TestFirst.java`: Test cases for the *first()* attribute.
    * `TestFollow.java`: Test cases for the *follow()* attribute.
    * `TestNameAnalysis.java`: Test cases for the *decl()* attribute.
    * `TestParser.java`: Tests that prettyprinting a parsed file gives back the original file (modulo whitespace).

### Building ###

* `tools` is a dir containing bundled tools: JavaCC, JastAdd, and JUnit.
* `AST`: Java package containing parser classes generated by JavaCC and AST classes generated by JastAdd. Attributes are represented by (pure) methods in the AST classes.
* `build.xml`: The Ant build file.

Build steps when running `ant test`:
* JavaCC is run on the `jjt` file to produce Java code for the parser.
* JastAdd is run on the `ast` and `jrag` files to produce Java code for the attributed abstract syntax tree classes.
* Javac is run to compile all the Java code.
* TestAll.java is run to run all JUnit test cases.

## Main contributors ##

Eva Magnusson and Görel Hedin, Lund University, Sweden

This code was originally developed in 2005, under CVS.

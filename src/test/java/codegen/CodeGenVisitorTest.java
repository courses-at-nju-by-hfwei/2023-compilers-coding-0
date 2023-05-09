package codegen;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class CodeGenVisitorTest {
  InputStream is = System.in;

  @BeforeMethod
  public void setUp() throws IOException {
//    is = new FileInputStream(Path.of("src/test/antlr/codegen/bool.txt").toFile());
//    is = new FileInputStream(Path.of("src/test/antlr/codegen/if.txt").toFile());
//    is = new FileInputStream(Path.of("src/test/antlr/codegen/while.txt").toFile());
    is = new FileInputStream(Path.of("src/test/antlr/codegen/break.txt").toFile());
  }

  @Test
  public void testCodeGenVisitor() throws IOException {
    CharStream input = CharStreams.fromStream(is);
    ControlLexer lexer = new ControlLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    ControlParser parser = new ControlParser(tokens);
    ParseTree tree = parser.prog();

//    CodeGenVisitor cg = new CodeGenVisitor("src/test/antlr/codegen/bool-code.txt");
//    CodeGenVisitor cg = new CodeGenVisitor("src/test/antlr/codegen/if-code.txt");
//    CodeGenVisitor cg = new CodeGenVisitor("src/test/antlr/codegen/while-code.txt");
    CodeGenVisitor cg = new CodeGenVisitor("src/test/antlr/codegen/break-code.txt");
    cg.visit(tree);
  }
}
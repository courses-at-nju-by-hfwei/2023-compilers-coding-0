package parser.allstar.exprprec;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ExprPrecTest {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(
        Path.of("src/test/antlr/allstar/exprprec/seA.txt").toFile());
//    InputStream is = new FileInputStream(
//        Path.of("src/test/antlr/allstar/exprprec/seB.txt").toFile());

    CharStream input = CharStreams.fromStream(is);
    SEAPrecLexer lexer = new SEAPrecLexer(input);
//    SEBPrecLexer lexer = new SEBPrecLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    SEAPrecParser parser = new SEAPrecParser(tokens);
//    SEBPrecParser parser = new SEBPrecParser(tokens);
    ParseTree tree = parser.s();

    // WARNING: The remaining code is generated by ChatGPT!!!
    JFrame frame = new JFrame("Parse Tree");
    JPanel panel = new JPanel();

    TreeViewer viewer = new TreeViewer(Arrays.asList(
        parser.getRuleNames()), tree);
    viewer.setScale(3.0);

    panel.add(viewer);
    frame.add(panel);

    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setVisible(true);
  }
}
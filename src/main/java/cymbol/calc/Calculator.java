package cymbol.calc;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import cymbol.CymbolLexer;
import cymbol.CymbolParser;
import cymbol.calc.listener.CalcListener;
import cymbol.calc.visitor.CalcVisitor;

public class Calculator {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(Path.of("src/main/antlr/cymbol/cymbol-calculator.txt").toFile());
//    InputStream is = new FileInputStream(Path.of("src/main/antlr/cymbol/cymbol-calc-stat.txt").toFile());
    CharStream cs = CharStreams.fromStream(is);
    CymbolLexer lexer = new CymbolLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    CymbolParser parser = new CymbolParser(tokens);

    // for CalcListener and cymbol-calculator.txt
    ParseTree tree = parser.prog();
    ParseTreeWalker walker = new ParseTreeWalker();
    CalcListener calcListener = new CalcListener();
    walker.walk(calcListener, tree);

//    for CalcVistor and cymbol-calc-stat.txt
//    ParseTree tree = parser.block();
//    CalcVisitor caclVisitor = new CalcVisitor();
//    caclVisitor.visit(tree);
  }
}

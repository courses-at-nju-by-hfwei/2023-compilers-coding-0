package codegen;

import org.antlr.v4.runtime.Token;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class CodeGenVisitor extends ControlBaseVisitor<String> {
  private final Deque<String> breakLabels = new ArrayDeque<>();

  private final FileWriter fileWriter;
  private int tempCounter = 1;
  private int labelCounter = 1;

  public CodeGenVisitor(String file) throws IOException {
    fileWriter = new FileWriter(file);
  }

  @Override
  public String visitProg(ControlParser.ProgContext ctx) {
    visit(ctx.stat());
    try {
      fileWriter.close();
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
    return null;
  }

  @Override
  public String visitBreakStat(ControlParser.BreakStatContext ctx) {
    emit("br " + breakLabels.peek());
    return null;
  }

  @Override
  public String visitWhileStat(ControlParser.WhileStatContext ctx) {
    String beginLabel = getNewLabel("begin");
    emit(beginLabel);

    String bool = visit(ctx.bool());

    String trueLabel = getNewLabel("b.true");
    String falseLabel = getNewLabel("b.false");
    emit("br " + bool + " " + trueLabel + " " + falseLabel);

    emitLabel(trueLabel);
    breakLabels.push(falseLabel);
    visit(ctx.stat());
    breakLabels.pop();
    emit("br " + beginLabel);

    emitLabel(falseLabel);
    return null;
  }

  @Override
  public String visitIfElseStat(ControlParser.IfElseStatContext ctx) {
    String bool = visit(ctx.bool());

    String trueLabel = getNewLabel("b.true");
    String falseLabel = getNewLabel("b.false");
    String nextLabel = getNewLabel("b.next");

    emit("br " + bool + " " + trueLabel + falseLabel);
    emitLabel(trueLabel);
    visit(ctx.ifStat);
    emit("br " + nextLabel);
    emitLabel(falseLabel);
    visit(ctx.elseStat);
    emitLabel(nextLabel);
    return null;
  }

  @Override
  public String visitIfStat(ControlParser.IfStatContext ctx) {
    String bool = visit(ctx.bool());

    String trueLabel = getNewLabel("b.true");
    String falseLabel = getNewLabel("b.false");
    emit("br " + bool + " " + trueLabel + " " + falseLabel);

    emitLabel(trueLabel);
    visit(ctx.stat());

    emitLabel(falseLabel);
    return null;
  }

  @Override
  public String visitSeqStat(ControlParser.SeqStatContext ctx) {
    visit(ctx.first);
    visit(ctx.second);
    return null;
  }

  @Override
  public String visitAssignStat(ControlParser.AssignStatContext ctx) {
    String expr = visit(ctx.expr());
    emit(ctx.ID().getText() + " = " + expr);
    return null;
  }

  @Override
  public String visitNotExpr(ControlParser.NotExprContext ctx) {
    String bool = visit(ctx.bool());

    String temp = getNewTemp();
    emit(temp + " = " + "NOT " + bool);
    return temp;
  }

  @Override
  public String visitRelExpr(ControlParser.RelExprContext ctx) {
    String lhs = visit(ctx.lhs);
    String rhs = visit(ctx.rhs);

    Token op = ctx.op;
    String cond = switch (op.getType()) {
      case ControlLexer.GT -> "sgt";
      case ControlLexer.GE -> "sge";
      case ControlLexer.EE -> "eq";
      default -> throw new IllegalArgumentException("No such cond: " + op.getText());
    };

    String temp = getNewTemp();
    emit(temp + " = " + "icmp " + cond + " " + lhs + " " + rhs);
    return temp;
  }

  @Override
  public String visitOrExpr(ControlParser.OrExprContext ctx) {
    String lhs = visit(ctx.lhs);
    String rhs = visit(ctx.rhs);

    String temp = getNewTemp();
    emit(temp + " = OR " + lhs + " " + rhs);
    return temp;
  }

  @Override
  public String visitAndExpr(ControlParser.AndExprContext ctx) {
    String lhs = visit(ctx.lhs);
    String rhs = visit(ctx.rhs);

    String temp = getNewTemp();
    emit(temp + " = AND " + lhs + " " + rhs);
    return temp;
  }

  @Override
  public String visitFalseExpr(ControlParser.FalseExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    return temp;
  }

  @Override
  public String visitTrueExpr(ControlParser.TrueExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    return temp;
  }

  @Override
  public String visitIdExpr(ControlParser.IdExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    return temp;
  }

  @Override
  public String visitIntExpr(ControlParser.IntExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    return temp;
  }

  @Override
  public String visitMultExpr(ControlParser.MultExprContext ctx) {
    return super.visitMultExpr(ctx);
  }

  @Override
  public String visitADDExpr(ControlParser.ADDExprContext ctx) {
    return super.visitADDExpr(ctx);
  }

  @Override
  public String visitNegExpr(ControlParser.NegExprContext ctx) {
    return super.visitNegExpr(ctx);
  }

  @Override
  public String visitParenExpr(ControlParser.ParenExprContext ctx) {
    return super.visitParenExpr(ctx);
  }

  private void emitLabel(String label) {
    try {
      fileWriter.write(label + ":\n");
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage() + " : " + label);
    }
  }

  private void emit(String code) {
    try {
      fileWriter.write(code + '\n');
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage() + " : " + code);
    }
  }

  private String getNewTemp() {
    return "t" + tempCounter++;
  }

  private String getNewLabel(String label) {
    return label + labelCounter++;
  }
}
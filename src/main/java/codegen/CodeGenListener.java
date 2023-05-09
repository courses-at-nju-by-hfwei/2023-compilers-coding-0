package codegen;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.io.FileWriter;
import java.io.IOException;

public class CodeGenListener extends ControlBaseListener {
  private final ParseTreeProperty<String> placeProperty = new ParseTreeProperty<>();
  private final ParseTreeProperty<String> codeProperty = new ParseTreeProperty<>();

  private final FileWriter fileWriter;
  private int tempCounter = 1;
  private int labelCounter = 1;

  public CodeGenListener(String file) throws IOException {
    fileWriter = new FileWriter(file);
  }

  @Override
  public void exitProg(ControlParser.ProgContext ctx) {
    try {
      fileWriter.close();
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
  }

  @Override
  public void exitWhileStat(ControlParser.WhileStatContext ctx) {
    super.exitWhileStat(ctx);
  }

  @Override
  public void exitIfElseStat(ControlParser.IfElseStatContext ctx) {
  }

  @Override
  public void exitIfStat(ControlParser.IfStatContext ctx) {
  }

  @Override
  public void exitSeqStat(ControlParser.SeqStatContext ctx) {
    // do nothing
  }

  @Override
  public void exitAssignStat(ControlParser.AssignStatContext ctx) {
    String expr = placeProperty.get(ctx.expr());
    emit(ctx.ID().getText() + " = " + expr);
  }

  @Override
  public void exitNotExpr(ControlParser.NotExprContext ctx) {
    String temp = getNewTemp();
    String bool = placeProperty.get(ctx.bool());
    emit(temp + " = " + "NOT " + bool);
    placeProperty.put(ctx, temp);
  }

  @Override
  public void exitRelExpr(ControlParser.RelExprContext ctx) {
    String temp = getNewTemp();
    String lhs = placeProperty.get(ctx.lhs);
    String rhs = placeProperty.get(ctx.rhs);

    Token op = ctx.op;
    String cond = switch (op.getType()) {
      case ControlLexer.GT -> "sgt";
      case ControlLexer.GE -> "sge";
      case ControlLexer.EE -> "eq";
      default -> throw new IllegalArgumentException("No such cond: " + op.getText());
    };

    emit(temp + " = " + "icmp " + cond + " " + lhs + " " + rhs);
    placeProperty.put(ctx, temp);
  }

  @Override
  public void exitOrExpr(ControlParser.OrExprContext ctx) {
    String temp = getNewTemp();
    String lhs = placeProperty.get(ctx.lhs);
    String rhs = placeProperty.get(ctx.rhs);
    emit(temp + " = OR " + lhs + " " + rhs);
    placeProperty.put(ctx, temp);
  }

  @Override
  public void exitAndExpr(ControlParser.AndExprContext ctx) {
    String temp = getNewTemp();
    String lhs = placeProperty.get(ctx.lhs);
    String rhs = placeProperty.get(ctx.rhs);
    emit(temp + " = AND " + lhs + " " + rhs);
    placeProperty.put(ctx, temp);
  }

  @Override
  public void exitTrueExpr(ControlParser.TrueExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    placeProperty.put(ctx, temp);
  }

  @Override
  public void exitFalseExpr(ControlParser.FalseExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    placeProperty.put(ctx, temp);
  }

  @Override
  public void exitIdExpr(ControlParser.IdExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    placeProperty.put(ctx, temp);
  }

  @Override
  public void exitIntExpr(ControlParser.IntExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    placeProperty.put(ctx, temp);
  }

  private void dump(String code) {
    try {
      fileWriter.write(code);
      fileWriter.close();
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage() + " : " + code);
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

  private String getNewLabel() {
    return "L" + labelCounter++ + ":";
  }
}
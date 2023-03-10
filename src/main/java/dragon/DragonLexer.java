package dragon;

public class DragonLexer extends Lexer {
  private final KeywordTable kwTable = new KeywordTable();

  public DragonLexer(String input) {
    super(input);
  }

  @Override
  public Token nextToken() {
    if (peek == EOF) {
      return Token.EOF;
    }

    if (Character.isWhitespace(peek)) {
      return WS();
    }

    if (Character.isLetter(peek)) {
      return ID();
    }

    if (Character.isDigit(peek)) {
      return NUMBER();
    }

    if (peek == '=') {
      advance();
      return Token.EQ;
    }

    if (peek == '<') {
      advance();
      if (peek == '=') {
        advance();
        return Token.LE;
      }

      if (peek == '>') {
        advance();
        return Token.NE;
      }

      return Token.LT;
    }

    if (peek == '>') {
      advance();

      if (peek == '=') {
        advance();
        return Token.GE;
      }

      return Token.GT;
    }

    Token unknown = new Token(TokenType.UNKNOWN, Character.toString(peek));
    advance();
    return unknown;
  }

  private Token WS() {
    while (Character.isWhitespace(this.peek)) {
      advance();
    }

    return Token.WS;
  }

  private Token ID() {
    StringBuilder sb = new StringBuilder();

    do {
      sb.append(peek);
      advance();
    } while (Character.isLetterOrDigit(peek));

    Token token = this.kwTable.getKeyword(sb.toString());
    if (token == null) {
      return new Token(TokenType.ID, sb.toString());
    }

    return token;
  }

  private Token INT() {
    StringBuilder sb = new StringBuilder();

    do {
      sb.append(peek);
      advance();
    } while (Character.isDigit(peek));

    return new Token(TokenType.INT, sb.toString());
  }

  private Token NUMBER() {
    StringBuilder intStr = new StringBuilder();
    intStr.append(peek);
    advance();

    int intPos = -1;
    int realPos = -1;

    StringBuilder realStr = new StringBuilder();
    StringBuilder sciStr = new StringBuilder();

    int state = 13;
    while (true) {
      switch (state) {
        case 13:
          intPos = pos;
          if (Character.isDigit(peek)) {
            intStr.append(peek);
            advance();
            state = 13;
            break;
          } else if (peek == '.') {
            realStr.append(peek);
            advance();
            state = 14;
            break;
          } else if (peek == 'E' || peek == 'e') {
            sciStr.append(peek);
            advance();
            state = 16;
            break;
          } else {
            return new Token(TokenType.INT, intStr.toString());
          }
        case 14:
          if (Character.isDigit(peek)) {
            realStr.append(peek);
            advance();
            state = 15;
            break;
          } else {
            this.reset(intPos);
            return new Token(TokenType.INT, intStr.toString());
          }
        case 15:
          realPos = pos;
          if (Character.isDigit(peek)) {
            realStr.append(peek);
            advance();
            state = 15;
            break;
          } else if (peek == 'E') {
            sciStr.append(peek);
            advance();
            state = 16;
            break;
          } else {
            return new Token(TokenType.REAL, intStr.append(realStr).toString());
          }
        case 16:
          if (peek == '+' || peek == '-') {
            sciStr.append(peek);
            advance();
            state = 17;
            break;
          } else if (Character.isDigit(peek)) {
            sciStr.append(peek);
            advance();
            state = 18;
            break;
          } else {
            this.reset(realPos);
            return new Token(TokenType.REAL, intStr.append(realStr).toString());
          }
        case 17:
          if (Character.isDigit(peek)) {
            sciStr.append(peek);
            advance();
            state = 18;
            break;
          } else {
            this.reset(realPos);
            return new Token(TokenType.REAL, intStr.append(realStr).toString());
          }
        case 18:
          if (Character.isDigit(peek)) {
            sciStr.append(peek);
            advance();
            state = 18;
            break;
          } else {
            return new Token(TokenType.SCI, intStr.append(realStr).append(sciStr).toString());
          }
        default:
          System.err.println("Unreachable");
      }
    }
  }
}

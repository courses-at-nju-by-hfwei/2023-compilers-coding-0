package dragon;

public class DragonLexerTemplate extends Lexer {
  private final KeywordTable kwTable = new KeywordTable();

  public DragonLexerTemplate(String input) {
    super(input);
  }

  @Override
  public Token nextToken() {
    // add code below for WS, ID, INT (NUMBER)

    // add code below for relop

    // unknown tokens (characters)
    Token unknown = new Token(TokenType.UNKNOWN, Character.toString(peek));
    advance();
    return unknown;
  }

  private Token WS() {
    // add code below

    return Token.WS;
  }

  private Token ID() {
    // add code below

    return null;
  }

  private Token INT() {
    // add code below

    return null;
  }

  private Token NUMBER() {
    StringBuilder intStr = new StringBuilder();
    intStr.append(peek);
    advance();

    // add code below for recording positions

    // add code below for storing scanned characters

    int state = 13;
    while (true) {
      switch (state) {
        case 13:
          if (Character.isDigit(peek)) {
            // add code below for recording scanned characters

            advance();
            state = 13;
            break;
          } else if (peek == '.') {
            // add code below for recording scanned characters

            advance();
            state = 14;
            break;
          } else if (peek == 'E' || peek == 'e') {
            // add code below for recording scanned characters

            advance();
            state = 16;
            break;
          } else {
            // add code for recognizing and returning an INT
            return null;
          }
        case 14:
          if (Character.isDigit(peek)) {
            // add code below for recording scanned characters

            advance();
            state = 15;
            break;
          } else {
            // add code below for resetting positions and recognizing an INT

            return null;
          }
        case 15:
          // add code below for recording positions

          if (Character.isDigit(peek)) {
            // add code below for recording scanned characters

            advance();
            state = 15;
            break;
          } else if (peek == 'E') {
            // add code below for recording scanned characters

            advance();
            state = 16;
            break;
          } else {
            // add code for recognizing and returning a REAL
            return null;
          }
        case 16:
          if (peek == '+' || peek == '-') {
            // add code below for recording scanned characters

            advance();
            state = 17;
            break;
          } else if (Character.isDigit(peek)) {
            // add code below for recording scanned characters

            advance();
            state = 18;
            break;
          } else {
            // add code below for resetting positions and recognizing an INT

            return null;
          }
        case 17:
          if (Character.isDigit(peek)) {
            // add code below for recording scanned characters

            advance();
            state = 18;
            break;
          } else {
            // add code below for resetting positions and recognizing an INT

            return null;
          }
        case 18:
          if (Character.isDigit(peek)) {
            // add code below for recording scanned characters

            advance();
            state = 18;
            break;
          } else {
            // add code for recognizing and returning an SCI
            return null;
          }
        default:
          System.err.println("Unreachable");
      }
    }
  }
}
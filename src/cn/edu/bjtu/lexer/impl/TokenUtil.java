package cn.edu.bjtu.lexer.impl;

import cn.edu.bjtu.lexer.TokenType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The {@code TokenUtil} class defines the constant values of characters, available tokens,
 * and static methods for checking types of characters and strings.
 */
public class TokenUtil {

    public static final int CHAR_EOF = -1;

    // White-space characters which are available in ANSI C

    public static final int CHAR_HORIZONTAL_TAB = '\t';
    public static final int CHAR_LINE_FEED = '\n';
    public static final int CHAR_VERTICAL_TAB = '\u000b';
    public static final int CHAR_FEED = '\f';
    public static final int CHAR_CARRIAGE_RETURN = '\r';
    public static final int CHAR_SPACE = ' ';

    // Some characters that used in the LexerImpl class

    public static final int CHAR_SLASH = '/';
    public static final int CHAR_PERIOD = '.';
    public static final int CHAR_UNDERLINE = '_';
    public static final int CHAR_STAR = '*';
    public static final int CHAR_SHARP = '#';
    public static final int CHAR_ESCAPE = '\\';
    public static final int CHAR_DOUBLE_QUOTE = '"';
    public static final int CHAR_SINGLE_QUOTE = '\'';
    public static final int CHAR_LEFT_BRACE = '{';
    public static final int CHAR_RIGHT_BRACE = '}';
    public static final int CHAR_LEFT_BRACKET = '[';
    public static final int CHAR_RIGHT_BRACKET = ']';
    public static final int CHAR_LEFT_PARENTHESIS = '(';
    public static final int CHAR_RIGHT_PARENTHESIS = ')';

    /**
     * Available keywords for the lexical scanner.
     * The token types of all the keywords are {@link TokenTypeImpl#KEYWORD}.
     *
     * @see TokenUtil#isKeyword(String)
     * @see TokenUtil#getKeywordTokenType(String)
     */
    private static final Map<String, TokenType> KEYWORDS = new HashMap<String, TokenType>() {{
        put("void", TokenTypeImpl.KEYWORD);
        put("char", TokenTypeImpl.KEYWORD);
        put("int", TokenTypeImpl.KEYWORD);
        put("float", TokenTypeImpl.KEYWORD);
        put("double", TokenTypeImpl.KEYWORD);
        put("short", TokenTypeImpl.KEYWORD);
        put("long", TokenTypeImpl.KEYWORD);
        put("signed", TokenTypeImpl.KEYWORD);
        put("unsigned", TokenTypeImpl.KEYWORD);
        put("struct", TokenTypeImpl.KEYWORD);
        put("union", TokenTypeImpl.KEYWORD);
        put("enum", TokenTypeImpl.KEYWORD);
        put("typedef", TokenTypeImpl.KEYWORD);
        put("sizeof", TokenTypeImpl.KEYWORD);
        put("auto", TokenTypeImpl.KEYWORD);
        put("static", TokenTypeImpl.KEYWORD);
        put("register", TokenTypeImpl.KEYWORD);
        put("extern", TokenTypeImpl.KEYWORD);
        put("const", TokenTypeImpl.KEYWORD);
        put("volatile", TokenTypeImpl.KEYWORD);
        put("return", TokenTypeImpl.KEYWORD);
        put("continue", TokenTypeImpl.KEYWORD);
        put("break", TokenTypeImpl.KEYWORD);
        put("goto", TokenTypeImpl.KEYWORD);
        put("if", TokenTypeImpl.KEYWORD);
        put("else", TokenTypeImpl.KEYWORD);
        put("switch", TokenTypeImpl.KEYWORD);
        put("case", TokenTypeImpl.KEYWORD);
        put("default", TokenTypeImpl.KEYWORD);
        put("for", TokenTypeImpl.KEYWORD);
        put("do", TokenTypeImpl.KEYWORD);
        put("while", TokenTypeImpl.KEYWORD);
    }};

    /**
     * Available operators for the lexical scanner.
     * The token types of all the operators are {@link TokenTypeImpl#OPERATOR}.
     *
     * @see TokenUtil#isOperator(String)
     */
    private static final Map<String, TokenType> OPERATORS = new HashMap<String, TokenType>() {{
        put("+", TokenTypeImpl.OPERATOR);
        put("-", TokenTypeImpl.OPERATOR);
        put("*", TokenTypeImpl.OPERATOR);
        put("/", TokenTypeImpl.OPERATOR);
        put("%", TokenTypeImpl.OPERATOR);
        put("+=", TokenTypeImpl.OPERATOR);
        put("-=", TokenTypeImpl.OPERATOR);
        put("*=", TokenTypeImpl.OPERATOR);
        put("/=", TokenTypeImpl.OPERATOR);
        put("%=", TokenTypeImpl.OPERATOR);
        put("&", TokenTypeImpl.OPERATOR);
        put("|", TokenTypeImpl.OPERATOR);
        put("^", TokenTypeImpl.OPERATOR);
        put("&^", TokenTypeImpl.OPERATOR);
        put("<<", TokenTypeImpl.OPERATOR);
        put(">>", TokenTypeImpl.OPERATOR);
        put("~", TokenTypeImpl.OPERATOR);
        put("&=", TokenTypeImpl.OPERATOR);
        put("|=", TokenTypeImpl.OPERATOR);
        put("^=", TokenTypeImpl.OPERATOR);
        put("&^=", TokenTypeImpl.OPERATOR);
        put("<<=", TokenTypeImpl.OPERATOR);
        put(">>=", TokenTypeImpl.OPERATOR);
        put("&&", TokenTypeImpl.OPERATOR);
        put("||", TokenTypeImpl.OPERATOR);
        put("++", TokenTypeImpl.OPERATOR);
        put("--", TokenTypeImpl.OPERATOR);
        put("==", TokenTypeImpl.OPERATOR);
        put("!", TokenTypeImpl.OPERATOR);
        put(">", TokenTypeImpl.OPERATOR);
        put("<", TokenTypeImpl.OPERATOR);
        put("=", TokenTypeImpl.OPERATOR);
        put("!=", TokenTypeImpl.OPERATOR);
        put(">=", TokenTypeImpl.OPERATOR);
        put("<=", TokenTypeImpl.OPERATOR);
        put("->", TokenTypeImpl.OPERATOR);
    }};

    /**
     * Available delimiters for lexical scanner.
     * The token types of all the delimiters are {@link TokenTypeImpl#DELIMITER}.
     *
     * @see TokenUtil#isDelimiter(int)
     */
    private static final Map<Integer, TokenType> DELIMITERS = new HashMap<Integer, TokenType>() {{
        put((int) ',', TokenTypeImpl.DELIMITER);
        put((int) '.', TokenTypeImpl.DELIMITER);
        put((int) ';', TokenTypeImpl.DELIMITER);
        put((int) ':', TokenTypeImpl.DELIMITER);
        put((int) '?', TokenTypeImpl.DELIMITER);
        put((int) '(', TokenTypeImpl.DELIMITER);
        put((int) '[', TokenTypeImpl.DELIMITER);
        put((int) '{', TokenTypeImpl.DELIMITER);
        put((int) ')', TokenTypeImpl.DELIMITER);
        put((int) ']', TokenTypeImpl.DELIMITER);
        put((int) '}', TokenTypeImpl.DELIMITER);
    }};

    /**
     * Available operator prefixes according to {@link TokenUtil#OPERATORS}.
     *
     * @see TokenUtil#isOperatorPrefix(int)
     */
    private static final Set<Integer> OPERATOR_PREFIXES = new HashSet<Integer>() {{
        for (String key : OPERATORS.keySet()) {
            add((int) key.charAt(0));
        }
    }};

    // Characters in integer for ranging the letters and digits

    private static final int CHAR_LETTER_LOWERCASE_BEGIN = 'a';
    private static final int CHAR_LETTER_LOWERCASE_END = 'z';
    private static final int CHAR_LETTER_UPPERCASE_BEGIN = 'A';
    private static final int CHAR_LETTER_UPPERCASE_END = 'Z';
    private static final int CHAR_DIGIT_BEGIN = '0';
    private static final int CHAR_DIGIT_END = '9';

    // Regular expression patterns for different number types

    private static final String PATTERN_NUMBER_INTEGER = "^0|[1-9][0-9]*$";
    private static final String PATTERN_NUMBER_FLOAT = "(?i)(^[0-9]*\\.[0-9]+f?$)|(^0|[1-9][0-9]*f$)";
    private static final String PATTERN_NUMBER_LONG = "(?i)(^0l$)|(^[1-9][0-9]*l$)";
    private static final String PATTERN_NUMBER_LONG_LONG = "(?i)(^0ll$)|(^[1-9][0-9]*ll$)";
    private static final String PATTERN_NUMBER_UNSIGNED = "(?i)(^0u|[1-9][0-9]*u$)";
    private static final String PATTERN_NUMBER_OCTAL = "^0[1-7][0-7]*$";
    private static final String PATTERN_NUMBER_HEXADECIMAL = "(?i)^0x[1-9a-f][0-9a-f]*$";

    /**
     * Check if the character is a white-space.
     * <p>
     * There are six white-space characters in {@code ctype.h} of ANSI C:
     * <ul>
     *     <li>Horizontal tab (TAB): {@code '\t'} (0x09)</li>
     *     <li>Line feed (LF):       {@code '\n'} (0x0a)</li>
     *     <li>Vertical tab (VT):    {@code '\v'} (0x0b)</li>
     *     <li>Feed (FF):            {@code '\f'} (0x0c)</li>
     *     <li>Carriage return (CR): {@code '\r'} (0x0d)</li>
     *     <li>Space (SPC):          {@code ' '}  (0x20)</li>
     * </ul>
     *
     * @param c Character in {@code int} to be tested.
     * @return {@code true} when the character is a white space.
     */
    public static boolean isSpace(int c) {
        return c == CHAR_HORIZONTAL_TAB
            || c == CHAR_LINE_FEED
            || c == CHAR_VERTICAL_TAB
            || c == CHAR_FEED
            || c == CHAR_CARRIAGE_RETURN
            || c == CHAR_SPACE;
    }

    /**
     * Check if the character is a digit.
     *
     * @param c Character in {@code int} to be tested.
     * @return {@code true} when the character is a digit.
     */
    public static boolean isDigit(int c) {
        return CHAR_DIGIT_BEGIN <= c && c <= CHAR_DIGIT_END;
    }

    /**
     * Check if the character is a letter.
     *
     * @param c Character in {@code int} to be tested.
     * @return {@code true} when the character is a letter.
     */
    public static boolean isLetter(int c) {
        return (CHAR_LETTER_LOWERCASE_BEGIN <= c && c <= CHAR_LETTER_LOWERCASE_END)
            || (CHAR_LETTER_UPPERCASE_BEGIN <= c && c <= CHAR_LETTER_UPPERCASE_END);
    }

    /**
     * Check if the character is a keyword.
     *
     * @param s String to be tested.
     * @return {@code true} when the string is a keyword.
     */
    public static boolean isKeyword(String s) {
        return KEYWORDS.containsKey(s);
    }

    /**
     * Check if the character is the prefix of the operators.
     *
     * @param c Character in {@code int} to be tested.
     * @return {@code true} when the character is a prefix of operators.
     */
    public static boolean isOperatorPrefix(int c) {
        return OPERATOR_PREFIXES.contains(c);
    }

    /**
     * Check if the string is an operator.
     *
     * @param s String to be tested.
     * @return {@code true} when the string is an operator.
     */
    public static boolean isOperator(String s) {
        return OPERATORS.containsKey(s);
    }

    /**
     * Check if the character is a delimiter.
     *
     * @param c Character in int to be tested.
     * @return {@code true} when the character is a delimiter.
     */
    public static boolean isDelimiter(int c) {
        return DELIMITERS.containsKey(c);
    }

    /**
     * Get the token type of string.
     *
     * @param token String to get the token type.
     * @return The corresponding token type.
     * If the string is a key of {@link TokenUtil#KEYWORDS}, get by the {@code get()} method.
     * Else if, the token length is not longer than 32, its type is {@link TokenTypeImpl#IDENTIFIER}.
     * Otherwise, the token is {@link TokenTypeImpl#ILLEGAL}.
     */
    public static TokenType getKeywordTokenType(String token) {
        if (isKeyword(token)) {
            return KEYWORDS.get(token);
        } else if (token.length() <= 32) {
            return TokenTypeImpl.IDENTIFIER;
        } else {
            return TokenTypeImpl.ILLEGAL;
        }
    }

    /**
     * Get the token type of operator in string.
     *
     * @param token Operator in string to get the token type.
     * @return The corresponding token type.
     * If the string is a key of {@link TokenUtil#OPERATORS}, get by the {@code get()} method.
     * Otherwise, the token type is {@link TokenTypeImpl#ILLEGAL}.
     */
    public static TokenType getOperatorTokenType(String token) {
        if (isOperator(token)) {
            return OPERATORS.get(token);
        } else {
            return TokenTypeImpl.ILLEGAL;
        }
    }

    /**
     * Get the token type of number in string.
     *
     * @param token Number in string to get the token type.
     * @return The corresponding token type of the number in string according to the regular expression patterns.
     */
    public static TokenType getNumberTokenType(String token) {
        if (token.matches(PATTERN_NUMBER_INTEGER)) {
            return TokenTypeImpl.NUMBER_INTEGER;
        } else if (token.matches(PATTERN_NUMBER_FLOAT)) {
            return TokenTypeImpl.NUMBER_FLOAT;
        } else if (token.matches(PATTERN_NUMBER_LONG)) {
            return TokenTypeImpl.NUMBER_LONG;
        } else if (token.matches(PATTERN_NUMBER_LONG_LONG)) {
            return TokenTypeImpl.NUMBER_LONG_LONG;
        } else if (token.matches(PATTERN_NUMBER_UNSIGNED)) {
            return TokenTypeImpl.NUMBER_UNSIGNED;
        } else if (token.matches(PATTERN_NUMBER_OCTAL)) {
            return TokenTypeImpl.NUMBER_OCTAL;
        } else if (token.matches(PATTERN_NUMBER_HEXADECIMAL)) {
            return TokenTypeImpl.NUMBER_HEXADECIMAL;
        } else {
            return TokenTypeImpl.ILLEGAL;
        }
    }

}
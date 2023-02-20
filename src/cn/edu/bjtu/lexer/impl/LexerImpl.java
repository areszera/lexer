package cn.edu.bjtu.lexer.impl;

import cn.edu.bjtu.lexer.Lexer;
import cn.edu.bjtu.lexer.Position;
import cn.edu.bjtu.lexer.ResultSet;
import cn.edu.bjtu.lexer.TokenType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import static cn.edu.bjtu.lexer.impl.TokenUtil.*;

/**
 * The {@code Lexer} class is the implementation of the {@link Lexer} interface.
 */
public class LexerImpl implements Lexer {

    private static final String PATTERN_HTML = "(?i).*\\.html$";
    private static final String PATTERN_MARKDOWN = "(?i).*\\.md$";
    private static final String EMPTY_TOKEN = "";
    private final ResultSet rs = new ResultSetImpl();
    private final FileReader reader;
    private final Position position = new Position();

    /**
     * Count brackets: '[' and ']'
     */
    private int countBrack = 0;
    /**
     * Count parentheses: '(' and ')'
     */
    private int countParen = 0;
    /**
     * Count braces: '{' and '}'
     */
    private int countBrace = 0;

    private int currentChar;
    private Position begin = new Position(position.getIndex(), position.getRow(), position.getColumn());
    private Position end = new Position(position.getIndex(), position.getRow(), position.getColumn());

    /**
     * Constructs a new {@code Lexer} object.
     *
     * @param filename Name of the file to read.
     * @throws FileNotFoundException when the file does not exist.
     */
    public LexerImpl(String filename) throws FileNotFoundException {
        reader = new FileReader(filename);
    }

    /**
     * Based on the {@link FileReader#read()} method, modify the {@link LexerImpl#currentChar}
     * and the {@code Position} objects according to the currently read character.
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void read() throws IOException {
        // Update end position
        end = new Position(position.getIndex() + 1, position.getRow(), position.getColumn() + 1);
        currentChar = reader.read();
        // Check if the current character is LF (\n)
        if (currentChar == CHAR_LINE_FEED) {
            position.nextLine();
        } else {
            position.nextChar();
        }
    }

    /**
     * When the current character is a slash,
     * it will be called by the {@link LexerImpl#scan()} method to scan comment.
     * <p>
     * It firstly read the next character, then check the current character:
     * <ul>
     *     <li>When read EOF, check if slash is an operator and append it to the {@code ResultSet} object.</li>
     *     <li>When read slash, this line is a inline comment.</li>
     *     <li>When read star, the block comment begins, read until another {@code &#42/}.</li>
     *     <li>Otherwise, handle as an operator.</li>
     * </ul>
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void scanComment() throws IOException {
        int temp = currentChar;
        read();
        if (currentChar == CHAR_EOF) {
            rs.append(begin, end, getOperatorTokenType(Character.toString((char) temp)), Character.toString((char) temp));
        } else if (currentChar == CHAR_SLASH) {
            do {
                read();
            } while (currentChar != CHAR_EOF && currentChar != CHAR_LINE_FEED && currentChar != CHAR_CARRIAGE_RETURN);
            rs.append(begin, end, TokenTypeImpl.INLINE_COMMENT, EMPTY_TOKEN);
        } else if (currentChar == CHAR_STAR) {
            boolean prepare = false;
            do {
                read();
                boolean isCommentEnd = prepare && currentChar == CHAR_SLASH;
                if (currentChar != CHAR_EOF && !isCommentEnd) {
                    prepare = currentChar == CHAR_STAR;
                } else {
                    break;
                }
            } while (true);
            read();
            rs.append(begin, end, TokenTypeImpl.BLOCK_COMMENT, EMPTY_TOKEN);
        } else {
            StringBuilder builder = new StringBuilder().append((char) temp);
            do {
                boolean isOperator = isOperator(builder.toString() + (char) currentChar);
                if (currentChar != CHAR_EOF && !isSpace(currentChar) && !isLetter(currentChar) && !isDigit(currentChar) && !isDelimiter(currentChar) && isOperator) {
                    builder.append((char) currentChar);
                } else {
                    break;
                }
                read();
            } while (true);
            rs.append(begin, end, getOperatorTokenType(builder.toString()), builder.toString());
        }
    }

    /**
     * When the current character is a sharp,
     * it will be called by the {@link LexerImpl#scan()} method to scan preprocessor.
     * <p>
     * Read until the current character in {@code int} is EOF, CR, or LF.
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void scanPreprocessor() throws IOException {
        StringBuilder builder = new StringBuilder().append((char) currentChar);
        do {
            read();
            if (currentChar != CHAR_EOF && currentChar != CHAR_CARRIAGE_RETURN && currentChar != CHAR_LINE_FEED) {
                builder.append((char) currentChar);
            } else {
                break;
            }
        } while (true);
        rs.append(begin, end, TokenTypeImpl.PREPROCESSOR, builder.toString());
    }

    /**
     * When the current character is a single quote,
     * it will be called by the {@link LexerImpl#scan()} method to scan character.
     * <p>
     * Use an integer to record the length of the character for checking
     * if the content rounded by single quotes are legal as character.
     * Use a boolean value to mark the escape characters.
     * Read until:
     * <ul>
     *     <li>EOF, CR, or LF.</li>
     *     <li>The current character is a single quote and the status is not prepare to transfer.</li>
     * </ul>
     * It finally checks if the character is legal by the length of its content.
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void scanCharacter() throws IOException {
        int length = 0;
        boolean prepare = false;
        StringBuilder builder = new StringBuilder().append((char) currentChar);
        do {
            read();
            if (currentChar != CHAR_EOF && currentChar == CHAR_ESCAPE) {
                builder.append((char) currentChar);
                if (prepare) {
                    prepare = false;
                    length++;
                } else {
                    prepare = true;
                }
            } else if (currentChar != CHAR_EOF && currentChar != CHAR_CARRIAGE_RETURN && currentChar != CHAR_LINE_FEED) {
                builder.append((char) currentChar);
                if (!prepare && currentChar == CHAR_SINGLE_QUOTE) {
                    break;
                }
                prepare = false;
                length++;
            } else {
                break;
            }
        } while (true);
        if (length == 1) {
            rs.append(begin, end, TokenTypeImpl.CHARACTER, builder.toString());
        } else {
            rs.append(begin, end, TokenTypeImpl.ILLEGAL, builder.toString());
        }
        read();
    }

    /**
     * When the current character is a double quote,
     * it will be called by the {@link LexerImpl#scan()} method to scan string.
     * <p>
     * Use a boolean value to mark the escape characters.
     * Read until:
     * <ul>
     *     <li>EOF, CR, or LF.</li>
     *     <li>The current character is a double quote and the status is not prepare to transfer.</li>
     * </ul>
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void scanString() throws IOException {
        TokenType type = TokenTypeImpl.STRING;
        boolean prepare = false;
        StringBuilder builder = new StringBuilder().append((char) currentChar);
        do {
            read();
            if (currentChar == CHAR_EOF) {
                type = TokenTypeImpl.ILLEGAL;
                break;
            } else if (currentChar == CHAR_ESCAPE) {
                builder.append((char) currentChar);
                prepare = !prepare;
            } else if (currentChar != CHAR_CARRIAGE_RETURN && currentChar != CHAR_LINE_FEED) {
                builder.append((char) currentChar);
                if (!prepare && currentChar == CHAR_DOUBLE_QUOTE) {
                    break;
                }
                prepare = false;
            } else {
                type = TokenTypeImpl.ILLEGAL;
                break;
            }
        } while (true);
        rs.append(begin, end, type, builder.toString());
        read();
    }

    /**
     * When the current character is a digit,
     * it will be called by the {@link LexerImpl#scan()} method to scan digit.
     * <p>
     * Read until the character is not one of the digits, letters, and period ({@code .}).
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void scanDigits() throws IOException {
        StringBuilder builder = new StringBuilder().append((char) currentChar);
        do {
            read();
            boolean isCharValid = isDigit(currentChar) || isLetter(currentChar) || currentChar == CHAR_PERIOD;
            if (currentChar != CHAR_EOF && isCharValid) {
                builder.append((char) currentChar);
            } else {
                break;
            }
        } while (true);
        rs.append(begin, end, getNumberTokenType(builder.toString()), builder.toString());
    }

    /**
     * When the current character is a letter or underline,
     * it will be called by the {@link LexerImpl#scan()} method to scan letters.
     * <p>
     * Read until the character is not one of the digits, letters, and underline.
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void scanLetters() throws IOException {
        StringBuilder builder = new StringBuilder().append((char) currentChar);
        do {
            read();
            boolean isCharValid = isDigit(currentChar) || isLetter(currentChar) || currentChar == CHAR_UNDERLINE;
            if (currentChar != CHAR_EOF && isCharValid) {
                builder.append((char) currentChar);
            } else {
                break;
            }
        } while (true);
        rs.append(begin, end, getKeywordTokenType(builder.toString()), builder.toString());
    }

    /**
     * When the current character is a delimiter,
     * it will be called by the {@link LexerImpl#scan()} method to scan delimiter.
     * <p>
     * It will modify {@link LexerImpl#countBrace}, {@link LexerImpl#countBrack}, and {@link LexerImpl#countParen}
     * for checking if these delimiters appears in pair.
     * Since the length of all the delimiters are 1, append it to its {@code ResultSet} object directly.
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void scanDelimiter() throws IOException {
        switch (currentChar) {
            case CHAR_LEFT_BRACE:
                countBrace++;
                break;
            case CHAR_RIGHT_BRACE:
                countBrace--;
                break;
            case CHAR_LEFT_BRACKET:
                countBrack++;
                break;
            case CHAR_RIGHT_BRACKET:
                countBrack--;
                break;
            case CHAR_LEFT_PARENTHESIS:
                countParen++;
                break;
            case CHAR_RIGHT_PARENTHESIS:
                countParen--;
                break;
            default:
                break;
        }
        String value = Character.toString((char) currentChar);
        read();
        rs.append(begin, end, TokenTypeImpl.DELIMITER, value);
    }

    /**
     * When the current character is an operator prefix,
     * it will be called by the {@link LexerImpl#scan()} method to scan operators.
     * <p>
     * Read until the next character is a symbol and the appended string is an operator.
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void scanOperator() throws IOException {
        StringBuilder builder = new StringBuilder().append((char) currentChar);
        do {
            read();
            String temp = builder.toString() + (char) currentChar;
            if (currentChar != CHAR_EOF &&
                !isSpace(currentChar) &&
                !isLetter(currentChar) &&
                !isDigit(currentChar) &&
                !isDelimiter(currentChar) &&
                isOperator(temp)) {
                builder.append((char) currentChar);
            } else {
                break;
            }
        } while (true);
        if (isOperator(builder.toString())) {
            rs.append(begin, end, TokenTypeImpl.OPERATOR, builder.toString());
        } else {
            rs.append(begin, end, TokenTypeImpl.ILLEGAL, builder.toString());
        }
    }

    /**
     * When the current character is in the other cases,
     * it will be called by the {@link LexerImpl#scan()} method to scan illegal token.
     * <p>
     * Read until the next character is not another illegal symbol.
     * Since the starting character is illegal, the token type is always illegal.
     *
     * @throws IOException If an I/O error occurs when read the next character.
     */
    private void scanOthers() throws IOException {
        StringBuilder builder = new StringBuilder().append((char) currentChar);
        do {
            read();
            if (currentChar != CHAR_EOF &&
                !isSpace(currentChar) &&
                !isLetter(currentChar) &&
                !isDigit(currentChar) &&
                !isDelimiter(currentChar) &&
                !isOperatorPrefix(currentChar)) {
                builder.append((char) currentChar);
            } else {
                break;
            }
        } while (true);
        rs.append(begin, end, TokenTypeImpl.ILLEGAL, builder.toString());
    }

    /**
     * It checks if the delimiters of braces, brackets, and parentheses are appearing in pair.
     * If not, print errors.
     */
    private void checkPairDelimiters() {
        if (countBrace < 0) {
            System.out.printf("Brace error: %d '{' excepted.\n", -countBrace);
        } else if (countBrace > 0) {
            System.out.printf("Brace error: %d '}' excepted.\n", countBrace);
        }
        if (countBrack < 0) {
            System.out.printf("Bracket error: %d '[' excepted.\n", -countBrack);
        } else if (countBrack > 0) {
            System.out.printf("Bracket error: %d ']' excepted.\n", countBrack);
        }
        if (countParen < 0) {
            System.out.printf("Parenthesis error: %d '(' excepted.\n", -countParen);
        } else if (countParen > 0) {
            System.out.printf("Parenthesis error: %d ')' excepted.\n", countParen);
        }
    }

    /**
     * Read the content to do lexical scan, which is the core method of a lexical scanner.
     * <p>
     * The checking procedure is:
     * <ol>
     *     <li>Check EOF.</li>
     *     <li>Skip white-spaces.</li>
     *     <li>Check if the character is a slash to scan comment.</li>
     *     <li>Check if the character is a sharp to scan preprocessor.</li>
     *     <li>Check if the character is a single quote to scan character.</li>
     *     <li>Check if the character is a double quote to scan string.</li>
     *     <li>Check if the character is a digit to scan numbers.</li>
     *     <li>Check if the character is a letter or underline to scan keywords or identifiers.</li>
     *     <li>Check if the character is a delimiter to scan delimiter.</li>
     *     <li>Check if the character is an operator prefix to scan operator.</li>
     *     <li>Illegal token.</li>
     *     <li>Pair delimiters.</li>
     * </ol>
     *
     * @return The Lexer object itself for chain call.
     * @throws IOException If an I/O error occurs when reading.
     */
    @Override
    public Lexer scan() throws IOException {
        read();
        do {
            begin = new Position(position.getIndex(), position.getRow(), position.getColumn());
            if (currentChar == CHAR_EOF) {
                break;
            } else if (isSpace(currentChar)) {
                read();
            } else if (currentChar == CHAR_SLASH) {
                scanComment();
            } else if (currentChar == CHAR_SHARP) {
                scanPreprocessor();
            } else if (currentChar == CHAR_SINGLE_QUOTE) {
                scanCharacter();
            } else if (currentChar == CHAR_DOUBLE_QUOTE) {
                scanString();
            } else if (isDigit(currentChar)) {
                scanDigits();
            } else if (isLetter(currentChar) || currentChar == CHAR_UNDERLINE) {
                scanLetters();
            } else if (isDelimiter(currentChar)) {
                scanDelimiter();
            } else if (isOperatorPrefix(currentChar)) {
                scanOperator();
            } else {
                scanOthers();
            }
        } while (true);
        checkPairDelimiters();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lexer print() {
        System.out.println(ResultSetFactory.toString(rs));
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Check the filename suffix, write the {@code ResultSet}
     * in different formats of string to the target file.
     *
     * @see ResultSetFactory#toHtml(ResultSet)
     * @see ResultSetFactory#toMarkdown(ResultSet)
     * @see ResultSetFactory#toString(ResultSet)
     */
    @Override
    public Lexer write(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        if (Pattern.matches(PATTERN_HTML, filename)) {
            writer.write(ResultSetFactory.toHtml(rs));
        } else if (Pattern.matches(PATTERN_MARKDOWN, filename)) {
            writer.write(ResultSetFactory.toMarkdown(rs));
        } else {
            writer.write(ResultSetFactory.toString(rs));
        }
        writer.close();
        System.out.printf("Results has been written to %s successfully\n", filename);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void close() throws IOException {
        reader.close();
    }

}
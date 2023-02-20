package cn.edu.bjtu.lexer;

/**
 * Referring to {@link java.sql.ResultSet},
 * a {@code ResultSet} object is designed to store the lexical scanner results.
 * <p>
 * To traverse the a {@code ResultSet} object and get the results:
 * <pre>
 *     rs.first()
 *     while(rs.next()) {
 *         Position begin = rs.getBeginPosition();
 *         Position end = rs.getEndPosition();
 *         TokenType type = rs.getTokenType();
 *         String token = rs.getToken();
 *     }
 * </pre>
 * Similarly, to traverse a {@code ResultSet} object and get the results:
 * <pre>
 *     rs.last();
 *     while(rs.previous()) {
 *         Position begin = rs.getBeginPosition();
 *         Position end = rs.getEndPosition();
 *         TokenType type = rs.getTokenType();
 *         String token = rs.getToken();
 *     }
 * </pre>
 */
public interface ResultSet {

    /**
     * Append result to a {@code ResultSet} object.
     *
     * @param begin The beginning position of the result.
     * @param end   The end position of the result.
     * @param type  The type of token of the result.
     * @param token The token of the result.
     */
    void append(Position begin, Position end, TokenType type, String token);

    /**
     * Get to the beginning of the {@code ResultSet} by setting cursor to the first position.
     */
    void first();

    /**
     * Get to the end of the {@code ResultSet} by setting cursor to the last position.
     */
    void last();

    /**
     * Move the cursor to the next by increase the value of cursor.
     *
     * @return {@code true} when the cursor has not moved to the end.
     */
    boolean next();

    /**
     * Move the cursor to the previous by decrease the value of cursor.
     *
     * @return {@code true} when the cursor has not moved to the beginning.
     */
    boolean previous();

    /**
     * Get the beginning position of the result by the current cursor.
     *
     * @return Beginning {@link Position} object of the result.
     */
    Position getBeginPosition();

    /**
     * Get the end position of the result by the current cursor.
     *
     * @return end {@link Position} object of the result.
     */
    Position getEndPosition();

    /**
     * Get the token type of the result by the current cursor.
     *
     * @return {@link TokenType} object of the result.
     */
    TokenType getTokenType();

    /**
     * Get the token of the result by the current cursor,
     *
     * @return Token of the result in string.
     */
    String getToken();

}
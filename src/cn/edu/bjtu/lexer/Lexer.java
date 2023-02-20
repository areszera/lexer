package cn.edu.bjtu.lexer;

import java.io.IOException;

/**
 * A {@code Lexer} object is a lexical scanner.
 * According to the rules of the target language, implement the methods to do lexical scan.
 */
public interface Lexer {

    /**
     * Read the content to do lexical scan, which is the core method of a lexical scanner.
     * <p>
     * The {@link java.io.FileReader} object is suggested to use for storing content,
     * if so, inside the method the structure can be designed as:
     * <pre>
     *     do {
     *         int c = reader.read();
     *         if (c == -1) {
     *             break;
     *         } else if (condition_1) {
     *             // do something
     *         } else if (condition_2) {
     *             // do something
     *         } else {
     *             // do something
     *         }
     *     } while(true);
     * </pre>
     *
     * @return The {@code Lexer} object itself for chain call.
     * @throws IOException If an I/O error occurs when reading.
     */
    Lexer scan() throws IOException;

    /**
     * Print the results of lexical scan.
     *
     * @return The {@code Lexer} object itself for chain call.
     * @throws IOException If an I/O error occurs when reading.
     */
    Lexer print() throws IOException;

    /**
     * Write the results of lexical scan.
     *
     * @param filename Name of the target file to write.
     * @return The {@code Lexer} object itself for chain call.
     * @throws IOException If an I/O error occurs when writing.
     */
    Lexer write(String filename) throws IOException;

    /**
     * Close the stream or reader that used in the {@code Lexer} object.
     *
     * @throws IOException If an I/O error occurs when closing.
     */
    void close() throws IOException;

}
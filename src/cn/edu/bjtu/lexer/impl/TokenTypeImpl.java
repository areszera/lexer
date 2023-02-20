package cn.edu.bjtu.lexer.impl;

import cn.edu.bjtu.lexer.TokenType;

/**
 * TokenTypeImpl is an enumerated class that listed all the token types for the lexical scanner.
 */
public enum TokenTypeImpl implements TokenType {

    /**
     * Illegal tokens, e.g., strings start with '@', illegal number 1.234.5, etc.
     */
    ILLEGAL,

    /**
     * Inline comment starts with two slashes: {@code //}.
     */
    INLINE_COMMENT,

    /**
     * Block comment start and end with a slash and a start: {@code /*}.
     */
    BLOCK_COMMENT,

    /**
     * Preprocessor tokens starts with a sharp: {@code #}.
     */
    PREPROCESSOR,

    /**
     * Identifier tokens, like function names, variables, etc.
     */
    IDENTIFIER,

    /**
     * Keyword tokens, e.g., {@code if}, {@code else}, etc.
     */
    KEYWORD,

    /**
     * Integer tokens, e.g., 12345.
     */
    NUMBER_INTEGER,

    /**
     * Fraction tokens, e.g., 123.45, 123F.
     */
    NUMBER_FLOAT,

    /**
     * Long integer tokens, e.g., 12345L.
     */
    NUMBER_LONG,

    /**
     * Long-long integer tokens, e.g., 12345LL.
     */
    NUMBER_LONG_LONG,

    /**
     * Unsigned integer tokens, e.g., 12345U.
     */
    NUMBER_UNSIGNED,

    /**
     * Octal integer tokens, e.g., 01234.
     */
    NUMBER_OCTAL,

    /**
     * Hexadecimal integer tokens, e.g., 0x123.
     */
    NUMBER_HEXADECIMAL,

    /**
     * Character tokens, e.g., 'a'.
     * Tokens will be recognized as character only when rounded by '\'' i one line and the length is 1.
     */
    CHARACTER,

    /**
     * String tokens, e.g., "abc".
     * Tokens will be recognized as string only when round by '"' in one line.
     */
    STRING,

    /**
     * Operator tokens, e.g., {@code +}, {@code -}, {@code ==}, etc.
     */
    OPERATOR,

    /**
     * Delimiter tokens, e.g., {@code ,}, {@code :}, {@code (}, etc.
     */
    DELIMITER

}
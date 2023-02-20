package cn.edu.bjtu.lexer;

/**
 * A {@code Position} object is designed to store the
 * index, row, and column of a character in lexical scan.
 */
public final class Position {

    private int index;
    private int row;
    private int column;

    /**
     * Constructs a new {@code Position} object with given numbers of
     * index, row, and column.
     *
     * @param index  Index number of the character.
     * @param row    Row number of the character.
     * @param column Column number of the character.
     */
    public Position(int index, int row, int column) {
        this.index = index;
        this.row = row;
        this.column = column;
    }

    /**
     * Constructs a new {@code Position} object with default numbers of
     * 0, 1, and 0 for index, row, and column, respectively.
     */
    public Position() {
        this.index = 0;
        this.row = 1;
        this.column = 0;
    }

    /**
     * Getter for the index number of the character.
     *
     * @return Index number in {@code int} of the character.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter for the row number of the character.
     *
     * @return Row number in {@code int} of the character.
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for the column number of the character.
     *
     * @return Column number in {@code int} of the character.
     */
    public int getColumn() {
        return column;
    }

    /**
     * When the character has been read to the next and the character is not CR ({@code \r}) or LF ({@code \n}),
     * use this method to increase the index and column number.
     *
     * @see Position#nextLine()
     */
    public void nextChar() {
        this.index++;
        this.column++;
    }

    /**
     * When the character has been read to the next and the character is LF ({@code \n}),
     * use ths method to increase the index and row number, and set the column number to 0.
     * It is remarkable that in different systems, the line separators are different:
     * <ul>
     *     <li>Windows: CRLF ({@code \r\n})</li>
     *     <li>Unix and macOS: LF ({@code \n})</li>
     *     <li>Classic Mac OS: CR ({@code \r})</li>
     * </ul>
     * Thus, this method only handles the position. To use this method properly,
     * the systems should be considered because of the differences of line separators.
     *
     * @see Position#nextChar()
     */
    public void nextLine() {
        this.index++;
        this.row++;
        this.column = 0;
    }

}
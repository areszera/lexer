package cn.edu.bjtu.lexer.impl;

import cn.edu.bjtu.lexer.Position;
import cn.edu.bjtu.lexer.ResultSet;
import cn.edu.bjtu.lexer.TokenType;

import java.util.ArrayList;

/**
 * The {@code ResultSetImpl} class is the implementation of the {@link ResultSet} interface.
 */
public final class ResultSetImpl implements ResultSet {

    private final ArrayList<Result> results = new ArrayList<>();
    private int cursor = -1;

    /**
     * {@inheritDoc}
     */
    @Override
    public void append(Position begin, Position end, TokenType type, String token) {
        results.add(new Result(begin, end, type, token));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void first() {
        cursor = -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void last() {
        cursor = results.size() - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean next() {
        if (cursor < results.size() - 1) {
            cursor++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean previous() {
        if (cursor >= 0) {
            cursor--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getBeginPosition() {
        if (0 <= cursor && cursor < results.size()) {
            return results.get(cursor).begin;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getEndPosition() {
        if (0 <= cursor && cursor < results.size()) {
            return results.get(cursor).end;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenType getTokenType() {
        if (0 <= cursor && cursor < results.size()) {
            return results.get(cursor).type;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken() {
        if (0 <= cursor && cursor < results.size()) {
            return results.get(cursor).token;
        } else {
            return null;
        }
    }

    /**
     * A {@code Result} object stores beginning position, end position, token type, and token of a list of result.
     * The {@code ArrayList} of {@code Result} objects forms the storing part of the {@code ResultSet} object.
     */
    private static class Result {

        public Position begin;
        public Position end;
        public TokenType type;
        public String token;

        public Result(Position begin, Position end, TokenType type, String token) {
            this.begin = begin;
            this.end = end;
            this.type = type;
            this.token = token;
        }

    }

}
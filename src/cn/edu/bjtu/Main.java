package cn.edu.bjtu;

import cn.edu.bjtu.lexer.impl.LexerImpl;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new LexerImpl("sample.c")
                .scan()
//                .write("result.html")
//                .write("result.md")
//                .write("result.txt")
                .print()
                .close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
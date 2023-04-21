package org.example;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        String string = OperacjePlikowe.wczytajZpliku("Huffman/test.txt");
        Huffman huff = new Huffman(string);
        String code = huff.codeEverything(string);
        System.out.println(code);

    }
}
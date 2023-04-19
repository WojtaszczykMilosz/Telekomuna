package org.example;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        String string = OperacjePlikowe.wczytajZpliku("Huffman/test.txt");
//        System.out.println(string);
//        System.out.println();
        Huffman huff = new Huffman(string);

//        huff.codeFromFileToFile("/Users/miloszwojtaszczyk/JAVAprojects/Telekomuna/Huffman/test.txt","/Users/miloszwojtaszczyk/JAVAprojects/Telekomuna/Huffman/codeTest.txt");
//        huff.decodeFromFileToFile("/Users/miloszwojtaszczyk/JAVAprojects/Telekomuna/Huffman/codeTest.txt","/Users/miloszwojtaszczyk/JAVAprojects/Telekomuna/Huffman/test2.txt");
        String code = huff.codeEverything(string);
        System.out.println(code);
//        System.out.println(code.toCharArray().length);
//        char d = 'ś';
//        Huffman huff2 = new Huffman();
//        String decode = huff2.decodeEveything(code);
//        System.out.println();
//        System.out.println(decode);
//
//        OperacjePlikowe.zapiszDoPliku("/Users/miloszwojtaszczyk/JAVAprojects/Telekomuna/Huffman/testCode.txt",code);
//        String af = OperacjePlikowe.wczytajKodZpliku("/Users/miloszwojtaszczyk/JAVAprojects/Telekomuna/Huffman/testCode.txt");
//        System.out.println(af.length());
//        System.out.println(af);
//        System.out.println(code);
//        System.out.println(af.equals(code));
//        System.out.println(new Huffman().decodeEveything(af));
    }
}
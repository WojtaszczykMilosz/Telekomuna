package org.example;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Huffman to fajny ziomek");
//        Node n1 = new Node('a',15);
//        Node n2 = new Node('b',35);
//        Node n3 = new Node(n1,n2);
//        Node n4 = new Node('c',5);
//        Node n5 = new Node('d',15);
//        Node n6 = new Node(n4,n5);
//        Node n7 = new Node(n6,n3);
//        PriorityQueue<Node> queue = new PriorityQueue<>();
//        queue.add(n1);
//        queue.add(n3);
//        queue.add(n6);
//        queue.add(n5);
//        queue.add(n7);
//        queue.add(n2);
//        queue.add(n4);
//        while(!queue.isEmpty()) {
//            System.out.println(queue.poll().getAmount());
//        }

        String string = "Bogurodzica dziewica,\n" +
                "Bogiem sławiena Maryja.\n" +
                "U twego syna, Gospodzina,\n" +
                "matko zwolena, Maryja!\n" +
                "Zyszczy nam, spuści nam.\n" +
                "Kyrie eleison.";
        byte[] b = string.getBytes();

        Huffman huff = new Huffman();
        huff.createAmountMap(string);
        huff.printAmount();
        huff.createTree();
        System.out.println(huff.root.getAmount());
        huff.createDict("",huff.root);
        System.out.println(huff.dict.entrySet());
        String s = huff.code(string);
        System.out.println(string);
        System.out.println(s.length());
        System.out.println(b.length * 8);


        System.out.println(huff.decode(s));


    }
}
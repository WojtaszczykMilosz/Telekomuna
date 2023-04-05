package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Huffman to fajny ziomek");
        Node n1 = new Node('a',15);
        Node n2 = new Node('b',35);
        Node n3 = new Node(n1,n2);
        Node n4 = new Node('c',5);
        Node n5 = new Node('d',15);
        Node n6 = new Node(n4,n5);
        Node n7 = new Node(n6,n3);
        System.out.println(n7.getAmount());

    }
}
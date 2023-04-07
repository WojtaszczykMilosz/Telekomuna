package org.example;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {

    private HashMap<Character,Integer> amountMap = new HashMap<>();
    public HashMap<Character,String> dict = new HashMap<>();
    public Node root;

    public void printAmount(){
        System.out.println(amountMap.entrySet());
    }
    public void createAmountMap(String string){
        char[] chars = string.toCharArray();
        for (char c:chars) {
            if(amountMap.containsKey(c)){
                int amount = amountMap.get(c) + 1;
                amountMap.replace(c,amount);
            } else {
                amountMap.put(c,1);
            }
        }
    }

    public void createTree(){

        PriorityQueue<Node> queue = new PriorityQueue<>();

        for(char c : amountMap.keySet()){
            queue.add(new Node(c,amountMap.get(c)));
        }

        while (queue.size() > 1){
            queue.add( new Node(queue.poll(),queue.poll()));

        }
        root = queue.poll();
    }

    public void createDict(String string, Node node){
        if(node.isIsLeaf()){
            dict.put(node.getCharacter(),string);
            return;
        }
        createDict(string.concat("0"),node.getLeftNode());
        createDict(string.concat("1"),node.getRightNode());

    }

    public String code(String string){
        char[] chars = string.toCharArray();
        StringBuilder wyj = new StringBuilder();
        for(char c:chars){
            wyj.append(dict.get(c));
        }
        return wyj.toString();
    }
    public String decode(String string) {
        char[] chars = string.toCharArray();
        StringBuilder wyj = new StringBuilder();

        Node iterate = root;
        for(char c :chars){
            if (c == '0'){
                iterate = iterate.getLeftNode();
            }else {
                iterate = iterate.getRightNode();
            }
            if(iterate.isIsLeaf()){
                wyj.append(iterate.getCharacter());
                iterate = root;
            }
        }
        return wyj.toString();
    }
}

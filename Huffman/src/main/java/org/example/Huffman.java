package org.example;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {

    private HashMap<Character,Integer> amountMap = new HashMap<>();
    private HashMap<Character,String> dict = new HashMap<>();
    private Node root;
    public Huffman(){}
    public Huffman(String string){
        createAmountMap(string);
        createTree();
        createDict("",root);
    }
//    public void printAmount(){
//        System.out.println(amountMap.entrySet());
//    }
    private void createAmountMap(String string){
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

    private void createTree(){

        PriorityQueue<Node> queue = new PriorityQueue<>();

        for(char c : amountMap.keySet()){
            queue.add(new Node(c,amountMap.get(c)));
        }

        while (queue.size() > 1){
            queue.add( new Node(queue.poll(),queue.poll()));

        }
        root = queue.poll();
    }

    private void createDict(String string, Node node){
        if(node.isIsLeaf()){
            dict.put(node.getCharacter(),string);
            return;
        }
        createDict(string.concat("0"),node.getLeftNode());
        createDict(string.concat("1"),node.getRightNode());

    }

    private String code(String string){
        char[] chars = string.toCharArray();
        StringBuilder wyj = new StringBuilder();
        for(char c:chars){
            wyj.append(dict.get(c));
        }
        return wyj.toString();
    }
    private String decode(String string) {
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

    private String saveTree(String string,Node node) {
        if(node.isIsLeaf()){
            string += '1';
            byte b = (byte) node.getCharacter();
            StringBuilder stringBuilder = new StringBuilder(string);
            for(int i = 0; i < 8; i++) {
                stringBuilder.append((char) (Operacje.getBit(b, i) + '0'));
            }
            string = stringBuilder.toString();
            return string;
        }

        string += '0';
        string = saveTree(string,node.getLeftNode());
        string = saveTree(string,node.getRightNode());
        return string;
    }
    private Node readTree(Integer i,char[] chars){
        if(chars[i] == '1') {
            i++;
            byte b = 0;
            for(int x = 0;x<8;x++){
                b = Operacje.setBit(b,x,Integer.parseInt(String.valueOf(chars[x + i])));
            }
            i += 8;
            char c = (char) b;

            return new Node(c,i);
        }
        i++;
        Node node = new Node();
        node.setLeftNode(readTree(i,chars));
        node.setRightNode(readTree(node.getLeftNode().getAmount(),chars));
        node.setAmount(node.getRightNode().getAmount());
        return node;
    }
    public String codeEverything(String string){
        String wyj = saveTree("",root);
        return wyj + code(string);

    }

    public String decodeEveything(String string) {

        Huffman huff = new Huffman();
        huff.root = readTree(0,string.toCharArray());
        StringBuilder s = new StringBuilder();
        char[] chars = string.toCharArray();
        for(int i = huff.root.getAmount();i<chars.length;i++){
            s.append(chars[i]);
        }

        return huff.decode(s.toString());
    }
    public void codeFromFileToFile(String plik1, String plik2){
        String string = OperacjePlikowe.wczytajZpliku(plik1);
        String code = codeEverything(string);
        OperacjePlikowe.zapiszDoPliku(plik2,code);
    }
    public void decodeFromFileToFile(String plik1, String plik2){
        String string = OperacjePlikowe.wczytajKodZpliku(plik1);
        String code = decodeEveything(string);
        OperacjePlikowe.zapiszDoPlikuTekst(plik2,code);
    }
}

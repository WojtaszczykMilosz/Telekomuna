package org.example;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {


    private static Socket sock;

    private static void SocketInit(String host) throws IOException {
        sock = new Socket(host,Server.PORT);
    }

    private static void SendFileThroughSocket(String filePath) throws IOException {
        byte[] plik = OperacjePlikowe.wczytajPlik(filePath);
        DataOutputStream os =new DataOutputStream(sock.getOutputStream());
        os.writeInt(plik.length);
        os.write(plik);
        os.close();
        sock.close();
    }

    private static void RunMenu(){
        Scanner scanner = new Scanner(System.in);
        int wybor = -1;
        while (wybor != 0) {
            PrintChoiceMessage();
            wybor = scanner.nextInt();
            HandleChoice(wybor);
        }
        scanner.close();
    }
    private static void HandleChoice(int choice) {
        Huffman huf;
        String wiadomosc;
        Scanner scanner = new Scanner(System.in);
        switch (choice) {
            case 1 -> {
                SendingMessage();
            }
            case 2 -> {
                System.out.println("Podaj wiadomość do zakodowania:");
                wiadomosc = scanner.next();
                scanner.nextLine();
                System.out.println(wiadomosc);
                huf = new Huffman(wiadomosc);
                System.out.print("ZAKODOWANA WIADOMOŚĆ: ");
                System.out.println(huf.codeEverything(wiadomosc));

            }
            case 3 -> {
                System.out.println("Podaj wiadomość do dekodowania:");
                wiadomosc = scanner.next();
                scanner.nextLine();
                huf = new Huffman(wiadomosc);
                System.out.print("DEKODOWANA WIADOMOŚĆ: ");
                System.out.println(huf.decodeEveything(wiadomosc));
            }
            case 4 -> {
                String plik = OperacjePlikowe.wczytajZpliku("Huffman\\test.txt");
                huf = new Huffman(plik);
                huf.codeFromFileToFile("Huffman\\test.txt","Huffman\\zakodowany.txt");
                System.out.println("Zakodowany plik text.txt zapisano do pliku zakodowany.txt");
            }
            case 5 -> {
                String plik = OperacjePlikowe.wczytajZpliku("Huffman\\codeTest.txt");
                huf = new Huffman(plik);
                huf.decodeFromFileToFile("Huffman\\codeTest.txt","Huffman\\odkodowany.txt");
                System.out.println("Odkodowany plik codeTest.txt zapisano do pliku odkodowany.txt");
            }

        }

    }


    private static void SendingMessage() {
        try {
            System.out.println("Podaj adres servera: ");
            Scanner scanner = new Scanner(System.in);
            String adres = scanner.next();
            scanner.nextLine();
            SocketInit(adres);
            String string = OperacjePlikowe.wczytajZpliku("Huffman/test.txt");
            Huffman huff = new Huffman(string);
            String code = huff.codeEverything(string);
            OperacjePlikowe.zapiszDoPliku("Huffman\\kodDoWysłania.txt",code);
            SendFileThroughSocket("Huffman\\kodDoWysłania.txt");
            System.out.println("Plik zostal wyslany");
        } catch (IOException e ) {

        }
    }
    private static void PrintChoiceMessage(){
        System.out.println("CLIENT - wysyłanie pliku");
        System.out.println("1. Wyślij zakodowany plik do servera");
        System.out.println("2. Zakoduj wiadomość");
        System.out.println("3. Dekoduj wiadomość");
        System.out.println("4. Zakoduj plik z wiadomość");
        System.out.println("5. Dekoduj plik z zakodowaną wiadomością");
        System.out.println("0. WYJSCIE");

    }

    public static void main(String[] args){
        RunMenu();
    }

}

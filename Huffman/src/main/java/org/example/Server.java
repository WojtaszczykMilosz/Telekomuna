package org.example;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket sock;
    private static DataInputStream is;

    public static final int PORT = 5050;


    public static void OdbierzPlik() throws IOException{
        int length = is.readInt();
        if (length > 0) {
            byte[] bytes = new byte[length];
            is.readFully(bytes);
            String plik = "odebrane.txt";
            OperacjePlikowe.zapiszPlik(plik,bytes);
            System.out.println("Zakodowany plik o długości: " + length + " bajtów");
            for(int i = 0;i < length;i++){
                System.out.print(bytes[i]);
            }
            System.out.println();
            DekodujPlik(plik);
            is.close();
            sock.close();
            serverSocket.close();
        }
    }

    private static void DekodujPlik(String sciezkaPliku){
        Huffman decoder = new Huffman();
        decoder.decodeFromFileToFile(sciezkaPliku,"odkodowane.txt");
        String s = OperacjePlikowe.wczytajZpliku("odkodowane.txt");
        System.out.println("Odkodowana wiadomość o długości: " + s.toCharArray().length + " bajtów");
        System.out.println(s);
    }

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Oczekiwanie na klienta...");
            sock = serverSocket.accept();
            System.out.println("Nawiązno połączenie");
            is =new DataInputStream(sock.getInputStream());
            OdbierzPlik();
        } catch (IOException e) {

        }

    }
}

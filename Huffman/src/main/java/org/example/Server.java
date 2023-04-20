package org.example;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.GarbageCollectorMXBean;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
            DekodujPlik(plik);
        }
    }

    private static void DekodujPlik(String sciezkaPliku){
        Huffman decoder = new Huffman();
        decoder.decodeFromFileToFile(sciezkaPliku,"brrrr.txt");
    }

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            sock = serverSocket.accept();
            is =new DataInputStream(sock.getInputStream());
            OdbierzPlik();
        } catch (IOException e) {

        }

    }

    public static InetAddress getLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }
}

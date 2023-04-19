package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {






    public static void main(String[] args){
        try {
            Socket sock = new Socket(InetAddress.getLocalHost(),5050);

            byte[] plik = OperacjePlikowe.wczytajPlik("Huffman\\test.txt");
            DataOutputStream os =new DataOutputStream(sock.getOutputStream());
            os.writeInt(plik.length);
            os.write(plik);
        } catch (IOException e) {

        }
    }

}

package org.example;


import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Odbieranie {

    static XModem xmodem;
    private static void ustawTryb(SerialPort port){
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Wybierz");
        System.out.println("1 - Podstawowa wersja");
        System.out.println("2 - Wersja crc");
        try {
            String wybor = reader.readLine();
            switch (wybor){
                case "1":
                    xmodem = new XModem(port);
                    break;
                case "2":
                    xmodem = new XModemCRC(port);
                    break;
                default:
                    xmodem = new XModem(port);
            }
        }
        catch (IOException e) {

        }
    }

    public static void main(String[] args){
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING,500,0);

        xmodem.Odbierz("odebrane.docx");

        port.closePort();

    }
}

package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        SerialPort ports = SerialPort.getCommPorts()[2];
        byte[] tab = {(byte)255,5,6};
        ports.writeBytes(tab,tab.length);
        System.out.println(ports.getSystemPortName());
//        XModem test = new XModem();

//        System.out.println(new byte[]{23);

    }
}

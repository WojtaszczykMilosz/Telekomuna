package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        SerialPort ports = SerialPort.getCommPorts()[2];
        byte[] tab = {-1,5,6};
        ports.writeBytes(tab,tab.length);
        System.out.println(ports.getSystemPortName());
        XModem test = new XModem(ports);
        XModem.Watek a = test.new Watek(2);
        a.start();
        System.out.println(test.sumBytes(tab));

    }
}

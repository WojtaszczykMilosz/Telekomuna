package org.example;


import com.fazecast.jSerialComm.SerialPort;

public class Main {



    public static void main(String[] args) {


        SerialPort ports = SerialPort.getCommPorts()[2];
        byte[] tab = {-1,5,6};
        byte[] dick = {0,0,0};
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        ports.openPort();
        ports.writeBytes(tab,2);
        port.readBytes(dick,2);
        for (byte a : dick)
            System.out.println(a);
//        ports.writeBytes(tab,tab.length);
//        System.out.println(ports.getSystemPortName());
//        XModem test = new XModem(ports);
//        XModem.Watek a = test.new Watek(2);
//        a.start();
//        try {
//            in.read(tab);
//        } catch (IOException e){
//
//        }
//        System.out.println(tab[0]);

    }
}

package org.example;

import com.fazecast.jSerialComm.SerialPort;

public class Wysylanie {
    public static void main(String[] args){
        SerialPort port = SerialPort.getCommPorts()[2];
        XModem x = new XModem(port);

        x.Wyslij("test.txt");

    }
}

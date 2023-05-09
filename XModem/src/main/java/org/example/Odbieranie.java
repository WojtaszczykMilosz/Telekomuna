package org.example;

import com.fazecast.jSerialComm.SerialPort;

public class Odbieranie {
    public static void main(String[] args){
        SerialPort port = SerialPort.getCommPorts()[2];
        XModem x = new XModem(port);
        x.Odbierz();

    }
}

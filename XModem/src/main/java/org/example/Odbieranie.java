package org.example;


import com.fazecast.jSerialComm.SerialPort;

public class Odbieranie {
    public static void main(String[] args){
        SerialPort port = SerialPort.getCommPorts()[2];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING,500,0);
        XModem x = new XModem(port);
        x.Odbierz("odebrane.txt");

        port.closePort();

    }
}

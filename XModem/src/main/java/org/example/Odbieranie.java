package org.example;


import com.fazecast.jSerialComm.SerialPort;

public class Odbieranie {
    public static void main(String[] args){
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING,500,0);
        XModem x = new XModem(port);
        x.Odbierz("odebrane.docx");

        port.closePort();

    }
}

package org.example;


import com.fazecast.jSerialComm.SerialPort;

public class Wysylanie {
    public static void main(String[] args){
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        XModem x = new XModem(port);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING,500,0);
//        SerialPort.getCommPorts()[1].writeBytes(new byte[]{0x15},1);
        x.Wyslij("XModem\\doc.docx");
//        port.readBytes(b,1);
        port.closePort();
    }
}

package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.*;
import java.util.Arrays;

public class XModem {

    XModem(SerialPort port){
        naglowek[0] = SOH;
        this.port = port;
    }

    public void Odbierz(){
        byte[] blok = new byte[128];
        byte[] checkSumRCV = {0};
        byte checkSumCalc;

        byte[] wiadomosc =  new byte [128];



        port.writeBytes(new byte[]{NAK},1);


        while (true) {
            port.readBytes(naglowek,1);
            if (naglowek[0] == EOT) {
                port.writeBytes(new byte[] {ACK},1);
                break;
            }
            port.readBytes(naglowek,2);

            port.readBytes(blok,128);
            port.readBytes(checkSumRCV,1);

            checkSumCalc = (byte)sumBytes(blok);

            if (checkSumCalc != checkSumRCV[0]) {
                port.writeBytes(new byte[]{NAK},1);
                continue;
            }
            port.writeBytes(new byte[]{ACK},1);

        }
    }



    public void Wyslij(String file){
        byte[] plik = OperacjePlikowe.wczytajZpliku(file);
        byte[][] bloki = podzielBajty(plik);
        int iloscBlokow = (plik.length - 1) / wielkoscBloku + 1;


        byte[] odpowiedz = {NAK};

        for (byte i = 1; i <= iloscBlokow; i++) {

            while(odpowiedz[0] != ACK){
                //preparation
                tworzNaglowek(i);

                //sending
                wyslijPakiet(naglowek,bloki[i-1]);

                //response
                port.readBytes(odpowiedz,1);
            }

        }
        odpowiedz[0] = NAK;
        while(odpowiedz[0] != ACK) {
            port.writeBytes(new byte[]{EOT}, 1);
            port.readBytes(odpowiedz,1);
        }
    }

    public void wyslijPakiet(byte[] naglowek,byte[] blok) {
        byte[] checkSuma = {(byte)sumBytes(blok)};
        port.writeBytes(naglowek,naglowek.length);
        port.writeBytes(blok,wielkoscBloku);
        port.writeBytes(checkSuma,1);

    }

    public byte[][] podzielBajty(byte[] plik) {
        int iloscBlokow = (plik.length - 1) / wielkoscBloku + 1;
        byte[][] wyj = new byte[iloscBlokow][];
        for (byte i = 0; i < iloscBlokow; i++) {
            wyj[i] = Arrays.copyOfRange(plik,0,128);
        }

        return wyj;
    }

    public int sumBytes(byte[] array) {
        int result = 0;
        for (byte a: array) {
            result = (( (a&0xFF) + result) % 256);

        }
        return result;
    }

    private void tworzNaglowek(byte i){
        naglowek[1] = i;
        naglowek[2] = (byte) (255 - i);
    }

    private SerialPort port;
    private byte[] naglowek = new byte[3];
    private int wielkoscBloku = 128;
    private byte SOH = 0x1;
    private byte EOT = 0x4;
    private byte ACK = 0x6;
    private byte NAK = 0x15;
    private byte CAN = 0x18;
    private byte C = 0x43;
}

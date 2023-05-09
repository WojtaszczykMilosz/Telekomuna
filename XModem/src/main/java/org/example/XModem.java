package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.*;
import java.util.Arrays;

public class XModem {

    XModem(SerialPort port){
        naglowek[0] = SOH;
        this.port = port;
    }


    public class Watek extends Thread {
        private long miliseconds;
        public Watek(long seconds) {
            miliseconds = 1000*seconds;
        }

        public void run() {
            try {
                Thread.sleep(miliseconds);
            } catch ( InterruptedException e ) {

            }
            if (odebranoWiadomosc)
                Break();
        }

    }

    private void Break(){
        System.exit(21);
    }

    public void Odbierz(){
        byte[] blok = new byte[128];
        byte[] wiadomosc =  new byte [128];

        byte[] checkSumRCV = {0};
        byte checkSumCalc;


        iloscBledow = 0;
        odebranoWiadomosc = false;

        new Watek(timeout).start();

        port.writeBytes(new byte[]{NAK},1);

        int iteracja = 1;
        while (true) {
            wiadomosc = Arrays.copyOfRange(wiadomosc,0,iteracja*128);

            if (iloscBledow > MAX_BLEDOW) Break();

            port.readBytes(naglowek,1);
            odebranoWiadomosc = true;
            if (naglowek[0] == EOT ) {
                port.writeBytes(new byte[] {ACK},1);
                break;
            } else if (naglowek[0] != SOH) {
                iloscBledow++;
                continue;
            }


            port.readBytes(naglowek,2);

            if (naglowek[0] != ~naglowek[1]) {
                iloscBledow++;
                continue;
            }

            port.readBytes(blok,wielkoscBloku);
            port.readBytes(checkSumRCV,1);

            checkSumCalc = sumBytes(blok);

            if (checkSumCalc != checkSumRCV[0]) {
                port.writeBytes(new byte[]{NAK},1);
                iloscBledow++;
                continue;
            }

            port.writeBytes(new byte[]{ACK},1);

            System.arraycopy(blok,0,wiadomosc,iteracja*wielkoscBloku,wielkoscBloku);
            iteracja++;
        }
        port.writeBytes(new byte[]{ACK},1);
        port.writeBytes(new byte[]{ACK},1);
        port.writeBytes(new byte[]{ACK},1);

    }

    private boolean SprawdzCzyToKoniec(){
        if (naglowek[0] == EOT ) {
            port.writeBytes(new byte[] {ACK},1);
            return true;
        } else if (naglowek[0] != SOH) {
            return true;
        }
        return false;
    }

    public void Wyslij(String file){

        byte[] plik = OperacjePlikowe.wczytajZpliku(file);
        byte[][] bloki = podzielBajty(plik);

        int iloscBlokow = (plik.length - 1) / wielkoscBloku + 1;

        iloscBledow = 0;
        odebranoWiadomosc = false;

        new Watek(timeout).start();
        czekajNaSygnal();

        if (iloscBledow == MAX_BLEDOW) return;


        byte[] odpowiedz = {0};
        for (byte i = 1; i <= iloscBlokow; i++) {

            while(true) {
                tworzNaglowek(i);

                wyslijPakiet(naglowek, bloki[i - 1]);

                port.readBytes(odpowiedz, 1);
                if (odpowiedz[0] != ACK)
                    iloscBledow++;
                else
                    break;
            }
        }
        odpowiedz[0] = NAK;
        while(odpowiedz[0] != ACK) {
            port.writeBytes(new byte[]{EOT}, 1);
            port.readBytes(odpowiedz,1);
        }
    }

    public void czekajNaSygnal(){
        byte[] odp = {0};
        while (odp[0] != NAK && iloscBledow < MAX_BLEDOW) {
            port.readBytes(odp, 1);
            odebranoWiadomosc = true;
            iloscBledow++;
        }
    }

    public void wyslijPakiet(byte[] naglowek,byte[] blok) {
        byte[] checkSuma = {sumBytes(blok)};
        port.writeBytes(naglowek,naglowek.length);
        port.writeBytes(blok,wielkoscBloku);
        port.writeBytes(checkSuma,1);

    }

    public byte[][] podzielBajty(byte[] plik) {
        int iloscBlokow = (plik.length - 1) / wielkoscBloku + 1;
        byte[][] wyj = new byte[iloscBlokow][];
        for (byte i = 0; i < iloscBlokow; i++) {
            wyj[i] = Arrays.copyOfRange(plik,i,128*i);
        }

        return wyj;
    }

    public byte sumBytes(byte[] array) {
        byte result = 0;
        for (byte a: array) {
            result = (byte)( (a + result) % 256);

        }
        return result;
    }

    private void tworzNaglowek(byte i){
        naglowek[1] = i;
        naglowek[2] = (byte)~i;
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

    private boolean odebranoWiadomosc = false;
    private long timeout = 20;
    private int iloscBledow = 0;
    private int MAX_BLEDOW = 15;
}

package org.example;


import com.fazecast.jSerialComm.SerialPort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.AnnotatedArrayType;
import java.util.Arrays;

public class XModem {

    XModem(SerialPort port) {
        naglowek[0] = SOH;
        this.port = port;
        in = new DataInputStream(port.getInputStream());
        out = new DataOutputStream(port.getOutputStream());
    }


    public class Watek extends Thread {
        private long miliseconds;

        public Watek(long seconds) {
            miliseconds = 1000 * seconds;
        }

        public void run() {
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {

            }
            if (!odebranoWiadomosc)
                Break();
        }

    }

    private void Break() {
        System.exit(21);
    }

    public void Odbierz(String plik) {
        byte[] blok = new byte[wielkoscBloku];
        byte[] wiadomosc = new byte[wielkoscBloku];




        byte[] checkSumRCV = {0};
        byte checkSumCalc;


        iloscBledow = 0;
        odebranoWiadomosc = false;



        new Watek(timeout).start();

        Write(new byte[]{NAK});
        System.out.println("Wysłano");
        int iteracja = 1;
        while (true) {
            resizeArray(wiadomosc);

            if (iloscBledow > MAX_BLEDOW) {
                System.out.println(iteracja);
                Break();
            }

            odebranoWiadomosc = true;
            Recv(naglowek,0,1);
            if (naglowek[0] == EOT) {
                Write(new byte[]{ACK});
                break;
            } else if (naglowek[0] != SOH) {

                iloscBledow++;
                continue;
            }

            Recv(naglowek, 1, 2);

            if (naglowek[1] != ~naglowek[2]) {
                System.out.println("cos nie gra wrr");
                iloscBledow++;
                continue;
            }

            Recv(blok);
            Recv(checkSumRCV);

            checkSumCalc = sumBytes(blok);

            if (checkSumCalc != checkSumRCV[0]) {
                Write(new byte[]{NAK});
                iloscBledow++;
                System.out.println("chujek");
                continue;
            }


            Write(new byte[]{ACK});
            System.out.println("ACK PO ODEBRANIU");
            System.arraycopy(blok, 0, wiadomosc, (iteracja - 1) * wielkoscBloku, wielkoscBloku);
            iteracja++;
        }
        Write(new byte[]{ACK});
        Write(new byte[]{ACK});
        Write(new byte[]{ACK});

        OperacjePlikowe.zapiszDoPliku(plik, wiadomosc);
    }

//    private boolean SprawdzCzyToKoniec() {
//        if (naglowek[0] == EOT) {
//            port.writeBytes(new byte[]{ACK}, 1);
//            return true;
//        } else if (naglowek[0] != SOH) {
//            return true;
//        }
//        return false;
//    }

    private void resizeArray(byte[] tab) {
        byte[] temp = new byte[tab.length+wielkoscBloku];
        temp = Arrays.copyOfRange(tab,0,tab.length);
        tab = temp;
    }

    public void Wyslij(String file) {

        byte[] plik = OperacjePlikowe.wczytajZpliku(file);
        byte[][] bloki = podzielBajty(plik);





        int iloscBlokow = (plik.length - 1) / wielkoscBloku + 1;

        iloscBledow = 0;
        odebranoWiadomosc = false;

        new Watek(timeout).start();
        czekajNaSygnal();

        if (iloscBledow >= MAX_BLEDOW) return;


        byte[] odpowiedz = {0};
        for (byte i = 1; i <= iloscBlokow; i++) {

            while (true) {
                System.out.println("tworze naglowek");
                tworzNaglowek(i);
                System.out.println("stworzony - wysylam pakiet");

                wyslijPakiet(naglowek, bloki[i - 1]);

                Recv(odpowiedz);
                if (odpowiedz[0] != ACK) {
                    iloscBledow++;
                    System.out.println("e?");
                }
                else
                    break;
            }
        }
        System.out.println("PO petli");
        odpowiedz[0] = NAK;
        while (odpowiedz[0] != ACK) {
            Write(new byte[]{EOT});
            System.out.println("WYSLANY EOT - CZEKAM");
            Recv(odpowiedz);
        }
    }

    public void czekajNaSygnal() {
        byte[] odp = {0};
        while (odp[0] != NAK) {
            Recv(odp);
            System.out.println("Czekam");
            odebranoWiadomosc = true;
        }

    }

    public void wyslijPakiet(byte[] naglowek, byte[] blok) {
        byte[] checkSuma = {sumBytes(blok)};
        Write(naglowek);
        Write(blok);
        Write(checkSuma);

    }

    public byte[][] podzielBajty(byte[] plik) {
        int iloscBlokow = (plik.length - 1) / wielkoscBloku + 1;
        byte[][] wyj = new byte[iloscBlokow][wielkoscBloku];

        for (byte i = 0; i < iloscBlokow; i++) {
            wyj[i] = Arrays.copyOfRange(plik, i, 128 * (i+1));

        }
        return wyj;
    }

    public byte sumBytes(byte[] array) {
        byte result = 0;
        for (byte a : array) {
            result = (byte) ((a + result) % 256);

        }
        return result;
    }

    private void tworzNaglowek(byte i) {
        naglowek[1] = i;
        naglowek[2] = (byte) ~i;
    }


    private void Write(byte[] tab) {
        try {
            out.write(tab);
        } catch (IOException e) {

        }
    }

    private void Recv(byte[] tab, int off, int len) {

        try {
            in.readFully(tab, off, len);
            System.out.println("2");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void Recv(byte[] tab) {
        try {
            in.readFully(tab);
            System.out.println("1");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private SerialPort port;


    private DataInputStream in;

    private DataOutputStream out;

    private byte[] naglowek = new byte[3];
    private final int wielkoscBloku = 128;
    private final byte SOH = 0x1;
    private final byte EOT = 0x4;
    private final byte ACK = 0x6;
    private final byte NAK = 0x15;
    private final byte CAN = 0x18;
    private final byte C = 0x43;

    private boolean odebranoWiadomosc = false;
    private final long timeout = 5;
    private int iloscBledow = 0;
    private final int MAX_BLEDOW = 15;
}

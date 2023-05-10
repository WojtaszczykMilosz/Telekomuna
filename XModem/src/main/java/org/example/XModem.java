package org.example;


import com.fazecast.jSerialComm.SerialPort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class XModem {

    XModem(SerialPort port) {
        naglowek[0] = SOH;
        in = new DataInputStream(port.getInputStream());
        out = new DataOutputStream(port.getOutputStream());
    }




    private void Break() {
        System.exit(21);
    }

    public void Odbierz(String plik) {
        byte[] blok = new byte[wielkoscBloku];
        byte[] wiadomosc = new byte[wielkoscBloku];

        byte[] checkSumRCV = {0};

        iloscBledow = 0;



        Write(new byte[]{NAK});
        System.out.println("Wysłano NAK");
        int iteracja = 1;
        while (true) {
            wiadomosc = Arrays.copyOf(wiadomosc, wielkoscBloku * iteracja);
            if (iloscBledow > MAX_BLEDOW) {
                Break();
            }


            Recv(naglowek, 0, 1);

            System.out.print("odebralem naglowek - ");
            System.out.println(naglowek[0]);
            if (naglowek[0] == EOT) {
                Write(new byte[]{ACK});
                break;
            } else if (naglowek[0] != SOH) {
                System.out.println(naglowek[0]);
                System.out.println("czemu?");
                iloscBledow++;
                continue;
            }

            Recv(naglowek, 1, 2);
            System.out.print("numer bloku - ");
            System.out.print(naglowek[1]);
            System.out.println(naglowek[2]);
            if (naglowek[1] != ~naglowek[2]) {
                iloscBledow++;
                continue;
            }

            Recv(blok);
            System.out.print("blok");
            for (byte i: blok) {
                System.out.print(i);
            }
            System.out.println();

            Recv(checkSumRCV);
            if (!checkReceivedCheckSum(checkSumRCV[0],blok)) {
                iloscBledow++;
                continue;
            }

            Write(new byte[]{ACK});
            System.out.println("ACK PO BLOKU");
            System.arraycopy(blok, 0, wiadomosc, (iteracja - 1) * wielkoscBloku, wielkoscBloku);
            iteracja++;
        }
        Write(new byte[]{ACK});
        Write(new byte[]{ACK});
        Write(new byte[]{ACK});

        OperacjePlikowe.zapiszDoPliku(plik, wiadomosc);
    }
    private boolean checkReceivedCheckSum(byte checkSum, byte[] blok){

        byte checkSumCalc ;

        System.out.print("checksuma");
        System.out.println(checkSum);

        checkSumCalc = sumBytes(blok);
        System.out.println(checkSumCalc);
        if (checkSumCalc != checkSum) {
            Write(new byte[]{NAK});
            iloscBledow++;
            return false;
        }
        return true;
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


    public void Wyslij(String file) {

        byte[] plik = OperacjePlikowe.wczytajZpliku(file);
        byte[][] bloki = podzielBajty(plik);

        int iloscBlokow = (plik.length - 1) / wielkoscBloku + 1;
        System.out.println(plik.length);
        iloscBledow = 0;

        czekajNaSygnal();


        byte[] odpowiedz = {0};
        for (int i = 1; i <= iloscBlokow; i++) {
            odpowiedz[0] = NAK;
            System.out.println("kolejna iteracja");
            while (true) {
                if (iloscBledow > MAX_BLEDOW) {
                    Break();
                }
                wyslijPakiet(bloki[i - 1],(byte)i);
                Recv(odpowiedz);
                System.out.print("odebralem odpowiedz - ");
                System.out.println(odpowiedz[0]);
                if (odpowiedz[0] != ACK) {
                    iloscBledow++;
                    System.out.println("e?");
                } else
                    break;


            }
            System.out.println(i);
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
        while (odp[0] != NAK && iloscBledow < MAX_BLEDOW) {
            Recv(odp);
            iloscBledow ++;
            System.out.println("Czekam");
        }
        if (iloscBledow >= MAX_BLEDOW) {
            Break();
        }
        iloscBledow = 0;
    }

    public void wyslijPakiet(byte[] blok,int numerBloku) {
        byte[] checkSuma = {sumBytes(blok)};
        tworzNaglowek((byte)numerBloku);
        Write(naglowek);
        System.out.print("naglowek - ");
        for (byte i: naglowek) {
            System.out.print(i);
        }
        System.out.println();
        Write(blok);
        System.out.print("blok - ");
        for (byte i: blok) {
            System.out.print(i);
        }
        System.out.println();
        Write(checkSuma);
        System.out.print("check sum - ");
        System.out.println(checkSuma[0]);

    }

    public byte[][] podzielBajty(byte[] plik) {
        int iloscBlokow = (plik.length - 1) / wielkoscBloku + 1;
        byte[][] wyj = new byte[iloscBlokow][wielkoscBloku];

        for (int i = 0; i < iloscBlokow; i++) {
            wyj[i] = Arrays.copyOfRange(plik, 128*i, 128 * (i + 1));
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
        naglowek[0] = SOH;
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
        } catch (IOException e) {
            Arrays.fill(tab, off, len, (byte) 0);
        }
    }

    private void Recv(byte[] tab) {
        try {
            in.readFully(tab);
        } catch (IOException e) {
            Arrays.fill(tab, 0, tab.length, (byte) 0);
        }
    }





    private final DataInputStream in;

    private final DataOutputStream out;

    private final byte[] naglowek = new byte[3];
    private final int wielkoscBloku = 128;
    private final byte SOH = 0x1;
    private final byte EOT = 0x4;
    private final byte ACK = 0x6;
    private final byte NAK = 0x15;
    private final byte CAN = 0x18;
    private final byte C = 0x43;

    private int iloscBledow = 0;
    private final int MAX_BLEDOW = 15;
}

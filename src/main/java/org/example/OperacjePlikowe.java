package org.example;

import java.io.*;
import java.nio.ByteBuffer;

public class OperacjePlikowe {

    public static int[] wczytajZpliku(String plik){
        try (
                InputStream sin = new FileInputStream(plik)
        ) {

            long rozmiar = new File(plik).length();
            byte[] bytes = new byte[(int) rozmiar];
            sin.read(bytes);
            String formatted = "";

            for (byte c: bytes){
                formatted += String.format("%8s", Integer.toBinaryString(c & 0xFF))
                        .replace(' ', '0');
            }

            int[] wyj = new int[formatted.length()];
            for (int i = 0; i<formatted.length();i++){
                wyj[i] =  formatted.charAt(i) - '0';
            }



            return wyj;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    public static void zapiszDoPliku(String plik, byte[] wej){
        try (
                OutputStream sou = new FileOutputStream(plik)
        ) {
            sou.write(wej);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

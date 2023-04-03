package org.example;

import java.io.*;
import java.math.BigInteger;

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
    public static void zapiszDoPliku(String plik, int[] wej){
        try (
                OutputStream sou = new FileOutputStream(plik)
        ) {

            String str = "";

            for (int i = 0; i < wej.length; i++)
            {
                str+=wej[i];
            }



            byte[] tab = bigToArray(new BigInteger(str,2),(str.length() - 1)/8 + 1);

            sou.write(tab);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] trim(byte[] trimmed){

        if (trimmed[0] != 0){
            return trimmed;
        }
        byte[] tab = new byte[trimmed.length-1];
        System.arraycopy(trimmed,1,tab,0,trimmed.length-1);

        return tab;
    }

    public static byte[] bigToArray(BigInteger big, int bytes){

        if(big.toByteArray().length > bytes){
            return trim(big.toByteArray());
        } else if (big.toByteArray().length < bytes) {
            byte[] tab = new byte[bytes];
            byte[] tab1 = big.toByteArray();
            for (int i = bytes - tab1.length; i < bytes;i++)
                tab[i] = tab1[i-(bytes-tab1.length)];
            return tab;
        } else {
            return big.toByteArray();
        }

    }
}

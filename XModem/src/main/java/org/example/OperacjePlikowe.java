package org.example;

import java.io.*;

public class OperacjePlikowe {

    public static byte[] wczytajZpliku(String plik){
        try (
                InputStream sin = new FileInputStream(plik)
        ) {

            long rozmiar = new File(plik).length();
            byte[] wyj = new byte[(int) rozmiar];

            sin.read(wyj);

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

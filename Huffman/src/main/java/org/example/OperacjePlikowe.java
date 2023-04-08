package org.example;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OperacjePlikowe {

    public static String wczytajZpliku(String plik){
       try(InputStream in = new FileInputStream(plik);
       BufferedReader br = new BufferedReader(new InputStreamReader(in,StandardCharsets.UTF_8))){

           String str = null;
           StringBuilder s = new StringBuilder();
           while ((str = br.readLine()) != null){
               s.append(str);
               s.append('\n');
           }
           return s.toString();
       }catch (Exception e ){
           System.out.println(plik);
       }
       return null;
    }
    public static void zapiszDoPliku(String plik, String wej){
        char[] chars = wej.toCharArray();
        int reszta = chars.length % 16;
        boolean podzielnosc = reszta == 0;
        int maxindex = (chars.length - 1) / 16 + 1;
        byte[] bytes;
        if(podzielnosc){
            bytes = new byte[maxindex * 2];
        } else {
            bytes = new byte[maxindex * 2 + 1];
            bytes[maxindex * 2] = (byte) (chars.length % 8);
        }
        for(int i =0;i<maxindex * 16;i++){
            if (chars.length > i){
                Operacje.setBit(bytes,i,chars[i] - '0');
            }else {
                Operacje.setBit(bytes,i,0);
            }
        }
        try (
                OutputStream sou = new FileOutputStream(plik)
        ) {
            sou.write(bytes);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static String wczytajKodZpliku(String plik){
        try (
                InputStream sin = new FileInputStream(plik)
        ) {

            long rozmiar = new File(plik).length();
            byte[] bytes = new byte[(int) rozmiar];
            sin.read(bytes);
            boolean podzielnosc = bytes.length % 2 == 0;
            int reszta;

            char[] chars;
            if(!podzielnosc) {
                reszta = bytes[bytes.length - 1];
                chars = new char[(bytes.length - 1) * 8 - 8 + reszta];
            } else {
                chars = new char[bytes.length * 8];
            }
            for(int i =0;i< chars.length;i++){
                chars[i] =(char) ('0' + Operacje.getBit(bytes,i));
            }
            return String.valueOf(chars);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void zapiszDoPlikuTekst(String plik, String wej){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(plik));
            writer.write(wej);

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

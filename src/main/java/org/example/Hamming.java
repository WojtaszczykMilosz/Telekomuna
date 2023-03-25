package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hamming {

    private static int[][] H2 = {
            {1,1,0,1,1,0,1,0, 1,0,0,0},
            {1,0,1,1,0,1,1,0, 0,1,0,0},
            {0,1,1,1,0,0,0,1, 0,0,1,0},
            {0,0,0,0,1,1,1,1, 0,0,0,1}
    };

    private static int[][] H = {
    {0,1,1,1,1,1,1,1, 1,0,0,0,0,0,0,0},
    {1,0,1,1,1,1,1,1, 0,1,0,0,0,0,0,0},
    {1,1,0,1,1,1,1,1, 0,0,1,0,0,0,0,0},
    {1,1,1,0,1,1,1,1, 0,0,0,1,0,0,0,0},
    {1,1,1,1,0,1,1,1, 0,0,0,0,1,0,0,0},
    {1,1,1,1,1,0,1,1, 0,0,0,0,0,1,0,0},
    {1,1,1,1,1,1,0,1, 0,0,0,0,0,0,1,0},
    {1,1,1,1,1,1,1,0, 0,0,0,0,0,0,0,1},
    };

    public static int[] sprawdz(int[] wiadomosc){
        int x;

        int[] E= new int[H.length];
        if (wiadomosc.length != H[0].length) return null;
        for (int i = 0; i< H.length ; i++) {
            x=0;
            for (int j = 0; j<wiadomosc.length; j++){
                x += H[i][j] * wiadomosc[j];
            }
            E[i] = x%2;
        }

        return E;
    }


    private static int[] getColumn(int i){
        int[] wyj = new int [H.length];
        for (int r = 0; r<H.length; r++){
           wyj[r] = H[r][i];
        }
        return wyj;
    }



    public static int[] XOR(int[] a, int[] b) {
        int[] wyj = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            wyj[i] = a[i] ^ b[i];
        }
        return wyj;
    }


    public static int[] napraw(int[] wiadomosc) {
        int[] E = sprawdz(wiadomosc);
        if (E == null) return wiadomosc;
        int[] wyj = wiadomosc;
        int[] col;
        for (int i = 0; i < H.length; i++) {
            col = getColumn(i);
            if (Arrays.compare(col,E) == 0) {
                wyj[i] = wiadomosc[i] ^ 1;
            }
        }

        return wyj;
    }

    public static int[] napraw2(int[] wiadomosc) {
        int[] E = sprawdz(wiadomosc);
        if (E == null) return wiadomosc;

        int[] wyj = Arrays.copyOf(wiadomosc,wiadomosc.length);
        int[] col;
        for (int i = 0; i < H.length; i++) {
            col = getColumn(i);
            if (Arrays.compare(col,E) == 0) {
                wyj[i] = wiadomosc[i] ^ 1;
            }
        }
        if (Arrays.compare(wyj,wiadomosc) != 0)
            return wyj;
        int[] tab;

        for (int i = 0; i < H.length; i++) {
            col = getColumn(i);

            for (int j = i+1; j < H.length; j++){
                tab = XOR(col,getColumn(j));
                if (Arrays.compare(tab,E) == 0) {
                    wyj[i] = wiadomosc[i] ^ 1;
                    wyj[j] = wiadomosc[j] ^ 1;
                }
            }
        }
        return wyj;
    }

    public static int[] koduj(int[] wiadomosc){

        int rozmiar = (wiadomosc.length - 1)/8 + 1;
        int[] wyj;
        if (wiadomosc.length % 8 == 0) {
            wyj = new int[rozmiar*8 + rozmiar*H2.length];
        } else{
            wyj = new int[rozmiar*8 + rozmiar*H2.length + 1];
            wyj[rozmiar*8 + rozmiar*H2.length]  = wiadomosc.length%8;
        }


        int[] buffor;
        int x = 0;
        int suma;
        for (int i = 0; i < rozmiar; i++){
            buffor = Arrays.copyOfRange(wiadomosc,i*8,(i+1)*8);
            for (int j = 0; j < buffor.length; j++)
                wyj[x + j] = buffor[j];
            for (int j = 0; j < H2.length; j++) {
                suma = 0;
                for (int k = 0 ;k < 8;k++)
                    suma += buffor[k] * H2[j][k];
                wyj[x+buffor.length + j] = suma%2;
            }
            x+=8+H2.length;


        }

        return wyj;
    }



    public static int[] dekoduj(int[] wiadomosc){

        int rozmiar = (wiadomosc.length - 1)/(8+H2.length) + 1;
        int[] wyj;
        boolean nierownosc = wiadomosc.length % (8+H2.length) != 0;
        if(nierownosc){
            rozmiar -= 1;
            wyj = new int[rozmiar*8 - wiadomosc[wiadomosc.length - 1]];

        } else {
            wyj = new int[rozmiar*8];
        }

        int[] buffor;
        int x = 0;
        for (int i = 0; i < rozmiar; i++){
            buffor = Arrays.copyOfRange(wiadomosc,i*(8+H2.length),(i+1)*(8+H2.length));
            buffor = napraw(buffor);
            if (!(i == rozmiar - 1 && nierownosc)) {
                for (int j = 0; j < buffor.length-H2.length; j++)
                    wyj[x + j] = buffor[j];
            } else {
                for (int j = 0; j < wiadomosc[wiadomosc.length-1]; j++)
                    wyj[x + j] = buffor[j];
            }


                x+=8;
            }


        return wyj;
    }






}

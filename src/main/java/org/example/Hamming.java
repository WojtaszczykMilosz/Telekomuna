package org.example;

import java.util.Arrays;

public class Hamming {

//    private static final int[][] H2 = {
//            {1,1,0,1,1,0,1,0, 1,0,0,0},
//            {1,0,1,1,0,1,1,0, 0,1,0,0},
//            {0,1,1,1,0,0,0,1, 0,0,1,0},
//            {0,0,0,0,1,1,1,1, 0,0,0,1}
//    };

    private static final int[][] H = {
            {1, 1, 1, 1, 0, 0, 0, 0,     1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 1, 0, 0,     0, 1, 1, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0,     0, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 1, 1, 0,     0, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 0, 0, 1,     0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 1, 0, 1, 0, 1,     0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 0, 1, 1,     0, 0, 0, 0, 0, 0, 1, 0},
            {1, 1, 1, 0, 0, 1, 1, 1,     0, 0, 0, 0, 0, 0, 0, 1}};
    public static void sprawdzH(){
        for (int i =0;i<H[0].length;i++){
            for (int j =0;j<H[0].length;j++){
                if(i != j){
                    if(Arrays.compare(getColumn(i),getColumn(j))==0){
                        System.out.println("Błąd");
                        return;
                    }
                }
            }
        }


        for (int i =0;i<H[0].length;i++){
            for (int j =0;j<H[0].length;j++){
                if(i != j) {
                    int[] tab = XOR(getColumn(i), getColumn(j));
                    for(int y =0;y<H[0].length;y++){
                        if(Arrays.compare(getColumn(y),tab)==0){
                            System.out.println("Błąd");
                            return;
                        }
                    }
                }
            }
        }


        for (int i =0;i<H[0].length;i++){
            for (int j =0;j<H[0].length;j++){
                if(i != j) {
                    int[] tab = XOR(getColumn(i), getColumn(j));
                    for (int y =0;y<H[0].length;y++){
                        for (int x =0;x<H[0].length;x++){
                            if(x!= y) {
                                int[] tab2 = XOR(getColumn(x), getColumn(y));
                                if((x != i && y != j) && (x != j && y != i)){
                                    if(Arrays.compare(tab,tab2) == 0){
                                        System.out.println("Błąd");
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Brak błędu");
    }
    public static int[] sprawdz(int[] wiadomosc){
        int x;
        int[] E= new int[H.length];

        if (wiadomosc.length != H[0].length) {
            return null;
        }

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
        if (E == null) {
            return wiadomosc;
        }
        int[] wyj = Arrays.copyOf(wiadomosc,H.length);
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
        if (E == null) {
            return wiadomosc;
        }

        int[] wyj = Arrays.copyOf(wiadomosc,H.length);
        int[] wej = Arrays.copyOf(wiadomosc,H.length);
        int[] col;
        for (int i = 0; i < H.length; i++) {
            col = getColumn(i);
            if (Arrays.compare(col,E) == 0) {
                wyj[i] = wiadomosc[i] ^ 1;
            }
        }
        if (Arrays.compare(wyj,wej) != 0) {
            return wyj;
        }

        int[] tab;

        for (int i = 0; i < H.length; i++) {
            col = getColumn(i);

            for (int j = i+1; j < H[0].length; j++){
                tab = XOR(col,getColumn(j));
                if (Arrays.compare(tab,E) == 0) {
                    System.out.println(i);
                    System.out.println(j);
                    wyj[i] = wiadomosc[i] ^ 1;
                    if (j < wyj.length) {
                        wyj[j] = wiadomosc[j] ^ 1;
                    }
                }
            }
        }
        return wyj;
    }

    public static int[] koduj(int[] wiadomosc){

        int rozmiar = (wiadomosc.length - 1)/8 + 1;
        int[] wyj;
        if (wiadomosc.length % 8 == 0) {
            wyj = new int[rozmiar*8 + rozmiar*H.length];
        } else{
            wyj = new int[rozmiar*8 + rozmiar*H.length + 1];
            wyj[rozmiar*8 + rozmiar*H.length]  = wiadomosc.length%8;
        }


        int[] buffor;
        int x = 0;
        int suma;
        for (int i = 0; i < rozmiar; i++){
            buffor = Arrays.copyOfRange(wiadomosc,i*8,(i+1)*8);
            for (int j = 0; j < buffor.length; j++)
                wyj[x + j] = buffor[j];
            for (int j = 0; j < H.length; j++) {
                suma = 0;
                for (int k = 0 ;k < 8;k++)
                    suma += buffor[k] * H[j][k];
                wyj[x+buffor.length + j] = suma%2;
            }
            x+=8+H.length;


        }

        return wyj;
    }



    public static int[] dekoduj(int[] wiadomosc){

        int rozmiar = (wiadomosc.length - 1)/(8+H.length) + 1;
        int[] wyj;
        boolean nierownosc = wiadomosc.length % (8+H.length) != 0;
        if(nierownosc){
            rozmiar -= 1;
            wyj = new int[rozmiar*8 - wiadomosc[wiadomosc.length - 1]];

        } else {
            wyj = new int[rozmiar*8];
        }

        int[] buffor;
        int x = 0;
        for (int i = 0; i < rozmiar; i++){
            buffor = Arrays.copyOfRange(wiadomosc,i*(8+H.length),(i+1)*(8+H.length));
            buffor = napraw(buffor);
            if (!(i == rozmiar - 1 && nierownosc)) {
                for (int j = 0; j < buffor.length-H.length; j++)
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

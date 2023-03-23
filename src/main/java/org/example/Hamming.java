package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hamming {

    private static int[][] H = {
            {1,1,0,1,1,0,1,0, 1,0,0,0},
            {1,0,1,1,0,1,1,0, 0,1,0,0},
            {0,1,1,1,0,0,0,1, 0,0,1,0},
            {0,0,0,0,1,1,1,1, 0,0,0,1}
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




    public static int[] napraw(int[] wiadomosc,int[] errors) {
        int[] wyj = wiadomosc;

        for (int i = 0; i < H.length; i++) {
            if (Arrays.compare(H[i],errors) == 0) {
                wyj[i] = ~wiadomosc[i];
                System.out.println('a');
            }
        }

        return wyj;
    }
}

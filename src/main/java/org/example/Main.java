package org.example;

import java.util.Arrays;

public class Main {


    
    public static void main(String[] args) {
        int[] tab = {1,1,1,1,0,0,1,1, 0,1,1,0};
        int[] wynik = Hamming.sprawdz(tab);
        int[] nowe = Hamming.napraw(tab,wynik);

        for (int i = 0; i<wynik.length;i++)
            System.out.print(wynik[i]);
    }
}
package org.example;

import java.math.BigInteger;
import java.util.Arrays;

public class Main {


    
    public static void main(String[] args) {
        int[] tab = {1,1,0,1,0,0,1,1, 0,1,1,0};
        int[] tab2 = {1,0,1,1,0,0,1,0, 1,1,1,1,0,0,1,1};
        int[] wynik = Hamming.sprawdz(tab2);
        int[] nowe = Hamming.napraw2(tab2);
        int[] wynik2 = Hamming.sprawdz(nowe);



        for (int i = 0; i<tab.length;i++)
            System.out.print(tab[i]);

        System.out.println();


//
//        for (int i = 0; i<wynik.length;i++)
//            System.out.print(wynik[i]);
//
//        System.out.println();
//        for (int i = 0; i<nowe.length;i++)
//            System.out.print(nowe[i]);
//
//        System.out.println();
//
//        for (int i = 0; i<wynik2.length;i++)
//            System.out.print(wynik2[i]);
//        System.out.println();
//
        int[] plik = OperacjePlikowe.wczytajZpliku("test.txt");
        int[] tab3 = Hamming.koduj(tab);
        System.out.println();
        for (int i = 0; i<tab3.length;i++)
            System.out.print(tab3[i]);
        System.out.println();
        int[] tab4 = Hamming.dekoduj(tab3);
        for (int i = 0; i<tab4.length;i++)
            System.out.print(tab4[i]);
//        System.out.println(new BigInteger(plik).toString(2));

//        for (int a:plik)
//            System.out.print(a);
    }
}

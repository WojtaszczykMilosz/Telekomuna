package org.example;

import java.math.BigInteger;
import java.util.Arrays;

public class Main {


    
    public static void main(String[] args) {
        int[] tab = { 1,0,1,1,0,0,1,0, 0,1,1,0};
        int[] wej= { 1,0,1,1,0,0,1,0};
        int[] tab2 = {1,0,1,1,0,0,1,0, 1,1,1,0,0,0,1,1};
        int[] wynik = Hamming.sprawdz(tab2);
        int[] nowe = Hamming.napraw2(tab2);
        int[] wynik2 = Hamming.koduj(wej);




//        for(int i =0;i<nowe.length;i++){
//            System.out.print(nowe[i]);
//        }

//        for (int i = 0; i<tab.length;i++)
//            System.out.print(tab[i]);
//
//        System.out.println();


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
//        int[] plik = OperacjePlikowe.wczytajZpliku("doc.doc");
//        int[] tab3 = Hamming.koduj(plik);
//        int[] tab4 = Hamming.dekoduj(tab3);
//        OperacjePlikowe.zapiszDoPliku("test.doc",tab4);
//        System.out.println();
////        for (int i = 0; i<plik.length;i++)
////            System.out.print(plik[i]);
//        System.out.println();
//
//        System.out.println(
//
//        );
//        System.out.println();
//
//
//
//        System.out.println(plik.length%8);
//        System.out.println(plik.length);
//        System.out.println(tab4.length);
////        tab4 = Hamming.dekoduj(tab3);
////        for (int i = 0; i<tab4.length;i++)
////            System.out.print(tab4[i]);
//
//        System.out.println(
//
//        );

//        System.out.println(new BigInteger(plik).toString(2));

//        for (int a:plik)
//            System.out.print(a);
    }
}

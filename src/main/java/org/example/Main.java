package org.example;

public class Main {


    public static void wypiszWynik(int[] plik){
        for (int i = 0; i<plik.length;i++) {
            if ((i+1) % 9  == 0) System.out.print("         ");
            System.out.print(plik[i]);
        }
        System.out.println();
    }

    public static void wypiszKod(int[] plik){
        for (int i = 0; i<plik.length;i++) {
            if ((i+1) % 17  == 0) System.out.print(" ");
            System.out.print(plik[i]);
        }
        System.out.println();
    }


    public static void main(String[] args) {
        int[] wej= {1,0,1,1,0,0,1,0, 1,1,1,0,0,0,1,1};
        int[] tab2 = {0,1,1,1,0,0,1,0, 1,1,1,0,0,0,1,1};
        int[] wynik = Hamming.sprawdz(tab2);
        int[] nowe = Hamming.napraw(tab2);
        int[] wynik2 = Hamming.koduj(wej);



        wypiszKod(tab2);
        wypiszWynik(nowe);
        wypiszKod(wynik2);


        int[] plik = OperacjePlikowe.wczytajZpliku("test.txt");
        int[] tab3 = Hamming.koduj(plik);

        OperacjePlikowe.zapiszDoPliku("bin.bin",tab3);
        tab3 = OperacjePlikowe.wczytajZpliku("bin.bin");
        int[] tab4 = Hamming.dekoduj(tab3);

        OperacjePlikowe.zapiszDoPliku("zapis.txt",tab4);
    }
}

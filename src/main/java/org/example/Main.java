package org.example;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void wypiszKod(int[] plik){
        for (int i = 0; i<plik.length;i++) {
            if ((i+1) % 9 == 0) System.out.print(" ");
            System.out.print(plik[i]);
        }
        System.out.println();
    }
    public static void zmienBit(int[] wej,int index) {
        if(index<wej.length){
            wej[index] = wej[index] ^ 1;
        }
    }

    public static void main(String[] args) {

        int[] demo = {1,0,1,1,0,0,1,0};
        int[] demoKopia = {1,0,1,1,0,0,1,0};
        int[] wczytanyplik = Arrays.copyOf(demoKopia,8);
        int[] wczytanyZakodowanyPlik;

        Scanner scanner = new Scanner(System.in);
        int wybor;
        boolean warunek = true;
        System.out.println("---------MENU---------");
        while (warunek){
            System.out.println("Co chcesz zrobić?");
            System.out.println("1. Wypisz demo.");
            System.out.println("2. Zmien bit w demo.");
            System.out.println("3. Przywroc pierwotne demo.");
            System.out.println("4. Zakoduj demo.");
            System.out.println("5. Odkoduj demo razem z naprawą.");
            System.out.println("6. Wczytaj plik.");
            System.out.println("7. Zakoduj plik.");
            System.out.println("8. Dekoduj plik.");
            System.out.println("0. Wyjscie z programu.");
            wybor = scanner.nextInt();
            scanner.nextLine();

            switch (wybor) {
                case 1 -> {
                    System.out.println("DEMO:");
                    wypiszKod(demo);
                    System.out.println();
                }
                case 2 -> {
                    System.out.println("Który bit chcesz zmienić?");
                    int ktory = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("DEMO PRZED ZMIANA:");
                    wypiszKod(demo);
                    zmienBit(demo, ktory - 1);
                    System.out.println("DEMO:");
                    wypiszKod(demo);
                    System.out.println();
                }
                case 3 -> {
                    demo = Arrays.copyOf(demoKopia, 8);
                    System.out.println("DEMO:");
                    wypiszKod(demo);
                    System.out.println();
                }
                case 4 -> {
                    System.out.println("DEMO PRZED ZMIANA:");
                    wypiszKod(demo);
                    demo = Hamming.koduj(demo);
                    System.out.println("DEMO:");
                    wypiszKod(demo);
                    System.out.println();
                }
                case 5 -> {
                    demo = Hamming.napraw(demo);
                    System.out.println("DEMO POCZĄTKOWE:");
                    wypiszKod(demoKopia);
                    System.out.println("DEMO:");
                    wypiszKod(demo);
                    System.out.println();
                }
                case 6 -> {
                    wczytanyplik = OperacjePlikowe.wczytajZpliku("test.txt");
                }
                case 7 -> {
                    OperacjePlikowe.zapiszDoPliku("bin.bin",Hamming.koduj(wczytanyplik));
                }
                case 8 -> {
                    wczytanyZakodowanyPlik = OperacjePlikowe.wczytajZpliku("bin.bin");
                    OperacjePlikowe.zapiszDoPliku("odkodowany.txt",Hamming.dekoduj(wczytanyZakodowanyPlik));
                }
                default -> warunek = false;
            }
        }
    }
}

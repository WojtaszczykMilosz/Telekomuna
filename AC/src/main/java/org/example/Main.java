package org.example;

import java.io.IOException;

public class Main {

    private static void input(){
        try{
            System.in.read();
        } catch (IOException e) {

        }
    }

    private static void AudioRecordSession(){
        Przetwornik przetw = new Przetwornik();
        System.out.println("enter zeby przechwytywac");
        input();
        przetw.capture();

        System.out.println("enter zeby przerwac");
        input();
        przetw.stopRecording();
    }

    private static void AudioListenSession(){
        Przetwornik przetw = new Przetwornik();
        przetw.playAudioFromFile("AC\\odebranyplik.wav");
    }

    public static void main(String[] args) {

        AudioRecordSession();

        AudioListenSession();

    }
}
package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Przetwornik {
    private AudioFormat format;
    private TargetDataLine targetDataLine;


    private float sampleRate = 8000.0F;
    private int sampleSizeInBits = 16;
    private int channels = 1;
    private boolean signed = true;
    private boolean bigEndian = false;

    public Przetwornik() {
       this.format = new AudioFormat(sampleRate,sampleSizeInBits,channels,signed,bigEndian);
    }


    class ReceiveAndSave extends Thread {
        @Override
        public void run(){
            AudioFileFormat.Type typ = AudioFileFormat.Type.WAVE;
            File audio = new File("AC\\odebranyplik.wav");
            try{
                targetDataLine.open(format);
                targetDataLine.start();
                AudioSystem.write(new AudioInputStream(targetDataLine),typ,audio);
            }catch (LineUnavailableException | IOException e ){

            }


        }
    }

    public void capture() {
        try {
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            ReceiveAndSave recv = new ReceiveAndSave();

            recv.start();
        }catch (LineUnavailableException e ){

        }

    }
    public void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }

    public void playAudioFromFile(String path){
        File audio = new File(path);
        try  {
            Clip klip = AudioSystem.getClip();
            klip.open(AudioSystem.getAudioInputStream(audio));
            klip.start();
            Thread.sleep(klip.getMicrosecondLength() / 1000);
        } catch (Exception e){

        }

    }

}

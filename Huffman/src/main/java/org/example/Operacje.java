package org.example;

public class Operacje {

    public static int getBit(byte wej, int pos){

        return ((wej << (pos)) & 128) > 0 ? 1 : 0;
    }

    public static byte setBit(byte bajt, int ktoryBit, int wartosc){

        if(wartosc == 0){
            bajt = (byte)(~(128 >> ktoryBit) & bajt);
        }else {
            bajt = (byte)((128 >> ktoryBit) | bajt);
        }
        return bajt;
    }
    public static int getBit(byte[] wej, int pos){

        int ktoryBajt = pos / 8;
        int ktoryBit = pos % 8;
        byte bajt = wej[ktoryBajt];
        return ((bajt << (ktoryBit)) & 128) > 0 ? 1 : 0;
    }

    public static void setBit(byte[] wej, int pos, int wartosc){

        int ktoryBajt = pos / 8;
        int ktoryBit = pos % 8;
        byte bajt = wej[ktoryBajt];
        if(wartosc == 0){
            bajt = (byte)(~(128 >> ktoryBit) & bajt);
        }else {
            bajt = (byte)((128 >> ktoryBit) | bajt);
        }
        wej[ktoryBajt] = bajt;


    }
}

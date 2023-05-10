package org.example;


import com.fazecast.jSerialComm.SerialPort;

public class XModemCRC extends XModem {
    public XModemCRC(SerialPort port)
    {
        super(port);
        pierwszyZnak = C;
//        naglowek[0] = CAN;
        checkSum = new byte[2];
    }

    @Override
    protected boolean checkReceivedCheckSum(byte[] blok) {
        int checkSumCalc ;
        System.out.print("checksuma");
        checkSumCalc = sumBytes(blok);

        System.out.println(checkSumCalc);
        System.out.println((checkSum[0] & 0xFF));
        System.out.println((checkSum[1] & 0xFF));
        for (int i = 0; i < 2; i++) {
            if (((checkSumCalc >> 8 * (1-i)) & 0xFF) != (checkSum[i] & 0xFF)) {
                Write(new byte[]{NAK});
                iloscBledow++;
                return false;
            }
        }

        return true;
    }

    @Override
    public void wyslijPakiet(byte[] blok, int numerBloku) {
        int suma = sumBytes(blok);
        checkSum[0] = (byte)((suma >> 8) & 0xFF);
        checkSum[1] = (byte)(suma & 0xFF);
        tworzNaglowek((byte)numerBloku);
        Write(naglowek);
        System.out.print("naglowek - ");
        for (byte i: naglowek) {
            System.out.print(i);
        }
        System.out.println();
        Write(blok);
        System.out.print("blok - ");
        for (byte i: blok) {
            System.out.print(i);
        }
        System.out.println();
        Write(checkSum);
        System.out.print("check sum - ");
        System.out.println(suma);
    }

    @Override
    public int sumBytes(byte[] array) {
        int DZIELNIK = 0x1021;
        int crc, i;

        int index = 0;
        crc = 0;

        while (index < 128) {
            crc = crc ^ array[index] << 8;
            for (i = 0; i < 8; ++i)
            {

                if ((crc & 0x8000) != 0)
                    crc = (crc << 1) ^ DZIELNIK;
                else
                    crc = crc << 1;
            }
            index ++;
        }
        return (crc & 0xFFFF);
    }
}

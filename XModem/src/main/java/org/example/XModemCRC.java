package org.example;


import com.fazecast.jSerialComm.SerialPort;

public class XModemCRC extends XModem {
    public XModemCRC(SerialPort port)
    {
        super(port);
        pierwszyZnak = C;
        naglowek[0] = C;
        checkSum = new byte[2];
    }

    @Override
    protected boolean checkReceivedCheckSum(byte[] blok) {
        int checkSumCalc ;

        System.out.print("checksuma");

        checkSumCalc = sumBytes(blok);


        System.out.println(checkSumCalc);
        for (int i = 0; i < 2; i++) {
            if (((checkSumCalc >> 8 * i)& 0xFF) != checkSum[0]) {
                Write(new byte[]{NAK});
                iloscBledow++;
                return false;
            }
        }

        return true;
    }

    @Override
    public void wyslijPakiet(byte[] blok, int numerBloku) {
        super.wyslijPakiet(blok, numerBloku);
    }

    @Override
    public int sumBytes(byte[] array) {
        int result = 0;
        for (byte a : array) {
            result = ((a + result) % 65536);

        }
        return result;
    }
}

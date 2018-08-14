package SpectrumApp.java.SPE.Read;

import SpectrumApp.java.SPE.Interfaces.ReadSpectrum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Administrator on 7/20/17.
 */

public class ReadSpectrumTxt implements ReadSpectrum {
    public static final int CHANNELS = 16384;
    private String path;

    public ReadSpectrumTxt(String path) {
        this.path = path;
    }

    @Override
    public String[] readChannels() {
        String[] spe = new String[CHANNELS];

        try {
            File f = new File(this.path);
            BufferedReader b = new BufferedReader(new FileReader(f));
            //System.out.println("Reading file using Buffered Reader");

            int i;
            for (i = 0; i < CHANNELS + 5; i++) {
                if (i < 5) {
                    b.readLine();
                } else {
                    String readLine = b.readLine();
                    //System.out.println(readLine);
                    spe[i - 5] = readLine;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spe;
    }

    @Override
    public String[] readHead() {
        String[] head = new String[5];
        String[] readLine;
        try {
            File f = new File(this.path);
            BufferedReader b = new BufferedReader(new FileReader(f));
            //System.out.println("Reading file using Buffered Reader");

            int i = 0;

            while (i < 5) {
                readLine = (b.readLine().split("="));
                head[i] = readLine[1];
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return head;
    }
}

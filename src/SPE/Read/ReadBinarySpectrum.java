package SPE.Read;

import SPE.Interfaces.ReadSpectrum;
import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Renat Razumilov on 19/09/17.
 */
public class ReadBinarySpectrum implements ReadSpectrum {
    public static final int CHANNELS = 16384;
    private String path;

    public ReadBinarySpectrum(String path) {
        this.path = path;
    }

    @Override
    public String[] readHead() {
        List<String> head = new ArrayList<>();
        String readLine;
        String spectrumBegin[] = new String[2];
        spectrumBegin[0] = "";
        try {
            File f = new File(this.path);
            BufferedReader b = new BufferedReader(new FileReader(f));
            int counter = 0;
            while (!(spectrumBegin[0].equals("SPECTR"))) {
                counter++;
                readLine = (b.readLine());
                spectrumBegin = readLine.split("=");
                if (spectrumBegin.length == 2) {
                    //System.out.println(spectrumBegin[0] + "=\n" + spectrumBegin[1]);
                    //System.out.println("----------------------");
                    if (counter == 5) {
                        head.add(removePart(spectrumBegin[1], " ", 0));
                        head.add(removePart(
                                (removePart(spectrumBegin[1], " ", 1)), "\\.", 0));
                    }
                    if (counter == 7) {
                        head.add(spectrumBegin[1]);
                    }
                    if (counter == 8) {
                        head.add(spectrumBegin[1]);
                        head.add("16384");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transformToArray(head);
    }

    private String[] transformToArray(List<String> listArray) {
        String[] sData = listArray.stream()
                .map(i -> i.toString())
                .toArray(String[]::new);
        return sData;
    }

    @Override
    public String[] readChannels() {

        List<Integer> data = readBinary();
        int[] index = {1};
        String[] sData = data.stream()
                .map(i -> index[0]++ + "\t" + i.toString())
                .toArray(String[]::new);
        return sData;
    }

    private String removePart(String str, String delimeter, int partToReturn) {
        String[] newStr = str.split(delimeter);
        return newStr[partToReturn];
    }

    public List<Integer> readBinary() {
        byte[] data = getBytesArray(this.path);
        List<Integer> bSpectr = getDataFromBytes(data);
        return bSpectr;
    }

    private List<Integer> getDataFromBytes(byte[] data) {
        boolean readSpectrumAvilible = false;
        List<Integer> bTemp = new ArrayList<>();
        List<Integer> bSpectr = new ArrayList<>();
        InputStream input = new ByteArrayInputStream(data);
        try {
            int datax = input.read();
            while (datax != -1) {
                datax = input.read();
                if (!(readSpectrumAvilible)) {
                    if (datax == 0) {
                        readSpectrumAvilible = true;
                    }
                }

                if (readSpectrumAvilible) {
                    bTemp.add(datax);
                }

                if (bTemp.size() == 4) {
                    bSpectr.add(getResult(bTemp));
                    //System.out.println(getResult(bTemp));
                    bTemp.clear();
                }
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bSpectr;
    }

    private byte[] getBytesArray(String path) {
        File fe = new File(path);
        byte[] data = new byte[0];
        try {
            FileInputStream fis = new FileInputStream(fe);
            System.out.println("Total file size to read (in bytes) : "
                    + fis.available());
            byte[] sData = new byte[fis.available()];
            fis.read(sData);
            fis.close();
            data = sData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return data;
    }

    private int getResult(List<Integer> arr) {
        return arr.get(0) + (arr.get(1) * 256) + (arr.get(2) * 512) + (arr.get(3) * 1024);
    }

    public void saveToFile() {
        String path = "C:\\JAVA\\Projects\\src\\SPE\\Co60spe\\TestSpectrum.txt";
        BufferedWriter writer = null;
        boolean thrown = false;

        String[] testData = new String[16384];

        for (int i = 0; i < 16384; i++) {
            String newString1 = String.valueOf((i + 1));
            String newString2 = String.valueOf((i));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(newString1);
            stringBuilder.append("\t");
            stringBuilder.append(newString2);
            testData[i] = stringBuilder.toString();
        }

        try {
            {
                writer = new BufferedWriter(new FileWriter(path));
                for (String i : testData) {
                    writer.write(i);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            thrown = true;
            System.out.println("Error");
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
            }
        }
        if (!thrown) {
            System.out.println("Save completed successful");
        }
    }
}

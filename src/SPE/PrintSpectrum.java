package SPE;

import java.util.ArrayList;
import java.util.List;

public class PrintSpectrum {
    public static void main(String[] args){
        List<Integer> spectr = new ArrayList<>();
        spectr.add(3);
        spectr.add(2);
        spectr.add(1);
        spectr.add(4);
        spectr.add(5);
        spectr.add(6);
        spectr.add(12);
        spectr.add(5);
        spectr.add(4);
        spectr.add(2);
        spectr.add(3);
        spectr.add(2);
        spectr.add(1);
        spectr.add(3);
        spectr.add(2);
        spectr.add(5);
        spectr.add(2);
        spectr.add(1);
        spectr.add(4);
        spectr.add(2);

        printSpectrumHorizontal(spectr);
        System.out.println();

        printSpectrumVertical(spectr);

    }
    private static void printSpectrumVertical(List<Integer> spectr){
        String[] aPrint = new String[spectr.size()];
        int max = findMax(spectr);

        for (int i = max; i > 0; i--){
            int k = 0;
            for (Integer j:spectr){
                if (j>=i){
                    aPrint[k] = "*";
                }else {
                    aPrint[k] = " ";
                }
                System.out.print(aPrint[k]);
                k++;
            }
            System.out.println();
        }
    }

    private static void printSpectrumHorizontal(List<Integer> spectr){
        for (Integer i:spectr){
            String[] arr = new String[i];
            for (int j = 0; j < arr.length;j++){
                arr[j] = "*";
                System.out.print(arr[j]);
            }
        System.out.println();
        }

    }

    private static int findMax(List<Integer> spectr){
        int max = 0;
        for (Integer i:spectr){
            if (i>max){
                max = i;
            }
        }
        return max;
    }
}

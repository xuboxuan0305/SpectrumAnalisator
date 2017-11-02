package SPE.Analyser.PeakSearch;

public class Derivative {
    private final int[] dSmooth = {-10,-7,-5,-3,-1,0,1,3,5,7,10};
    private int[] input;

    public Derivative(int[] input) {
        int size = input.length;
        boolean matrixMatch = size == dSmooth.length;

        if(matrixMatch) {
            this.input = input;
        }else {
            System.out.println("Matrix not Match");
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int getDerivative(){
        int Deriv = 0;
        for (int i = 0; i < this.dSmooth.length; i++){
            Deriv += this.input[i] * this.dSmooth[i];
        }

        return Deriv;
    }
}

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); //Creating Scanner

        double[][] Edata = new double[8][2]; // Creating matrix for education datas

        Edata[0][0] = 0.6; Edata[0][1] = 0.5; Edata[1][0] = 0.2; Edata[1][1] = 0.4;
        Edata[2][0] = -0.3; Edata[2][1] = -0.5; Edata[3][0] = -0.1; Edata[3][1] = -0.1;
        Edata[4][0] = 0.1; Edata[4][1] = 0.1; Edata[5][0] = -0.2; Edata[5][1] = 0.7;
        Edata[6][0] = -0.4; Edata[6][1] = -0.2; Edata[7][0] = -0.6; Edata[7][1] = 0.3;

        int totalMatch = 0; // Variable for total match with target and output

        System.out.println("Education Starts....");

        Neuron neuron = new Neuron();  // Creating neuron

        Educate(Edata, neuron);  // Educating neuron

        System.out.println("How many data sets for test?: ");   // Taking Data sets for test
        int TdataCount = scanner.nextInt();
        double[][] Tdata = new double[TdataCount][2]; // Creating matrix for test Data

        System.out.println("Enter Test Data:");   // Taking test data from user
        for (int i = 0; i < TdataCount; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print("X" + (j + 1) + ":");
                Tdata[i][j] = scanner.nextDouble();
            }
        }

        for (int i = 0; i < TdataCount; i++) {   // For loop for goin through each test data
            ArrayList<Double> output;    // Creating output arraylist for storing output

            double total = Tdata[i][0] + Tdata[i][1];  // Calculating total sum of x1 and x2

            int target;   // Creating target variable
            if (total < 0)
                target = -1;
            else
                target = 1;

            output = Calculate(Tdata[i], neuron.weights);  // Calling calculation method
            if(output.get(0) == target)  // If output matches with target we get correct answer
                totalMatch++;

            System.out.println();
            System.out.println("Result of output: "+output.get(0) + " Target: "+target);  // Printing output and target
        }
        System.out.println();
        System.out.println();
        System.out.println("Accuarcy: "+"%"+(totalMatch*100)/TdataCount); // Calculating accuracy
    }

    public static void Educate(double[][] data,Neuron neuron){   // Education method
        int x = 0;
        double lambda = 0.05;  // Creating lambda variable

        while (x < 100) {  // Creating while loop for 100 epoch

            int correct = 0;  // Creating variable for correct answer

            for (int i = 0; i < 8; i++) {  // Creating for loop for going through each data set
                int target;  // Creating variable for expected output

                ArrayList<Double> output;  // Creating arrayllist for storing output

                double total = data[i][0] + data[i][1];  // Sum of x1 and x2

                if (total < 0)   // Creating expected output
                    target = -1;
                else
                    target = 1;

                output = Calculate(data[i], neuron.weights); // Getting output

                if (output.get(0) != target) {  // If output isn't equal to expected output i change weights here

                    neuron.weights.set(0,neuron.weights.get(0)+ (lambda * (total - output.get(1)) * data[i][0]));
                    neuron.weights.set(1,neuron.weights.get(1)+ (lambda * (total - output.get(1)) * data[i][1]));

                } else {
                    correct++;
                }
            }
            double accuracy = (correct * 100) / 8;  // Calculating accuracy
            if(x==9 || x==99)  // Writing the accuracy at epok 10 and 100
                System.out.println("Epok"+(x+1)+":"+" "+"%"+accuracy);
            x++;
        }
    }

    public static ArrayList<Double> Calculate(double[] array, ArrayList<Double> array2){
        // A method that calculates sum of x1 and x2 then returns 1 or -1 based on result of sum.

        double total1;
        double total2;

        ArrayList<Double> liste = new ArrayList<>();

            total1 = array[0]*array2.get(0);
            total2 = array[1]*array2.get(1);

            double total = total1+total2;

        if (total < 0)
            liste.add((double) -1);
        else
            liste.add((double) 1);

        liste.add(total);
        return liste;
        }
    }

class Neuron {  // Creating neuron class
    ArrayList<Double> weights = new ArrayList<>(); // Creating arraylist for weights
    Random random = new Random();
    Neuron(){
        for(int i = 0;i<2; i++){  // Creating random weights
            weights.add(random.nextDouble()*2-1);
        }
    }
}
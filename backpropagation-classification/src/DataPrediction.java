import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Amadeus on 24/07/14.
 */
public class DataPrediction {

    public double[][] inputTraining;
    public double[][] inputTest;
    public double[][] expectedOutputs;
    private double[]  predictions;
    private String[] file;
    private int train;
    private int test;

    public DataPrediction(double[] parameters, String[] name)
    {
        inputTraining = new double[(int)parameters[3]][3];
        inputTest = new double[(int)parameters[4]][3];
        file = name;
        train = (int)parameters[3];
        test = (int)parameters[4];
    }

    public void readDates()
    {
        String line = "";
        double[] numColumns;
        try{
            for (int indexFile=0; indexFile < file.length; indexFile++)
            {
                File fileText = new File(file[indexFile]);
                FileReader fileReader = new FileReader(fileText);
                BufferedReader bufferReader = new BufferedReader(fileReader);
                int numTrain = 0;
                int numTest = 0;

                while ((line = bufferReader.readLine()) != null) {
                    numColumns = tokenData(line);
                    if (indexFile == 0) {
                        for (int pData = 0; pData < numColumns.length; pData++) {
                            inputTraining[numTrain][pData] = numColumns[pData];
                        }
                    }
                    else {
                        if (indexFile == 1) {
                            for (int pData = 0; pData < numColumns.length; pData++) {
                                inputTest[numTest][pData] = numColumns[pData];
                            }
                            numTest += 1;
                        }
                    }
                }
            }

        }
        catch (Exception e)
        {
            System.out.println("Read File: " + e.getMessage());
        }
    }

    private double[] tokenData(String line) {

        //Separate each columns of the line
        StringTokenizer token = new StringTokenizer(line, "\t");
        int numTokens = token.countTokens();
        double[] arrayToken = new double[numTokens];
        for (int pToken = 0; pToken < numTokens; pToken++)
        {
            arrayToken[pToken] = Double.parseDouble(token.nextToken());
        }

        return(arrayToken);
    }

    public void writeDataPrediction(double[] numPredictions)
    {
        //write in file text, the prediction of set test
        predictions = numPredictions;
        File OutputFile = new File("output/Result.txt");
        try
        {
            FileWriter fileWrite = new FileWriter(OutputFile);
            BufferedWriter bufferWrite = new BufferedWriter(fileWrite);
            PrintWriter printWrite = new PrintWriter(bufferWrite);
            for(int pData = 0; pData < predictions.length; pData++)
            {
                printWrite.write(String.valueOf(predictions[pData]) + '\n');
            }
            printWrite.close();
            bufferWrite.close();

        }
        catch (Exception e)
        {
            System.out.println("Write File: " + e.getMessage());
        }
    }

    public void loadDataOutputsTrain()
    {
        //Contiene the data real each pattern
        expectedOutputs = new double[inputTraining.length][1];

        //Se asigna los patrones de entrada  y las salidas esperadas  a la red
        for(int pPattern = 0; pPattern < inputTraining.length; pPattern++)
        {
            expectedOutputs[pPattern][0]=inputTraining[pPattern][2];
        }

    }

    public void loadDataOutputsTest()
    {
        //Contains the data real each pattern
        expectedOutputs = new double[inputTest.length][1];
        for(int pPattern = 0; pPattern < inputTest.length; pPattern++)
        {
            expectedOutputs[pPattern][0]=inputTest[pPattern][2];
        }

    }

    public void calculateError()
    {
        int n11 = 0, n00 = 0, n01 = 0, n10 = 0;
        double error;
        for(int i = 0; i < expectedOutputs.length; i++)
        {
            if(expectedOutputs[i][0] == predictions[i] && predictions[i] ==1)
            {
                //Calculate class one
                n11 += 1;
            }
            else
            {
                if(expectedOutputs[i][0] == predictions[i] && predictions[i]==0)
               {
                    //calculate total of class 0
                    n00 += 1;
                }
                else
                {
                    if (expectedOutputs[i][0] == 0 && predictions[i] == 1)
                    {
                        //calculate the class of 0 that this in 1
                        n01 += 1;
                    }
                    else
                    {
                        //Calculate the class of 1 that this in 0
                        n10 +=1;
                    }
                }
            }
        }

        //Calculate the error Bayesiano
        error = ((n01 + n10) / (n00 + n01 + n10 + n11));
        System.out.println("The Error total of classification the patterns is: " + error+ "%");
    }

}

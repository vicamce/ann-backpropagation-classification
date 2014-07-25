public class Main {

    public static void main(String[] args) {
	// write your code here
        String fileMerged = "input/ring-merged.txt";
        String fileSeparable = "input/ring-separable.txt";
        String fileTest = "input/ring-test.txt";

        String[] file = new String[2];
        file[1] =fileTest;
        file[0] = fileSeparable;
        //file[0] = fileMerged;

        int[] nodes = {2,10,7,1};
        double rateLearning = 0.2;
        double momentum = 0.09;
        int epochs = 1000;
        int dataTrain = 10000;
        int dataTest =10000;

        double[] parameters = new double[5];
        parameters[0] = rateLearning;
        parameters[1] = momentum;
        parameters[2] = epochs;
        parameters[3] = dataTrain;
        parameters[4] = dataTest;

        //Parameters file merged
        NeuronalNetwork net = new NeuronalNetwork(nodes,parameters, file);
        net.neuronalNetwork();

    }
}

package neuralnet;

public class NeuralNetArchitectureDriver {

    public static void main(String[] args) {
    /*
        if (!args[0].contains(".txt")) throw new Error("filename not valid");
        util.ReadTextFile rf = new util.ReadTextFile(args[0]);

        //::WARNING:: lazy approach: no error checking involved with these input files; assumes correct format
        String data_file = rf.readLine();
        int k_bins = Integer.valueOf(rf.readLine());
        int max_hidden_layers = Integer.valueOf(rf.readLine());
        int max_nodes = Integer.valueOf(rf.readLine());
        Double learning_rate = Double.valueOf(rf.readLine());
        Double error_tol = Double.valueOf(rf.readLine());

        BackPropLearning learner = new BackPropLearning(data_file,hidden_layers,hl_nodes,learning_rate,error_tol);
        NeuralNet nerval_netty = learner.doTheWork();

        Double lar = learner.getLargestVal();
        java.util.ArrayList<DataPoint> examples = learner.getExamples();
        for (int i=0; i<examples.size(); i++)
        examples.get(i).displayLearnedOutput(lar);
        System.out.println("Error summed:\n"+String.format("%.5g%n", SumOutputs.sum(lar, examples)));
        */
    }
}
package neuralnet;

public class NeuralNetDriver {

public static void main(String[] args) {

    if (!args[0].contains(".txt")) throw new Error("filename not valid");
    util.ReadTextFile rf = new util.ReadTextFile(args[0]);

    //::WARNING:: lazy approach: no error checking involved with these input files; assumes correct format
    String data_file = rf.readLine();
    int hidden_layers = Integer.valueOf(rf.readLine());
    int[] hl_nodes = new int[hidden_layers+2];
    for(int i=1;i<=hidden_layers;i++)
      hl_nodes[i] = Integer.valueOf(rf.readLine());
    Double learning_rate = Double.valueOf(rf.readLine());
    Double error_tol = Double.valueOf(rf.readLine());

    BackPropLearning learner = new BackPropLearning(data_file,hidden_layers,hl_nodes,learning_rate,error_tol);
    NeuralNet nerval_netty = learner.doTheWork();

    Double lar = learner.getLargestVal();
    java.util.ArrayList<DataPoint> examples = learner.getExamples();
    for (int i=0; i<examples.size(); i++)
      examples.get(i).displayLearnedOutput(lar);
    System.out.println("Error summed:\n"+String.format("%.5g%n", SumOutputs.sum(lar, examples)));
  }
}
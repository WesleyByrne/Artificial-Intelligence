package neuralnet;

import java.util.ArrayList;

public class NeuralNetArchitectureDriver {

    public static void main(String[] args) {
        if (!args[0].contains(".txt")) throw new Error("filename not valid");
        util.ReadTextFile rf = new util.ReadTextFile(args[0]);

        //::WARNING:: lazy approach: no error checking involved with these input files; assumes correct format
        String data_file = rf.readLine();
        int num_bins = Integer.valueOf(rf.readLine());
        int max_hidden_layers = Integer.valueOf(rf.readLine());
        int max_nodes = Integer.valueOf(rf.readLine());
        Double learning_rate = Double.valueOf(rf.readLine());
        Double error_tol = Double.valueOf(rf.readLine());


        BackPropLearning learner = new BackPropLearning(data_file,learning_rate,error_tol);
        ArrayList<DataPoint> default_examples = learner.getDefaultExamples();
        Double largest_val = learner.getLargestVal();
        //fill bins
        java.util.Collections.shuffle(default_examples);
        ArrayList<ArrayList<DataPoint>> k_bins = new ArrayList<ArrayList<DataPoint>>();
        for (int i=0;i<num_bins;i++)
            k_bins.add(new ArrayList<DataPoint>());

        int shuffle = 0;
        while (!default_examples.isEmpty())
        {
            k_bins.get(shuffle).add(default_examples.remove(0));
            shuffle++;
            shuffle=shuffle%num_bins;
        }
        

        
        ArrayList<NeuralNet> nets = new ArrayList<NeuralNet>();
        NeuralNet new_net;
        int[] nodes;
        
        int node_size=1;

        for (int i=1;i<=max_nodes;i++)
        {
            for(int j=1;j<=max_hidden_layers;j++)
            {
                nodes=new int[j+2];
                nodes[0] = learner.getInputSize();
                nodes[(nodes.length)-1] = learner.getOutputSize();
                for (int k=1;k<nodes.length-1;k++)
                {
                    nodes[k] = i;
                }
                new_net = new NeuralNet(nodes, learning_rate, error_tol, learner.getLargestVal());
                nets.add(new_net);
            }
        }
        
        //10 fold cross validation
        ArrayList<Double> errors;
        ArrayList<DataPoint> examples;
        for (NeuralNet net: nets)
        {
            System.out.println("processing...");
            net.reportArchitecture();
            errors = new ArrayList<Double>();
            examples = new ArrayList<DataPoint>();
            for (int i=0;i<num_bins;i++){
                examples=new ArrayList<DataPoint>();
                for(int j=0;j<num_bins;j++)
                    if (j!=i) examples.addAll(k_bins.get(j));
                net = learner.BackPropAlg(net, examples);
                examples = net.returnOutput(k_bins.get(i)); 
                errors.add(SumOutputs.sum(learner.getLargestVal(), examples));
                net.avg_err = SumOutputs.avg_err(errors);
                
            }
        }

        NeuralNet best_net = nets.get(0);
        for (NeuralNet net:nets)
            if (net.avg_err < best_net.avg_err )
                best_net = net;
        
        System.out.println("\nBest Architecture:");
        best_net.reportArchitecture();
        System.out.println("err: " +best_net.avg_err);

        util.WriteTextFile wf = new util.WriteTextFile("resources/optimal.txt");
        wf.writeLine(data_file);
        int hidden_layers = best_net.getNumLayers()-2;
        wf.writeLine(Integer.toString(hidden_layers));
        for (int i=1; i<=hidden_layers;i++)
            wf.writeLine(Integer.toString(best_net.getLayer(i).length));
        wf.writeLine(String.valueOf(learning_rate));
        wf.writeLine(String.valueOf(error_tol));
        wf.close();

        

    }

}
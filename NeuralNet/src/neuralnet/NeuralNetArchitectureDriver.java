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
        

        //create neural nets to test
        ArrayList<NeuralNet> nets = new ArrayList<NeuralNet>();
		
        ArrayList<int[]> combos=new ArrayList<int[]>();
        int[] combo;
		for (int l=1; l<=max_hidden_layers;l++)
		{
			combo = new int[l+2];
			combo[0] = learner.getInputSize();
			combo[(combo.length)-1] = learner.getOutputSize();
			recursiveFill(combos, combo, 1, l, max_nodes);
		}				
        
        for (int[] ints:combos)
            nets.add(new NeuralNet(ints,learning_rate,error_tol,learner.getLargestVal()));
		
		System.out.println("Combos to process:"+combos.size());
		
        //10 fold cross validation
        ArrayList<Double> errors;
        ArrayList<DataPoint> examples;
		NeuralNet best_net = nets.get(0);
		java.util.Iterator<NeuralNet> it = nets.iterator();
        while (it.hasNext())
        {
            NeuralNet net = it.next();
            net.reportArchitecture();
            errors = new ArrayList<Double>();
            examples = new ArrayList<DataPoint>();
            for (int i=0;i<num_bins;i++){
                examples=new ArrayList<DataPoint>();
                for(int j=0;j<num_bins;j++)
                    if (j!=i) examples.addAll(k_bins.get(j));
				System.out.println("processing...");
                net = learner.BackPropAlg(net, examples);
				examples = net.returnOutput(k_bins.get(i)); 
				errors.add(SumOutputs.sum(learner.getLargestVal(), examples));
            }
			net.avg_err = SumOutputs.avg_err(errors);
			if (net.avg_err < best_net.avg_err ){
				System.out.println(net.avg_err+"<"+best_net.avg_err);	
				best_net = net;
			}
			it.remove();
        }

        
        System.out.println("\nBest Found Architecture:");
        best_net.reportArchitecture();
        System.out.println("err: " +best_net.avg_err);

		
		//writes the decided optimal architecture into a runnable file
        util.WriteTextFile wf = new util.WriteTextFile("resources/optimal.txt");
        wf.writeLine(data_file);
        int hidden_layers = best_net.getNumLayers()-2;
        wf.writeLine(Integer.toString(hidden_layers));
        for (int i=1; i<=hidden_layers;i++)
            wf.writeLine(Integer.toString(best_net.getLayer(i+1).length));
        wf.writeLine(String.valueOf(learning_rate));
        wf.writeLine(String.valueOf(error_tol));
        wf.close();

        

    }
	
	private static void recursiveFill(ArrayList combos, int[] combo, int i, int num_layers, int max)
	{
        if(i==num_layers+1)
        {
            combos.add(combo.clone());
            return;
        }
		for(int j=1;j<=max;j++)
        {
            combo[i] = j;
            recursiveFill(combos, combo, i+1, num_layers, max);
        }
    }
}
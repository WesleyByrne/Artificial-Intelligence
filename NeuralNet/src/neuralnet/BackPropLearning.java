package neuralnet;

public class BackPropLearning {

    private int num_inputs;
    private int num_outputs;
    private int hidden_layers;
    private int[] nodes;

    private java.util.ArrayList<DataPoint> examples;
    private Double learning_rate; 
    private Double error_tol;

    private Double largest_val;

    public NeuralNet doTheWork()
    {
        NeuralNet new_net = new NeuralNet(hidden_layers+2, nodes);
        return BackPropAlg(new_net);
    }

    public NeuralNet BackPropAlg(NeuralNet net)
    {
        Double in=0.0,delt;
        NeuralNode[] layer;
        long tStart = System.currentTimeMillis();
        while((System.currentTimeMillis() - tStart) <= (error_tol*60*1000))
        {
            for(DataPoint example:examples) //example
            {   
                layer = net.getInputs();
                for(int i=0;i<layer.length;i++)
                {
                    layer[i].setActivation(example.getX(i));
                }
                for (int l=2;l<=net.getNumLayers();l++) //forward
                {
                    for(int j=0;j<net.getLayer(l).length;j++)
                    {
                        in = net.weightedSum(l,j);
                        net.setNodeIn(l,j,in);
                        delt = g_x(in);
                        net.setNodeActivation(l,j,delt);
                        //aj = output in last layer
                        if (l==net.getNumLayers()) 
                            example.setLearnedValue(j,delt);
                    }
                }
                
                layer = net.getOutputs();
                for (int i=0;i<layer.length;i++) //backward
                {
                    delt = (   g_dev(layer[i].getIn())*(example.getY(i)/largest_val-layer[i].getActivation())  );
                    layer[i].setDelta(delt);
                }
                for (int l=net.getNumLayers()-1;l>=1;l--)
                {
                    layer = net.getLayer(l);
                    for(int i=0;i<layer.length;i++)
                    {
                        delt = (   g_dev(layer[i].getIn())*net.weightedDeltaSum(l,i)   );
                        layer[i].setDelta(delt);
                        //System.out.println(node.weightedDeltaSum());
                    }
                }
                net.updateWeights(learning_rate);
            }
        }
        return net;
    }

    public Double getLargestVal()
    {
        return largest_val;
    }

    public Double g_x(Double x)
    {
        return (1/(1+java.lang.Math.exp(-x)));
    }

    public Double g_dev(Double x)
    {
        return (g_x(x)*(1-g_x(x)));
    }

    public java.util.ArrayList<DataPoint> getExamples()
    {
        return examples;
    }

    public BackPropLearning(String data_file, int hidden_layers, int[] hl_nodes, Double learning_rate, Double error_tol)
    {
        util.ReadTextFile rf = new util.ReadTextFile(data_file);
        this.largest_val = Double.valueOf(rf.readLine());
        String txt = rf.readLine();
        String[] txt_split = txt.split(",");
        txt = rf.readLine();

        this.num_inputs = Integer.valueOf(txt_split[0]);
        hl_nodes[0] = num_inputs;
        this.num_outputs = Integer.valueOf(txt_split[1]);
        hl_nodes[hl_nodes.length-1] = num_outputs;
        int num_layers = 2;
        
        this.examples = new java.util.ArrayList();

        Double[] inp_arr,out_arr;

        while (!rf.EOF())
        {
            inp_arr = new Double[num_inputs];
            out_arr = new Double[num_outputs];
            txt_split = txt.split(",");
            //adds new DataPoint with x, and y values
            for (int i=0; i<num_inputs; i++)
                inp_arr[i] = Double.valueOf(txt_split[i]);
            for (int i=num_inputs;i<txt_split.length;i++)
                out_arr[i-num_inputs] = Double.valueOf(txt_split[i]);

            this.examples.add(new DataPoint(inp_arr, out_arr));
            txt = rf.readLine();
        }
        this.hidden_layers=hidden_layers;
        this.nodes=hl_nodes;
        this.learning_rate=learning_rate;
        this.error_tol=error_tol;
    }
}
package neuralnet;

public class BackPropLearning {

    private java.util.ArrayList<DataPoint> default_examples;
    private int inputSize, outputSize;
    private int[] nodes;
    private Double learning_rate, error_tol, largest_val;

    public NeuralNet doTheWork()
    {
        NeuralNet net = new NeuralNet(nodes,learning_rate,error_tol,largest_val);
        return BackPropAlg(net, default_examples);
    }

    public NeuralNet BackPropAlg(NeuralNet net, java.util.ArrayList<DataPoint> examples)
    {
        Double in=0.0,delt;
        NeuralNode[] layer;
        long tStart = System.currentTimeMillis();
        while((System.currentTimeMillis() - tStart) <= (net.getErrorTol()*60*1000))
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
                    delt = (   g_dev(layer[i].getIn())*(example.getY(i)/net.getLargestVal()-layer[i].getActivation())  );
                    layer[i].setDelta(delt);
                }
                for (int l=net.getNumLayers()-1;l>=1;l--)
                {
                    layer = net.getLayer(l);
                    for(int i=0;i<layer.length;i++)
                    {
                        delt = (   g_dev(layer[i].getIn())*net.weightedDeltaSum(l,i)   );
                        layer[i].setDelta(delt);
                    }
                }
                net.updateWeights();
            }
        }
        return net;
    }

    public Double g_x(Double x)
    {
        return (1/(1+java.lang.Math.exp(-x)));
    }

    public Double g_dev(Double x)
    {
        return (g_x(x)*(1-g_x(x)));
    }

    public java.util.ArrayList<DataPoint> getDefaultExamples(){return default_examples;}

    public BackPropLearning(String data_file, int[] nodes, Double learning_rate, Double error_tol)
    {
        util.ReadTextFile rf = new util.ReadTextFile(data_file);

        this.largest_val = Double.valueOf(rf.readLine());
        this.learning_rate = learning_rate;
        this.error_tol = error_tol;

        String txt = rf.readLine();
        String[] txt_split = txt.split(",");
        txt = rf.readLine();

        nodes[0] = Integer.valueOf(txt_split[0]);
        nodes[nodes.length-1] = Integer.valueOf(txt_split[1]);

        inputSize = nodes[0];
        outputSize = nodes[nodes.length-1];
        //Set examples array
        Double[] inp_arr,out_arr;
        this.default_examples = new java.util.ArrayList<DataPoint>();
        while (!rf.EOF())
        {
            inp_arr = new Double[nodes[0]];
            out_arr = new Double[nodes[nodes.length-1]];
            txt_split = txt.split(",");
            //adds new DataPoint with x, and y values
            for (int i=0; i<nodes[0]; i++)
                inp_arr[i] = Double.valueOf(txt_split[i]);
            for (int i=nodes[0];i<txt_split.length;i++)
                out_arr[i-nodes[0]] = Double.valueOf(txt_split[i]);

            this.default_examples.add(new DataPoint(inp_arr, out_arr));
            txt = rf.readLine();
        }
        this.nodes = nodes;
    }
}
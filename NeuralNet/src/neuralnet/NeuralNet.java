package neuralnet;

public class NeuralNet{

    private java.util.ArrayList<NeuralNode[]> layers;
    private int num_layers;
    private Double learning_rate, error_tol, largest_val;
    public Double avg_err;

    public void updateWeights()
    {
        Double value;

        //update all weights but output
        for (int i=1;i<num_layers;i++)
        {
            for (NeuralNode node:getLayer(i))
            {
                for (int j=0;j<node.getNumConnections();j++)
                {
                    value = node.getWeight(j) + learning_rate * node.getActivation() * getLayer(i+1)[j].getDelta();
                    node.setWeight(j, value);
                }
            }
        }
    }

    public void reportArchitecture()
    {
        System.out.println("Hidden Layers: " +(num_layers-2)+"");
        //for (int i=1;i<num_layers-1;i++)
            System.out.println("Layers have "+layers.get(1).length+" nodes.");
            //System.out.println("layer "+i+" has "+layers.get(i).length+" nodes.");
    }

    public java.util.ArrayList<DataPoint> returnOutput(java.util.ArrayList<DataPoint> examples)
    {
        Double in=0.0,delt;
        NeuralNode[] layer;
        for(DataPoint example:examples) //example
        {
            layer = getInputs();
            for(int i=0;i<layer.length;i++)
            {
                layer[i].setActivation(example.getX(i));
            }
            for (int l=2;l<=getNumLayers();l++) //forward
            {
                for(int j=0;j<getLayer(l).length;j++)
                {
                    in = weightedSum(l,j);
                    setNodeIn(l,j,in);
                    delt = g_x(in);
                    setNodeActivation(l,j,delt);
                    //aj = output in last layer
                    if (l==getNumLayers()) 
                        example.setLearnedValue(j,delt);
                }
            }
        }
        return examples;
    }  

    public Double g_x(Double x)
    {
        return (1/(1+java.lang.Math.exp(-x)));
    }

    public Double g_dev(Double x)
    {
        return (g_x(x)*(1-g_x(x)));
    }

    public Double weightedDeltaSum(int index, int node_index)
    {
        Double value=0.0;
        NeuralNode node = getLayer(index)[node_index];
        Double j_delta;
        for (int i=0;i<node.getNumConnections();i++)
        { 
            j_delta = getLayer(index+1)[i].getDelta();
            value += node.getWeight(i)*j_delta;
        }
        return value;
    }

    //to calculate inj: sum of wi,j * ai
    public Double weightedSum(int layer_index, int node_index)
    {
        //get the layer in front of the node
        NeuralNode[] tmp = getLayer(layer_index-1);
        Double in = 0.0;
        for (NeuralNode node:tmp)
        {
            in+=node.getWeight(node_index)*node.getActivation();
        }
        return in;
    }

    public void setNodeIn(int layer_index, int node_index, Double act)
    {
        NeuralNode[] tmp = getLayer(layer_index);
        tmp[node_index].setIn(act);
    }

    public void setNodeActivation(int layer_index, int node_index, Double act)
    {
        NeuralNode[] tmp = getLayer(layer_index);
        tmp[node_index].setActivation(act);
    }

    public int getNumLayers(){return num_layers;}
    public void setLearningRate(Double val){learning_rate=val;}
    public Double getLearningRate(){return learning_rate;}
    public void setErrorTol(Double val){error_tol=val;}
    public Double getErrorTol(){return error_tol;}
    public void setLargestVal(Double val){largest_val=val;}
    public Double getLargestVal(){return largest_val;}
    
    public NeuralNode[] getInputs(){return layers.get(0);}
    public NeuralNode[] getOutputs(){return layers.get(num_layers-1);}
    public NeuralNode[] getLayer(int index){return layers.get(index-1);} //IMPORTANT: using 1-based index

    public NeuralNet(int[] nodes, Double learning_rate, Double error_tol, Double largest_val)
    {
        this.learning_rate = learning_rate;
        this.error_tol = error_tol;
        this.largest_val = largest_val;
        this.num_layers = nodes.length;
        this.layers = new java.util.ArrayList<NeuralNode[]>();
        java.util.Random rnd = new java.util.Random(); // for setting weights within nodes

        NeuralNode[] layer;
        for(int i=0; i<num_layers-1; i++)
        {
            layer = new NeuralNode[nodes[i]];
            for (int j=0; j<nodes[i]; j++)
            {
                layer[j] = new NeuralNode(nodes[i+1], rnd);
            }
            layers.add(layer);
        }
        layer = new NeuralNode[nodes[num_layers-1]];
        for (int i=0; i<nodes[num_layers-1]; i++)
            layer[i] = new NeuralNode(0,rnd);
        layers.add(layer);
    }
}
package neuralnet;

public class NeuralNet{

    private java.util.ArrayList<NeuralNode[]> layers;
    private int num_layers;

    public void updateWeights(Double learning_rate)
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

    public int getNumLayers()
    {
        return num_layers;
    }

    //IMPORTANT
    //using 1-based index
    public NeuralNode[] getLayer(int index)
    {
        return layers.get(index-1);
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

    public NeuralNode[] getInputs()
    {
        return layers.get(0);
    }

    public NeuralNode[] getOutputs()
    {
        return layers.get(num_layers-1);
    }

    public NeuralNet(int num_layers, int[] nodes)
    {
        this.num_layers = num_layers;
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
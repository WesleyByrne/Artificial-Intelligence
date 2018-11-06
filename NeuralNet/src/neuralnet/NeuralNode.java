package neuralnet;

public class NeuralNode {
    private int num_connections;
    //calculation values
    private Double[] weights;
    private Double activation;
    private Double delta;
    private Double in;

    public int getNumConnections()
    {
        return num_connections;
    }

    public Double getWeight(int conn_num)
    {
        return weights[conn_num];
    }
    public void setWeight(int conn_num, Double wgt)
    {
        weights[conn_num] = wgt;
    }

    public Double getActivation()
    {
        return activation;
    }
    public void setActivation(Double act)
    {
        this.activation = act;
    }

    public Double getDelta()
    {
        return delta;
    }
    public void setDelta(Double delta)
    {
        this.delta = delta;
    }

    public Double getIn()
    {
        return in;
    }
    public void setIn(Double in)
    {
        this.in = in;
    }

    private Double getRandomWeight(java.util.Random rnd)
    {
        //java.util.Random rnd = new java.util.Random();
        Double weight = rnd.nextDouble();                   //get random double
        weight /= 10;                                //place random variable in range
        if (rnd.nextInt(2)==0) weight *= -1; //set sign
        return weight;
    }

    public NeuralNode(int connections_needed, java.util.Random rnd)
    {
        num_connections = connections_needed;
        //output connection weight is 1
        if (connections_needed==0) 
        {
            num_connections = 1;
            weights = new Double[1];
            weights[0] = 1.0;
        }
        else
        {
            weights = new Double[connections_needed];
            for (int i=0;i<weights.length;i++)
            {
                weights[i] = getRandomWeight(rnd);
            }
        }
        activation=0.0;
        delta=0.0;
        in=0.0;
    }

}
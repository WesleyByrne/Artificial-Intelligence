package neuralnet;
 
public class SumOutputs
{
    static Double sum(Double lar, java.util.ArrayList<DataPoint> examples)
    {
        Double values = 0.0;
        for (DataPoint example:examples)
            values += java.lang.Math.abs(example.errorDiff(lar));
        return values;
    }
}
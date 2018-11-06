package neuralnet;

import java.util.ArrayList;

public class SumOutputs
{
    static Double sum(Double lar, ArrayList<DataPoint> examples)
    {
        Double values = 0.0;
        for (DataPoint example:examples)
            values += java.lang.Math.abs(example.errorDiff(lar));
        return values;
    }
}
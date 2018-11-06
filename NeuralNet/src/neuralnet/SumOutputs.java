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

    static Double avg_err(java.util.ArrayList<Double> errors)
    {
        Integer hdf = errors.size();
        Double avg=0.0,num_vals=hdf.doubleValue();
        for(Double err:errors)
        {
            avg+=err;
        }
        return avg/num_vals;
    }
}
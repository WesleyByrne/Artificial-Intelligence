package neuralnet;

public class DataPoint {

    private Double[] x_values;
    private Double[] y_values;
    private Double[] learned_values;
    
    public DataPoint(Double[] x, Double[] y)
    {
        //x input vector
        x_values=x;
        //y output vector
        y_values=y;
        learned_values= new Double[y_values.length];
    }

    public Double getX(int index)
    {
        return x_values[index];
    }

    public Double getY(int index)
    {
        return y_values[index];
    }

    public Double getLearnedValue(int index)
    {
        return learned_values[index];
    }

    public void setLearnedValue(int index, Double lv)
    {
        learned_values[index]=lv;
    }

    public Double errorDiff(Double lar)
    {
        Double total = 0.0;
        for (int i=0;i<y_values.length;i++)
            total += (y_values[i]-(learned_values[i]*lar));
        return total;
    }

    public void displayLearnedOutput(Double lar)
    {
        System.out.println("Input:");
        for (int i=0;i<x_values.length;i++)
            System.out.print(x_values[i]+" ");
        System.out.println("\nExpected Output: ");
        for (int i=0;i<y_values.length;i++)
            System.out.print(y_values[i]+" ");
            System.out.println("\nLearned Output: ");
        for (int i=0;i<learned_values.length;i++)
            System.out.print(String.format("%.5g%n", learned_values[i]*lar)+" ");
        System.out.println();
    }
}
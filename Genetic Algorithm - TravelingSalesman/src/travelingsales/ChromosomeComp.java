package travelingsales; 

public class ChromosomeComp implements java.util.Comparator<Chromosome>
{
	@Override
	public int compare(Chromosome o1, Chromosome o2)
	{
		if (o1 == o2) return 0;
		else return (o1.getFit()-o2.getFit());
	}
}
package travelingsales; 

import java.util.ArrayList;

public class Chromosome
{
	private int[] path;
	private int num_genes;
	private int fitness;
	TravelingSalesGeneticAI ai;
	
	private void initializeChromosome()
	{
		java.util.Random rand = new java.util.Random();			
		
		path = new int[num_genes]; //size initialized to the number of genes present
		
		for (int i=0; i<path.length; i++) 
		{
			path[i]=i;
		} //fill chromosome array
		
		for (int i=0; i<path.length; i++) 
		{
		    int rand_int = rand.nextInt(path.length);
		    int tmp = path[i];
		    path[i] = path[rand_int];
		    path[rand_int] = tmp;
		} //and shuffle their positions
	}
	
	private void crossover(Chromosome c1, Chromosome c2, int crossoverpoint)
	{
		
		path = new int[num_genes]; //initialize path
		
		Chromosome tmp_chr = c1;
		for (int i=0; i<path.length; i++)
		{
			if (i>crossoverpoint) 
				tmp_chr = c2; //swap to c2 for crossover
			path[i]=tmp_chr.getPath()[i];
		}
		fixDuplicates();
	}
	
	private void fixDuplicates()
	{
		ArrayList<Integer> test = new ArrayList<Integer>(); //array used to prevent duplicates
		for (int i=0; i<num_genes; i++)
			test.add(Integer.valueOf(i)); //fill it with available indexes
		java.util.Collections.shuffle(test); //shuffle it's contents
		
		//java.util.Random rand = new java.util.Random();
		//path[rand.nextInt(test.size())] = rand.nextInt(test.size());
		//path[rand.nextInt(test.size())] = rand.nextInt(test.size());
		//path[rand.nextInt(test.size())] = rand.nextInt(test.size());
		
		for (int i=0; i<path.length; i++)
		{
			if (test.contains(Integer.valueOf(path[i]))) 
				{
					test.remove(Integer.valueOf(path[i]));
				}
			else 
			{
				path[i]= test.remove(0); //mutation occurs here
			}
		}
	}
	
	private int determineChromosomeFitness() //cycles through vertex values to determine the distance traveled
	{
		//always from first city provided
		String edge_call = callEdge(ai.getStartingCity()+" "+ai.getVertices().get(path[0]));
		int distance = parseIntfromEdge(edge_call);
		
		//rest of the edges added
		for (int i=0; i<path.length-1; i++) {
			edge_call = callEdge(ai.getVertices().get(path[i])+" "+ai.getVertices().get(path[i+1]));
			distance += parseIntfromEdge(edge_call);
		}
		
		//always to first city provided
		edge_call = callEdge(ai.getVertices().get(path[path.length-1])+" "+ai.getStartingCity());
		distance += parseIntfromEdge(edge_call);
		
		return(distance);
	}
	
	private int parseIntfromEdge(String edge_call)
	{
		String[] split = edge_call.split(" ");
		return Integer.parseInt(split[2]);
	}
	
	private String callEdge(String call)
	{
		for (String edge : ai.getEdges())
			if (edge.contains(call))
				return edge;
		return "ERR";
	}
	
	public void setFitness(int fitness)
	{
		this.fitness = fitness;
	}
	
	public void setPath(int[] path)
	{
		this.path = path;
	}
	
	public int[] getPath()
	{
		return path;
	}
	
	public int getFit()
	{
		return fitness;
	}
	
	Chromosome(TravelingSalesGeneticAI ai) //constructor for initial set
	{
		this.ai = ai;
		num_genes = ai.getVertices().size();
		initializeChromosome();
		setFitness(determineChromosomeFitness());
	}
	
	Chromosome(TravelingSalesGeneticAI ai, Chromosome c1, Chromosome c2, int crossoverpoint) //crossover constructor
	{
		this.ai = ai;
		num_genes = ai.getVertices().size();
		crossover(c1, c2, crossoverpoint);
		setFitness(determineChromosomeFitness());
	}
	
	public String displayPath()
	{
		String pstr = "PATH: ";
		for (int edge : path)
		{
			pstr = pstr.concat(String.valueOf(edge)+", ");
		}
		return pstr;
	}
	
	public String toString()
	{
		return (fitness+" ");
	}
}
package travelingsales; 

import java.util.ArrayList;

public class TravelingSalesGeneticAI 
{
	private String starting_city;
	private ArrayList<String> vertices; //initialized variables passed by command line arguments
	private ArrayList<String> edges;
	private int num_pop;
	private int num_gen;
	
	private ArrayList<Chromosome> curr_gen;
	private ChromosomeComp cmp = new ChromosomeComp();
	
	public int[] solve()
	{
		for (int pop=curr_gen.size(); pop<num_pop; pop++) //fill first gen with random values 
		{
			curr_gen.add(new Chromosome(this));
		}
		java.util.Collections.sort(curr_gen, cmp); //sorted by min distance first
		
		for (int gen=1; gen<=num_gen; gen++) 
		{
			System.out.print("analyzing gen "+String.format("%4d",gen)+"...   ");
			newGeneration();  //creates a new generation from the old one
			System.out.println("fitness: "+curr_gen.get(0));
		}
		
		return (curr_gen.get(0).getPath());
	}
	
	
	private void newGeneration()
	{
		int rand_int;
		java.util.Random rand = new java.util.Random();
		
		ArrayList<Chromosome> new_gen = new ArrayList<Chromosome>();
		ArrayList<Chromosome> tmp_gen = new ArrayList<Chromosome>();
		//ArrayList other_gen = new ArrayList<Chromosome>();
		
		
		for(int i=0; i<10; i++)
		{
			new_gen.add(curr_gen.get(i)); //fill first 10 pop slots in new generation
		}
		
		while(new_gen.size()<num_pop) 
		{
			tmp_gen.clear();
			while (tmp_gen.size()<10) //10 randomly selected from the remaining list...
			{
				rand_int = rand.nextInt(num_pop);
				if (!tmp_gen.contains(curr_gen.get(rand_int)))
					tmp_gen.add(curr_gen.get(rand_int));
			}
			
			java.util.Collections.sort(tmp_gen, cmp); // sorted...
			
			Chromosome c1 = tmp_gen.remove(rand.nextInt(4)); //2 randomly picked from the best 4...
			Chromosome c2 = tmp_gen.remove(rand.nextInt(3));
			
			rand_int = rand.nextInt(vertices.size());
			new_gen.add(new Chromosome(this,c1,c2, rand_int));  //chromosome crossover
			new_gen.add(new Chromosome(this,c2,c1, rand_int));  //chromosome crossover
		}
			
		curr_gen = new_gen; //set the current to the new one;
		java.util.Collections.sort(curr_gen, cmp); //sort at the end
	}
	
	public ArrayList<String> getVertices()
	{
		return vertices;
	}
	
	public ArrayList<String> getEdges()
	{
		return edges;
	}
	
	public String getStartingCity()
	{
		return starting_city;
	}
	
    TravelingSalesGeneticAI(String vert_file, String edge_file, int num_pop, int num_gen)
	{
		vertices = new ArrayList<String>();
		edges = new ArrayList<String>();
		curr_gen = new ArrayList<Chromosome>();
		util.ReadTextFile rdr = new util.ReadTextFile(vert_file);
		
		starting_city = rdr.readLine(); //starting city is the vertex added
		String tmp = rdr.readLine();
		while (!rdr.EOF())
		{
			vertices.add(tmp);
			tmp = rdr.readLine();
		}//fill vertices
		
		rdr = new util.ReadTextFile(edge_file);
		tmp = rdr.readLine();
		while (!rdr.EOF())
		{
			edges.add(tmp);
			tmp = rdr.readLine();
		}//fill edges
		
		this.num_pop = num_pop;
		this.num_gen = num_gen;
	}
}
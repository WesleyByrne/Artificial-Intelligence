package travelingsales; 

public class TravelingSalesGeneticDriver {

    public static void main(String[] args) {
	
		String vertexfile;
		String edgefile;
		int popsize=0;
		int num_gen=0;
	
		if (args[0].contains(".txt"))
			vertexfile = args[0];
		else throw new Error("first filename not valid");
		
		if (args[1].contains(".txt"))
			edgefile = args[1];
		else throw new Error("second filename not valid");
		
		try {
				popsize = Integer.parseInt(args[2]);
				if (popsize < 10)
					throw new NumberFormatException();
			}
		    catch (NumberFormatException nfe){
				System.out.println("pop size not valid number");
				return;
		}
		
		try {
				num_gen = Integer.parseInt(args[3]);
			}
		    catch (NumberFormatException nfe){
				System.out.println("generations not valid number");
				return;
		}
		
		TravelingSalesGeneticAI ai = new TravelingSalesGeneticAI(vertexfile,edgefile,popsize,num_gen);
		int[] sol = ai.solve();
		
		for (int i=0; i<sol.length; i++) //vertices incremented for readability
			sol[i]++;
		System.out.println("Best Path found: "+java.util.Arrays.toString(sol));
		// [12, 6, 2, 1, 14, 10, 13, 11, 3, 7, 8, 9, 4, 5]
		// [5, 4, 9, 8, 7, 3, 11, 13, 10, 14, 1, 2, 6, 12]
    }

}
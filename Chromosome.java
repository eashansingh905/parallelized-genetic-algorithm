import java.io.*;
import java.util.*;
import java.text.*;

public class Chromosome {


	public String chromo;
	public double fitness; 

	public Chromosome(){

		char gene;
		double rand; 
		chromo = "";

		// Generate a binary candidate solution of length 20
		for (int i = 0; i < 20; i++)
		{
			gene = Math.random() > 0.5 ? '0':'1';
			this.chromo = chromo + gene;  
		}


		this.fitness = -1; 
	}

	// Selection strategy
	public static int selectionOfParent(){ 
		
		int tournamentType = 2;
		double rWheel = 0;
		double randnum;
		int j = 0; 
		int k = 0; 

		switch(tournamentType){

		case 1:
			randnum = GA.r.nextDouble();
			for (j=0; j<1000; j++){
				rWheel = rWheel + GA.parent[j].fitness;
				if (randnum < rWheel) return(j);
			}
			break;

		case 2:
			int tourneySize = 2;
            double minFitness = Double.MAX_VALUE;
            int bestMember = -1;

            for (int i = 0; i < tourneySize; i++) {
                randnum = GA.r.nextDouble();
                int x = (int)(randnum * 1000);
                if (GA.parent[x].fitness < minFitness) {
                    minFitness = GA.parent[x].fitness;
                    bestMember = x;
                }
            }

            return bestMember;

         default:
         		System.out.println("ERROR");
		}

		return -1; 
	}

	// Crossover strategy
	public static void crossover(Chromosome parent1, Chromosome parent2, Chromosome child1, Chromosome child2){
		int xoverPoint1;
		int xoverPoint2;

		int crossoverType = 1; 

		switch (crossoverType){

		case 1:     

			
			xoverPoint1 = 1 + (int)(GA.r.nextDouble() * (19));

			child1.chromo = parent1.chromo.substring(0,xoverPoint1) + parent2.chromo.substring(xoverPoint1);
			child2.chromo = parent2.chromo.substring(0,xoverPoint1) + parent1.chromo.substring(xoverPoint1);
			break;

		case 2:     

		case 3:     

		default:
			System.out.println("ERROR - Bad crossover method selected");
		}

		
		child1.fitness = -1; 
		child2.fitness = -1;   
	}

	// Mutation strategy
	public void mutation(){

		String mutatedChromosome = "";
		char x;
		double randnum;
		int mutationType = 1;


		switch (mutationType){

		case 1: 

			for (int j=0; j<20; j++){
				x = this.chromo.charAt(j);
				randnum = GA.r.nextDouble();
				if (randnum < 0.05){
					if (x == '1') x = '0';
					else x = '1';
				}
				mutatedChromosome = mutatedChromosome + x;
			}
			this.chromo = mutatedChromosome;
			break;

		default:
			System.out.println("ERROR");
		}

	}
	
}
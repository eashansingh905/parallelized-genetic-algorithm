/*********************************************************************************
* Simulates the GA on the given non-convex optimization problem 
*********************************************************************************/
import java.io.*;
import java.util.*;
import java.text.*;

public class GeneticAlgorithm {


	public static Problem problem;
	public static Chromosome[] member; 
	public static Chromosome[] child; 
	
	// Best overall chromosome and run/generation of occurrence
	public static Chromosome bestOverallSolution;
	public static int bestOverallRun;
	public static int bestOverallGeneration;

	public static Chromosome bestOfRunSolution;


	public static Chromosome bestOfGenerationSolution; 


	public static double intializedBest;
	public static double intializedWorst;

	public static double sumFitness; 

	public static void main(String [] args) throws java.io.IOException{

		problem = new Rastrigin();
		System.out.println("Optimization problem: " + problem.problemName);


		member = new Chromosome[100];
		child = new Chromosome[100];

		bestOverallSolution = new Chromosome();
		bestOfRunSolution = new Chromosome();
		bestOfGenerationSolution = new Chromosome();

		// Intialize best and worst
		intializedBest = Double.MAX_VALUE;
		intializedWorst = 0;

		bestOverallSolution.fitness = intializedBest; 

		// Run the GA for 20 runs
		for (int runs = 1; runs <= 20; runs++)
		{
			bestOfRunSolution.fitness = intializedBest; 
			System.out.println();

			// Intialize population of size 100
			for (int i = 0; i < 100; i++)
			{
				member[i] = new Chromosome();
				child[i] = new Chromosome(); 
			}

			// Each run has 100 generations 
			for(int generations = 1; generations <= 100; generations++)
			{
				sumFitness = 0;
				bestOfGenerationSolution.fitness = intializedBest; 

				// Evaluate each population members fitness (pop_size = 100)
				for(int i = 0; i < 100; i++)
				{
					member[i].fitness = 0; 
					problem.calculateFitness(member[i]);
					sumFitness += member[i].fitness;
				
					/* TODO

						1) Find the best chromo of the generation
						2) Find the best chromo of the run
						3) Find best overall chromo  
					*/

				}

				/* TODO

					1) Calculate average fitness of population
					2) Calculate stdev of fitness of population
					3) Print out the best chromosome of the generation
					4) Select parents for mating
					5) Perform crossover
					6) Perform mutation
					7) Perform replacement strategy for new generation
				*/

			}

			/* TODO

				1) Print out the best chromo of the run 
			*/

	    }

	    /* TODO

			1) Print out the best chromo over all runs
			2) Print statistics 
		*/
		
	}

}
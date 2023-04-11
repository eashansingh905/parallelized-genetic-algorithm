/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

import java.io.*;
import java.util.*;
import java.text.*;

public class Search {

/*******************************************************************************
*                           INSTANCE VARIABLES                                 *
*******************************************************************************/

/*******************************************************************************
*                           STATIC VARIABLES                                   *
*******************************************************************************/

	// public static FitnessFunction problem;
	public static Problem problem;

	public static Chromosome[] member;
	public static Chromosome[] child;

	public static Chromosome bestOfGenChromosome;
	public static int bestOfGenR;
	public static int bestOfGenG;
	public static Chromosome bestOfRunChromosome;
	public static int bestOfRunR;
	public static int bestOfRunG;
	public static Chromosome bestOverAllChromosome;
	public static int bestOverAllR;
	public static int bestOverAllG;

	public static double sumfitness;
	public static double sumfitness2;	// sum of squares of fitness
	// public static double sumSclFitness;
	// public static double sumProFitness;
	public static double defaultBest;
	public static double defaultWorst;

	public static double averagefitness;
	public static double stdevfitness;
	public static double stdevAverageFitness;
	public static double stdevBestFitness; 

	public static int G;
	public static int R;
	public static Random r = new Random();
	private static double randnum;

	private static int memberIndex[];
	private static double memberFitness[];
	private static int TmemberIndex;
	private static double TmemberFitness;

	private static double fitnessStats[][];  // 0=Avg, 1=Best
	private static double fitnessLog[][][];


	private static final int GENERATIONS = 100;
	private static final int RUNS = 100;
	private static final int POP_SIZE = 20;
	private static final boolean MAX_OR_MIN = false; // true = Maximization problem; false = Minimization problem
	private static final double XOVER_RATE = 0.8;

	// private static double bestFitness[][]

/*******************************************************************************
*                              CONSTRUCTORS                                    *
*******************************************************************************/


/*******************************************************************************
*                             MEMBER METHODS                                   *
*******************************************************************************/


/*******************************************************************************
*                             STATIC METHODS                                   *
*******************************************************************************/

	public static void main(String[] args) throws java.io.IOException{

		Calendar dateAndTime = Calendar.getInstance(); 
		Date startTime = dateAndTime.getTime();
		long begin = System.nanoTime();

	// //  Read Parameter File
	// 	System.out.println("\nParameter File Name is: " + args[0] + "\n");
	// 	Parameters parmValues = new Parameters(args[0]);

	//  Write Parameters To Summary Output File
		String summaryFileName = "Rastrigin" + "_summary.txt";
		FileWriter summaryOutput = new FileWriter(summaryFileName);
		// parmValues.outputParameters(summaryOutput);

	//	Set up Fitness Statistics matrix
		fitnessStats = new double[2][GENERATIONS];
		for (int i=0; i<GENERATIONS; i++){
			fitnessStats[0][i] = 0;
			fitnessStats[1][i] = 0;
		}

		fitnessLog = new double[RUNS+1][GENERATIONS][2];
        for (int i=1; i<=RUNS; i++){
            for (int j=0; j<GENERATIONS; j++){
                fitnessLog[i][j][0] = 0;
                fitnessLog[i][j][1] = 0;
            }
        }

	//	Problem Specific Setup - For new new fitness function problems, create
	//	the appropriate class file (extending FitnessFunction.java) and add
	//	an else_if block below to instantiate the problem.
 
		// if (Parameters.problemType.equals("NM")){
		// 		problem = new NumberMatch();
		// }
		// else if (Parameters.problemType.equals("PS")){
		// 		problem = new PointScattering();
		// }
		// else if (Parameters.problemType.equals("OM")){
		// 		problem = new OneMax();
		// }
		// else System.out.println("Invalid Problem Type");

        problem = new Rastrigin();
        System.out.println("Optimization problem: " + problem.problemName);
		// System.out.println(problem.name);

	//	Initialize RNG, array sizes and other objects
		// r.setSeed(Parameters.seed);
		memberIndex = new int[POP_SIZE];
		memberFitness = new double[POP_SIZE];
		member = new Chromosome[POP_SIZE];
		child = new Chromosome[POP_SIZE];
		bestOfGenChromosome = new Chromosome();
		bestOfRunChromosome = new Chromosome();
		bestOverAllChromosome = new Chromosome();

		if (MAX_OR_MIN == true){
			defaultBest = 0;
			defaultWorst = 999999999999999999999.0;
		}
		else{
			defaultBest = 999999999999999999999.0;
			defaultWorst = 0;
		}

		bestOverAllChromosome.fitness = defaultBest;

		//  Start program for multiple runs
		for (R = 1; R <= RUNS; R++){

			bestOfRunChromosome.fitness = defaultBest;
			System.out.println();

			//	Initialize First Generation
			for (int i=0; i<POP_SIZE; i++){
				member[i] = new Chromosome();
				child[i] = new Chromosome();
			}

			//	Begin Each Run
			for (G=0; G<GENERATIONS; G++){

				// sumProFitness = 0;
				// sumSclFitness = 0;
				sumfitness = 0;
				sumfitness2 = 0;
				bestOfGenChromosome.fitness = defaultBest;

				//	Test Fitness of Each Member
				for (int i=0; i<POP_SIZE; i++){

					member[i].fitness = 0;
					// member[i].sclFitness = 0;
					// member[i].proFitness = 0;

					problem.calculateFitness(member[i]);

					sumfitness = sumfitness + member[i].fitness;
					sumfitness2 = sumfitness2 +
						member[i].fitness * member[i].fitness;

					if (MAX_OR_MIN == true){
						if (member[i].fitness > bestOfGenChromosome.fitness){
							// Chromosome.copyB2A(bestOfGenChromosome, member[i]);
							bestOfGenChromosome.chromo = member[i].chromo;
							bestOfGenChromosome.fitness = member[i].fitness;
							bestOfGenR = R;
							bestOfGenG = G;
						}
						if (member[i].fitness > bestOfRunChromosome.fitness){
							// Chromosome.copyB2A(bestOfRunChromosome, member[i]);
							bestOfRunChromosome.chromo = member[i].chromo;
							bestOfRunChromosome.fitness = member[i].fitness;
							bestOfRunR = R;
							bestOfRunG = G;
						}
						if (member[i].fitness > bestOverAllChromosome.fitness){
							// Chromosome.copyB2A(bestOverAllChromosome, member[i]);
							bestOverAllChromosome.chromo = member[i].chromo;
							bestOverAllChromosome.fitness = member[i].fitness;
							bestOverAllR = R;
							bestOverAllG = G;
						}
					}
					else {
						if (member[i].fitness < bestOfGenChromosome.fitness){
							// Chromosome.copyB2A(bestOfGenChromosome, member[i]);
							bestOfGenChromosome.chromo = member[i].chromo;
							bestOfGenChromosome.fitness = member[i].fitness;
							bestOfGenR = R;
							bestOfGenG = G;
						}
						if (member[i].fitness < bestOfRunChromosome.fitness){
							// Chromosome.copyB2A(bestOfRunChromosome, member[i]);
							bestOfRunChromosome.chromo = member[i].chromo;
							bestOfRunChromosome.fitness = member[i].fitness;
							bestOfRunR = R;
							bestOfRunG = G;
						}
						if (member[i].fitness < bestOverAllChromosome.fitness){
							// Chromosome.copyB2A(bestOverAllChromosome, member[i]);
							bestOverAllChromosome.chromo = member[i].chromo;
							bestOverAllChromosome.fitness = member[i].fitness;
							bestOverAllR = R;
							bestOverAllG = G;
						}
					}
				}

				fitnessLog[R][G][0] += sumfitness / POP_SIZE;
                fitnessLog[R][G][1] += bestOfGenChromosome.fitness;

				fitnessStats[0][G] += sumfitness / POP_SIZE;
				fitnessStats[1][G] += bestOfGenChromosome.fitness;

                averagefitness = sumfitness / POP_SIZE;
				stdevfitness = Math.sqrt(
							Math.abs(sumfitness2 - 
							sumfitness*sumfitness/POP_SIZE)
							/
							(POP_SIZE-1)
							);

				// Output generation statistics To screen
				System.out.println(R + "\t" + G +  "\t" + bestOfGenChromosome.fitness + "\t" + averagefitness + "\t" + stdevfitness);

				// Output generation statistics to summary file
				summaryOutput.write(" R ");
				Hwrite.right(R, 3, summaryOutput);
				summaryOutput.write(" G ");
				Hwrite.right(G, 3, summaryOutput);
				Hwrite.right((int)bestOfGenChromosome.fitness, 7, summaryOutput);
				Hwrite.right(averagefitness, 11, 3, summaryOutput);
				Hwrite.right(stdevfitness, 11, 3, summaryOutput);
				summaryOutput.write("\n");


		// // *********************************************************************
		// // **************** SCALE FITNESS OF EACH MEMBER AND SUM ***************
		// // *********************************************************************

		// 		switch(Parameters.scaleType){

		// 		case 0:     // No change to raw fitness
		// 			for (int i=0; i<POP_SIZE; i++){
		// 				member[i].sclFitness = member[i].fitness + .000001;
		// 				sumSclFitness += member[i].sclFitness;
		// 			}
		// 			break;

		// 		case 1:     // Fitness not scaled.  Only inverted.
		// 			for (int i=0; i<POP_SIZE; i++){
		// 				member[i].sclFitness = 1/(member[i].fitness + .000001);
		// 				sumSclFitness += member[i].sclFitness;
		// 			}
		// 			break;

		// 		case 2:     // Fitness scaled by Rank (Maximizing fitness)

		// 			//  Copy genetic data to temp array
		// 			for (int i=0; i<POP_SIZE; i++){
		// 				memberIndex[i] = i;
		// 				memberFitness[i] = member[i].fitness;
		// 			}
		// 			//  Bubble Sort the array by floating point number
		// 			for (int i=POP_SIZE-1; i>0; i--){
		// 				for (int j=0; j<i; j++){
		// 					if (memberFitness[j] > memberFitness[j+1]){
		// 						TmemberIndex = memberIndex[j];
		// 						TmemberFitness = memberFitness[j];
		// 						memberIndex[j] = memberIndex[j+1];
		// 						memberFitness[j] = memberFitness[j+1];
		// 						memberIndex[j+1] = TmemberIndex;
		// 						memberFitness[j+1] = TmemberFitness;
		// 					}
		// 				}
		// 			}
		// 			//  Copy ordered array to scale fitness fields
		// 			for (int i=0; i<POP_SIZE; i++){
		// 				member[memberIndex[i]].sclFitness = i;
		// 				sumSclFitness += member[memberIndex[i]].sclFitness;
		// 			}

		// 			break;

		// 		case 3:     // Fitness scaled by Rank (minimizing fitness)

		// 			//  Copy genetic data to temp array
		// 			for (int i=0; i<POP_SIZE; i++){
		// 				memberIndex[i] = i;
		// 				memberFitness[i] = member[i].fitness;
		// 			}
		// 			//  Bubble Sort the array by floating point number
		// 			for (int i=1; i<POP_SIZE; i++){
		// 				for (int j=(POP_SIZE - 1); j>=i; j--){
		// 					if (memberFitness[j-i] < memberFitness[j]){
		// 						TmemberIndex = memberIndex[j-1];
		// 						TmemberFitness = memberFitness[j-1];
		// 						memberIndex[j-1] = memberIndex[j];
		// 						memberFitness[j-1] = memberFitness[j];
		// 						memberIndex[j] = TmemberIndex;
		// 						memberFitness[j] = TmemberFitness;
		// 					}
		// 				}
		// 			}
		// 			//  Copy array order to scale fitness fields
		// 			for (int i=0; i<POP_SIZE; i++){
		// 				member[memberIndex[i]].sclFitness = i;
		// 				sumSclFitness += member[memberIndex[i]].sclFitness;
		// 			}

		// 			break;

		// 		default:
		// 			System.out.println("ERROR - No scaling method selected");
		// 		}


		// // *********************************************************************
		// // ****** PROPORTIONALIZE SCALED FITNESS FOR EACH MEMBER AND SUM *******
		// // *********************************************************************

		// 		for (int i=0; i<POP_SIZE; i++){
		// 			member[i].proFitness = member[i].sclFitness/sumSclFitness;
		// 			sumProFitness = sumProFitness + member[i].proFitness;
		// 		}

		// *********************************************************************
		// ************ CROSSOVER AND CREATE NEXT GENERATION *******************
		// *********************************************************************

				int parent1 = -1;
				int parent2 = -1;

				//  Assumes always two offspring per mating
				for (int i=0; i<POP_SIZE; i=i+2){

					//	Select Two Parents
					parent1 = Chromosome.selectionOfParent();
					parent2 = parent1;
					while (parent2 == parent1){
						parent2 = Chromosome.selectionOfParent();
					}

					//	Crossover Two Parents to Create Two Children
					randnum = r.nextDouble();
					if (randnum < XOVER_RATE){
						Chromosome.crossover(member[parent1], member[parent2], child[i], child[i+1]);
					}
					else {
						// Chromosome.mateParents(parent1, member[parent1], child[i]);
						child[i].chromo = member[parent1].chromo; 
						child[i].fitness = -1; 
						// Chromosome.mateParents(parent2, member[parent2], child[i+1]);
						child[i+1].chromo = member[parent2].chromo; 
						child[i+1].fitness = -1; 
					}
				} // End Crossover

				//	Mutate Children
				for (int i=0; i<POP_SIZE; i++){
					child[i].mutation();
				}

				//	Swap Children with Last Generation
				for (int i=0; i<POP_SIZE; i++){
					// Chromosome.copyB2A(member[i], child[i]);
					member[i].chromo = child[i].chromo;
					member[i].fitness = child[i].fitness;
				}

			} //  Repeat the above loop for each generation

			Hwrite.left(bestOfRunR, 4, summaryOutput);
			Hwrite.right(bestOfRunG, 4, summaryOutput);

			// problem.printChromosome(bestOfRunChromosome, summaryOutput);

			System.out.println(R + "\t" + "B" + "\t"+ bestOfRunChromosome.fitness);
			System.out.println(bestOfRunChromosome.chromo);

		} //End of a Run

		// for(int i = 0; i< GENERATIONS; i++)
		// {
		// 	// stdev average fitness
  //           double sumAvgFitness = 0;
  //           for (int j=1; j<=RUNS; j++){
  //          	    sumAvgFitness += fitnessLog[j][i][0];
  //          	}
            	
  //           double avgAvgFitness = sumAvgFitness / RUNS;
  //           double stdevAvgFitness = 0;
            	
  //           for (int j=1; j<=RUNS; j++){
  //               stdevAvgFitness += Math.pow(fitnessLog[j][i][0] - avgAvgFitness, 2);
  //           }

  //           stdevAvgFitness = Math.sqrt(stdevAvgFitness / RUNS);
		// }

		Hwrite.left("B", 8, summaryOutput);

		// problem.printChromosome(bestOverAllChromosome, summaryOutput);

		//	Output Fitness Statistics matrix
		summaryOutput.write("Gen             AvgFit              StdAvg              BestFit              StdBest\n");
		
		for (int i=0; i<GENERATIONS; i++){
			
			// stdev average fitness
            double sumAvgFitness = 0;
            for (int j=1; j<=RUNS; j++) {
                sumAvgFitness += fitnessLog[j][i][0];
            }
            double avgAvgFitness = sumAvgFitness / RUNS;
            double stdevAvgFitness = 0;
            for (int j=1; j<=RUNS; j++) {
                stdevAvgFitness += Math.pow(fitnessLog[j][i][0] - avgAvgFitness, 2);
            }
            stdevAvgFitness = Math.sqrt(stdevAvgFitness / RUNS);



            // stdev average fitness
            double sumBestFitness = 0;
            for (int j=1; j<=RUNS; j++) {
                sumBestFitness += fitnessLog[j][i][1];
            }
            double avgBestFitness = sumBestFitness / RUNS;
            double stdevBestFitness = 0;
            for (int j=1; j<=RUNS; j++) {
                stdevBestFitness += Math.pow(fitnessLog[j][i][1] - avgBestFitness, 2);
            }
            stdevBestFitness = Math.sqrt(stdevBestFitness / RUNS);



			Hwrite.left(i, 15, summaryOutput);
			Hwrite.left(fitnessStats[0][i]/RUNS, 20, 2, summaryOutput);
			Hwrite.left(stdevAvgFitness, 20, 2, summaryOutput);
			Hwrite.left(fitnessStats[1][i]/RUNS, 20, 2, summaryOutput);
			Hwrite.left(stdevBestFitness, 20, 2, summaryOutput);
			summaryOutput.write("\n");
		}

		summaryOutput.write("\n");
		summaryOutput.close();

		System.out.println();
		System.out.println("Start:  " + startTime);
		dateAndTime = Calendar.getInstance(); 
		Date endTime = dateAndTime.getTime();
		System.out.println("End  :  " + endTime);
		long duration = System.nanoTime() - begin;
		System.out.println("Duration(ms)  :  " + (duration)/1e6);

	} // End of Main Class

}   // End of Search.Java ******************************************************



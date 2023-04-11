import java.io.*;
import java.util.*;
import java.text.*;

public class GA{

	public static Problem problem;

	public static Chromosome[] parent;
	public static Chromosome[] offspring;

	public static Chromosome genChromoBest;
	public static Chromosome runChromoBest;
	public static Chromosome chromoBestAll;


	private static int indexOfParent[];

	public static int bestOfGenR;
	public static int bestOfGenG;
	public static int bestOfRunR;
	public static int bestOfRunG;
	public static int bestOverAllR;
	public static int bestOverAllG;
	private static int TindexOfParent;
	public static int G;
	public static int R;


	private static double fitnessOfParent[];

	public static double fitnessSum;
	public static double fitnessSum2;
	public static double bestDefault;
	public static double worstDefault;
	public static double averagefitness;
	public static double stdevfitness;
	public static double stdevAverageFitness;
	public static double stdevBestFitness; 
	private static double randnum;
	private static double TfitnessOfParent;
	
	public static Random r = new Random();
	
	private static double fitnessStats[][];  // 0=Avg, 1=Best
	private static double fitnessLog[][][];



	//HYPER-PARAMETERS
	private static final int GENERATIONS = 100;
	private static final int RUNS = 100;
	private static final int POP_SIZE = 1000;
	private static final boolean MAX_OR_MIN = false; // false for Rastrigin(minimization)
	private static final double XOVER_RATE = 0.8;
	private static final int NUM_THREADS = 1;

	
	public static void main(String[] args) throws java.io.IOException{

		Calendar dateTime = Calendar.getInstance(); 
		Date start = dateTime.getTime();
		long begin = System.nanoTime();

		String fileName = "Rastrigin" + "_summary.txt";
		FileWriter summaryFileOutput = new FileWriter(fileName);
		

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

        // Initialize Rastrigin problem
        problem = new Rastrigin();
        System.out.println("Optimization problem: " + problem.problemName);

		indexOfParent = new int[POP_SIZE];
		fitnessOfParent = new double[POP_SIZE];
		parent = new Chromosome[POP_SIZE];
		offspring = new Chromosome[POP_SIZE];
		
		genChromoBest = new Chromosome();
		runChromoBest = new Chromosome();
		chromoBestAll = new Chromosome();

		// Figure out if min or max problem
		if (MAX_OR_MIN == true){
			bestDefault = 0;
			worstDefault = 999999999999999999999.0;
		}
		else{
			bestDefault = 999999999999999999999.0;
			worstDefault = 0;
		}

		chromoBestAll.fitness = bestDefault;

		//  Runs
		for (R = 1; R <= RUNS; R++){

			runChromoBest.fitness = bestDefault;
			System.out.println();

			//	Initialize Population
			for (int i=0; i<POP_SIZE; i++){
				parent[i] = new Chromosome();
				offspring[i] = new Chromosome();
			}

			// Generations
			for (G=0; G<GENERATIONS; G++){

				fitnessSum = 0;
				fitnessSum2 = 0;
				genChromoBest.fitness = bestDefault;


				int blockSize = (int) (POP_SIZE/NUM_THREADS);
				int total = 0; 
				
				// Multi-thread evaluation of population fitness for selection
				List<MultithreadFitness> threads = new ArrayList<MultithreadFitness>();

				for (int q = 0; q<NUM_THREADS; q++)
				{
					if(q!=NUM_THREADS-1)
					{
						MultithreadFitness myThing = new MultithreadFitness(problem, total,total+blockSize-1, POP_SIZE, parent);
						myThing.start();
						threads.add(myThing);
					}
					else
					{
						MultithreadFitness myThing = new MultithreadFitness(problem, total, POP_SIZE-1, POP_SIZE, parent);
						myThing.start();
						threads.add(myThing);
					}
					
					total += blockSize; 
				}

				for (MultithreadFitness thread : threads)
				{
					try{
						thread.join();
					} catch (InterruptedException e){
					}
				}

				for (int i=0; i<POP_SIZE; i++){

					fitnessSum = fitnessSum + parent[i].fitness;
					fitnessSum2 = fitnessSum2 +
						parent[i].fitness * parent[i].fitness;

					if (MAX_OR_MIN == true){
						if (parent[i].fitness > genChromoBest.fitness){
							// Chromosome.copyB2A(genChromoBest, parent[i]);
							genChromoBest.chromo = parent[i].chromo;
							genChromoBest.fitness = parent[i].fitness;
							bestOfGenR = R;
							bestOfGenG = G;
						}
						if (parent[i].fitness > runChromoBest.fitness){
							// Chromosome.copyB2A(runChromoBest, parent[i]);
							runChromoBest.chromo = parent[i].chromo;
							runChromoBest.fitness = parent[i].fitness;
							bestOfRunR = R;
							bestOfRunG = G;
						}
						if (parent[i].fitness > chromoBestAll.fitness){
							// Chromosome.copyB2A(chromoBestAll, parent[i]);
							chromoBestAll.chromo = parent[i].chromo;
							chromoBestAll.fitness = parent[i].fitness;
							bestOverAllR = R;
							bestOverAllG = G;
						}
					}
					else {
						if (parent[i].fitness < genChromoBest.fitness){
							// Chromosome.copyB2A(genChromoBest, parent[i]);
							genChromoBest.chromo = parent[i].chromo;
							genChromoBest.fitness = parent[i].fitness;
							bestOfGenR = R;
							bestOfGenG = G;
						}
						if (parent[i].fitness < runChromoBest.fitness){
							// Chromosome.copyB2A(runChromoBest, parent[i]);
							runChromoBest.chromo = parent[i].chromo;
							runChromoBest.fitness = parent[i].fitness;
							bestOfRunR = R;
							bestOfRunG = G;
						}
						if (parent[i].fitness < chromoBestAll.fitness){
							// Chromosome.copyB2A(chromoBestAll, parent[i]);
							chromoBestAll.chromo = parent[i].chromo;
							chromoBestAll.fitness = parent[i].fitness;
							bestOverAllR = R;
							bestOverAllG = G;
						}
					}
				}

				fitnessLog[R][G][0] += fitnessSum / POP_SIZE;
                fitnessLog[R][G][1] += genChromoBest.fitness;

				fitnessStats[0][G] += fitnessSum / POP_SIZE;
				fitnessStats[1][G] += genChromoBest.fitness;

                averagefitness = fitnessSum / POP_SIZE;
				stdevfitness = Math.sqrt(
							Math.abs(fitnessSum2 - 
							fitnessSum*fitnessSum/POP_SIZE)
							/
							(POP_SIZE-1)
							);

				System.out.println(R + "\t" + G +  "\t" + genChromoBest.fitness + "\t" + averagefitness + "\t" + stdevfitness);

			
				summaryFileOutput.write(" R ");
				Padding.rightPadding(R, 3, summaryFileOutput);
				summaryFileOutput.write(" G ");
				Padding.rightPadding(G, 3, summaryFileOutput);
				Padding.rightPadding((int)genChromoBest.fitness, 7, summaryFileOutput);
				Padding.rightPadding(averagefitness, 11, 3, summaryFileOutput);
				Padding.rightPadding(stdevfitness, 11, 3, summaryFileOutput);
				summaryFileOutput.write("\n");


				int parent1 = -1;
				int parent2 = -1;

				
				for (int i=0; i<POP_SIZE; i=i+2){

					// Selection
					parent1 = Chromosome.selectionOfParent();
					parent2 = parent1;
					while (parent2 == parent1){
						parent2 = Chromosome.selectionOfParent();
					}

					//	Crossover
					randnum = r.nextDouble();
					if (randnum < XOVER_RATE){
						Chromosome.crossover(parent[parent1], parent[parent2], offspring[i], offspring[i+1]);
					}
					else {
						
						offspring[i].chromo = parent[parent1].chromo; 
						offspring[i].fitness = -1; 
						
						offspring[i+1].chromo = parent[parent2].chromo; 
						offspring[i+1].fitness = -1; 
					}
				} 

				//	Mutation
				for (int i=0; i<POP_SIZE; i++){
					offspring[i].mutation();
				}

				
				// New offspring become new generation parents
				for (int i=0; i<POP_SIZE; i++){
					
					parent[i].chromo = offspring[i].chromo;
					parent[i].fitness = offspring[i].fitness;
				}

			} 

			Padding.leftPadding(bestOfRunR, 4, summaryFileOutput);
			Padding.rightPadding(bestOfRunG, 4, summaryFileOutput);

			System.out.println(R + "\t" + "B" + "\t"+ runChromoBest.fitness);
			System.out.println(runChromoBest.chromo);

		} 

		Padding.leftPadding("B", 8, summaryFileOutput);

		

		//	Output 
		summaryFileOutput.write("Gen             AvgFit              StdAvg              BestFit              StdBest\n");
		
		for (int i=0; i<GENERATIONS; i++){
			
			
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



			Padding.leftPadding(i, 15, summaryFileOutput);
			Padding.leftPadding(fitnessStats[0][i]/RUNS, 20, 2, summaryFileOutput);
			Padding.leftPadding(stdevAvgFitness, 20, 2, summaryFileOutput);
			Padding.leftPadding(fitnessStats[1][i]/RUNS, 20, 2, summaryFileOutput);
			Padding.leftPadding(stdevBestFitness, 20, 2, summaryFileOutput);
			summaryFileOutput.write("\n");
		}

		summaryFileOutput.write("\n");
		summaryFileOutput.close();

		System.out.println();
		System.out.println("Start:  " + start);
		dateTime = Calendar.getInstance(); 
		Date endTime = dateTime.getTime();
		System.out.println("End  :  " + endTime);
		long duration = System.nanoTime() - begin;
		System.out.println("Duration(ms)  :  " + (duration)/1e9);

	} 

} 



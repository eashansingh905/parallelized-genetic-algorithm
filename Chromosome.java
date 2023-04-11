/*********************************************************************************
* Class defining a chromosome member
*********************************************************************************/

import java.io.*;
import java.util.*;
import java.text.*;

public class Chromosome {


	public String chromo;
	public double fitness; 


	/*********************************** Constructors ***********************************/
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


	/*********************************** Class methods ***********************************/

	public void mutation(){

		String mutatedChromosome = "";
		char x;
		double randnum;
		int mutationType = 1;


		switch (mutationType){

		case 1: 

			for (int j=0; j<20; j++){
				x = this.chromo.charAt(j);
				randnum = Search.r.nextDouble();
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


	/*********************************** Static methods ***********************************/

	// TODO: Parent selection strategy
	public static int selectionOfParent(){ //selectParent
		
		int tournamentType = 2;
		double rWheel = 0;
		double randnum;
		int j = 0; 
		int k = 0; 

		switch(tournamentType){

		case 1:
			randnum = Search.r.nextDouble();
			for (j=0; j<1000; j++){
				rWheel = rWheel + Search.member[j].fitness;
				if (randnum < rWheel) return(j);
			}
			break;

		case 2:
			int tourneySize = 2;
            double minFitness = Double.MAX_VALUE;
            int bestMember = -1;

            for (int i = 0; i < tourneySize; i++) {
                randnum = Search.r.nextDouble();
                int x = (int)(randnum * 1000);
                if (Search.member[x].fitness < minFitness) {
                    minFitness = Search.member[x].fitness;
                    bestMember = x;
                }
            }

            return bestMember;

         default:
         		System.out.println("ERROR - No selection made for tournamentType.");
		}

		return -1; 
	}

	// TODO: Crossover operation
	public static void crossover(Chromosome parent1, Chromosome parent2, Chromosome child1, Chromosome child2){
		int xoverPoint1;
		int xoverPoint2;

		int crossoverType = 1; 

		switch (crossoverType){

		case 1:     //  Single Point Crossover

			//  Select crossover point
			xoverPoint1 = 1 + (int)(Search.r.nextDouble() * (19));

			//  Create child chromosome from parental material
			child1.chromo = parent1.chromo.substring(0,xoverPoint1) + parent2.chromo.substring(xoverPoint1);
			child2.chromo = parent2.chromo.substring(0,xoverPoint1) + parent1.chromo.substring(xoverPoint1);
			break;

		case 2:     //  Two Point Crossover

		case 3:     //  Uniform Crossover

		default:
			System.out.println("ERROR - Bad crossover method selected");
		}

		//  Set fitness values back to zero
		child1.fitness = -1;   //  Fitness not yet evaluated
		child2.fitness = -1;   //  Fitness not yet proportionalized
	}

	
}
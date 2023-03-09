/*********************************************************************************
* Class defining a chromosome member
*********************************************************************************/

import java.io.*;
import java.util.*;

public class Chromosome {


	public String chromo;
	public double fitness; 


	/*********************************** Constructors ***********************************/
	public Chromosome(){

		char gene;
		double rand; 
		chromo = "";

		// Generate a binary candidate solution of length 26
		for (int i = 0; i < 26; i++)
		{
			gene = Math.random() > 0.5 ? '0':'1';
			this.chromo = chromo + gene;  
		}


		this.fitness = -1; 
	}


	/*********************************** Class methods ***********************************/


	// TODO: Mutation operation
	public void mutation(){

	}


	/*********************************** Static methods ***********************************/

	// TODO: Parent selection strategy
	public static int selectionOfParent(){
	
		return -1; 
	}

	// TODO: Crossover operation
	public void crossover(Chromosome parent1, Chromosome parent2, Chromosome child1, Chromosome child2){
	
	}

	
}
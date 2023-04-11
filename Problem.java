/*********************************************************************************
* Problem class for different types of non-convex optimization problems
*********************************************************************************/

import java.io.*;
import java.util.*;
import java.text.*;

class Problem {

	/*********************************** Instance variables ***********************************/
	public String problemName;


	/*********************************** Constructors ***********************************/
	public Problem() {
		System.out.println("Initializing problem....");
	}


	/*********************************** Class methods ***********************************/

	// Calculating a chromosomes raw fitness
	public void calculateFitness(Chromosome chromo)
	{
		System.out.println("Evaluating Fitness Function...");
	}

	// // Print a candidate solution
	// public void printChromosome(Chromosome chromo, FileWriter output) throws java.io.IOException
	// {
	// 	System.out.println("Printing chromosome...");
	// }


}
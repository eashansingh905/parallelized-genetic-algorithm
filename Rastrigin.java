/*********************************************************************************
* Rastrigin Function
*********************************************************************************/

import java.io.*;
import java.util.*;
import java.text.*;

public class Rastrigin extends Problem {

	public Rastrigin() {

		problemName = "Rastrigin Function";
	}

	public void calculateFitness(Chromosome chromo)
	{
		double x1=0;
		double x2=0;
		char gene;
		
		for (int i = 9; i >= 0; i--)
		{
			gene = chromo.chromo.charAt(i);
			if (gene == '1')
			{
				x1 = x1 + (int) Math.pow(2.0, 9-i);
			}
		}

		for (int i = 19; i >= 10; i--)
		{
			gene = chromo.chromo.charAt(i);
			if (gene == '1')
			{
				x2 = x2 + (int) Math.pow(2.0, 19-i);
			}
		}

		x1 = x1/100.0;
		x2 = x2/100.0;

		x1 = x1 - 5.12;
		x2 = x2 - 5.12;

		double rastrigin = 0; 

		rastrigin += (x1*x1) - (10* Math.cos(2*Math.PI*x1)) + 10; 
		rastrigin += (x2*x2) - (10* Math.cos(2*Math.PI*x2)) + 10;

		chromo.fitness = rastrigin;
	}

}
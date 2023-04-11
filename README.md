# parallelized-genetic-algorithm

Non-convex optimization problems tackle an area of mathematics with many real-world applications in fields like signal processing, machine learning, and control systems. Due to the presence of many local optima, these problems become difficult to solve. 
One class of techniques used to solve constrained and unconstrained optimization problems are *genetic algorithms*. Genetic Algorithms (GA) model phenomena observed in biology and metaheuristic techniques by generating better and better “candidate solutions” that aspire towards stronger fitness function values. The generation of these candidate solutions can take time and their scalability is limited due to computation on single processors and high computational load.

This project aims to build a parallelized genetic algorithm (PGA) for solving a non-convex optimization problem, specifically the Rastrigin function.

## Instructions: How to run the code
The code has been written in Java. Below are instructions on how to run the code from the Command-line(Windows).

C:\Users> **javac \*.java**

C:\Users> **java GA**


## Output

Output for the Genetic algorithm is of 2 forms.

	1) A summary txt file is made called **Rastrigin_summary.txt**. In this file you will find information on the run, generation, best individual in the generation, the average fitness of a generation and the standard deviation of the fitness. At the bottom of the file, you will find an overall summary of every generation across all the runs. This includes the average fitness 
	across a generation, the standard deviation of the average fitness, the best fitness, and the standard deviation of the best fitness. 

	2) Commandline output: output similar to the txt file for quick visualization. 


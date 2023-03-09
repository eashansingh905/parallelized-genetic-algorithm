# parallelized-genetic-algorithm

Non-convex optimization problems tackle an area of mathematics with many real-world applications in fields like signal processing, machine learning, and control systems. Due to the presence of many local optima, these problems become difficult to solve. 
One class of techniques used to solve constrained and unconstrained optimization problems are *genetic algorithms*. Genetic Algorithms (GA) model phenomena observed in biology and metaheuristic techniques by generating better and better “candidate solutions” that aspire towards stronger fitness function values. The generation of these candidate solutions can take time and their scalability is limited due to computation on single processors and high computational load.

This project aims to build a parallelized genetic algorithm (DGA) for solving a non-convex optimization problem, specifically the Rastrigin function.

## Instructions: How to run the code
The code has been written in Java. Below are instructions on how to run the code from the Command-line(Windows).

C:\Users> **javac \*.java**

C:\Users> **java GeneticAlgorithm**

*Note: currently the expected output is still a work in progress, thus these instructions have been provided for compilation sakes rather than verification of output.*
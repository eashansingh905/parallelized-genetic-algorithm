public class MultithreadFitness extends Thread {
	
	private int start;
	private int end; 
	private int pop_size;
	private Chromosome member[];
	public static Problem problem;

	public MultithreadFitness(Problem problem, int start, int end, int pop_size, Chromosome member[]){
		this.start = start;
		this.end = end;
		this.member = member;
		this.problem = problem;
	}

	@Override
	public void run() {
		

		for (int i = start; i <= end; i++)
		{
			problem.calculateFitness(member[i]);	
			// System.out.println("member: " + i + ", Chromo: " + member[i].chromo + ", Fitness: " + member[i].fitness);			
		}
	}

}
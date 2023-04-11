public class rastriginTestFunction{

	public static double calculateFitness(String s)
	{
		double x1=0;
		char gene;
		for (int i = 9; i >= 0; i--)
		{
			gene = s.charAt(i);
			if (gene == '1')
			{
				x1 = x1 + (int) Math.pow(2.0, 9-i);
			}
		}	

		return x1;
	}

	public static void main(String [] args)
	{
		double x1 = calculateFitness("1011001000");
		x1 = x1/100.0;

		double x2 = calculateFitness("1011001000");
		x2 = x2/100.0;

		x1 = x1 - 5.12;
		x2 = x2 - 5.12;

		double rastrigin = 0; 

		rastrigin += (x1*x1) - (10* Math.cos(2*Math.PI*x1)) + 10; 
		rastrigin += (x2*x2) - (10* Math.cos(2*Math.PI*x2)) + 10; 


		// x1 = Math.toRadians(x1);
		// x2 = Math.toRadians(x2);

		// double rastrigin = 20 + Math.pow(x1, 2) + Math.pow(x2, 2) - (10 * (Math.cos(2*Math.PI*x1)) + (Math.cos(2*Math.PI*x2)));

		System.out.println(rastrigin);
	}
}
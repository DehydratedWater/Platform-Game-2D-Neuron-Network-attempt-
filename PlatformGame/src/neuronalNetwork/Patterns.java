package neuronalNetwork;

public class Patterns 
{
	public static float calculateSum(float inputs[], float weights[], float bias)
	{
		float sum = bias;
		for(int i = 0; i < inputs.length; i++)
		{
			sum+=inputs[i]*weights[i];
		}
		return sum;
	}
	
	public static float BinaryStep(float sum)
	{
		if(sum>0.5f)
			return 1;
		else
			return 0;
	}
	
	public static float Logistic(float sum)
	{
		return (float) (1/(1+Math.exp(-sum)));
	}
	public static float LogisticDerivative(float sum)
	{
		return (float) (Logistic(sum)*(1-Logistic(sum)));
//		return (float) (Math.exp(-sum)/((1+Math.exp(-sum))*(1+Math.exp(-sum))));
	}
	public static float Tahn(float sum)
	{
		return (float) (Math.tanh(sum));
	}
}

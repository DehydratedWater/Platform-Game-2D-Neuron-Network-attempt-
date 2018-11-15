package neuronalNetwork;

import java.util.Random;

public class Neuron 
{
	private float weights[];
	private float inputs[];
	private int outputs[];
	private float bias;
	private float resoult;
	private int pattern = 0;
	private float mistake = 0;
	
	
	public Neuron(int intutsNum, int outputsNum, float bias, int patternType)
	{
		inputs = new float[intutsNum];
		weights = new float[intutsNum];
		setOutputs(new int[outputsNum]);
		this.bias  = bias;
		this.pattern = patternType;
	}
	
	public Neuron(int intutsNum, int output[], float bias, int patternType)
	{
		inputs = new float[intutsNum];
		weights = new float[intutsNum];
		this.setOutputs(output);
		this.bias  = bias;
		this.pattern = patternType;
	}
	
	public void randomizeWeights()
	{
		Random r = new Random();
		for(int i = 0; i < weights.length; i++)
		{
			if(r.nextInt(100)<50)
				weights[i] = -r.nextFloat();
			else
				weights[i] = r.nextFloat();
			System.out.println(weights[i]);
		}
	}
	public void randomizeWeights(Random r)
	{
		for(int i = 0; i < weights.length; i++)
		{
			if(r.nextInt(100)<50)
				weights[i] = -r.nextFloat();
			else
				weights[i] = r.nextFloat();
			//System.out.println(weights[i]);
		}
	}
	public void setInputValue(float value, int index)
	{
		inputs[index] = value;
	}

	public void lernNeuron()
	{
		//System.out.println("B³¹d "+mistake);
		for(int i = 0; i < inputs.length; i++)
		{
			weights[i] += Network.learningConstant * mistake * Patterns.LogisticDerivative(Patterns.calculateSum(inputs, weights, bias)) * inputs[i];
		}
	}
	
	public void calculateResoult()
	{
		float sum = Patterns.calculateSum(inputs, weights, bias);
		
		//System.out.println("Wyliczanie sumy "+sum+" "+bias);
		if(pattern == 1)
		{
			resoult = Patterns.BinaryStep(sum);
		}
		else if(pattern == 2)
		{
			resoult = Patterns.Logistic(sum);
		}
		else if(pattern == 3)
		{
			resoult = Patterns.Tahn(sum);
		}
	}

	public float getResoult() {
		return resoult;
	}

	public void setResoult(float resoult) {
		this.resoult = resoult;
	}

	public int[] getOutputs() {
		return outputs;
	}
	public void setOutput(int index, int value) {
		outputs[index] = value;
	}
	public void setOutputs(int outputs[]) {
		this.outputs = outputs;
	}
	public void printInputsAndOutputs()
	{
		System.out.println(inputs.length+" "+outputs.length);
	}
	public float getMistakeWithWeight(int indexOfWeight)
	{
		return weights[indexOfWeight]*mistake;
	}
	public void calculateMistakeForLastLayer(float rightValue)
	{
		mistake = rightValue - resoult;
	}
	
	public void addMistake(float weightMistakeValue)
	{
		mistake += weightMistakeValue;
	}
	
	public float getMistake()
	{
		return mistake;
	}
}

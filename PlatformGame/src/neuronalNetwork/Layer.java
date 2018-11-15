package neuronalNetwork;

import java.util.Random;

public class Layer 
{
	private Neuron[] neuronLayer;
	public Layer(int layerSize)
	{
		neuronLayer = new Neuron[layerSize];
	}
	public void calculateValues()
	{
		for(int i = 0; i < neuronLayer.length; i++)
		{
			neuronLayer[i].calculateResoult();
		}
	}
	public void drawNeuronsMistakes()
	{
		for(int i = 0; i < neuronLayer.length; i++)
		{
			System.out.print(neuronLayer[i].getMistake()+" ");
		}
		System.out.println();
	}
	public void lernNeurons()
	{
		for(int i = 0; i < neuronLayer.length; i++)
		{
			neuronLayer[i].lernNeuron();
		}
	}
	public void randomizeWeights(Random r)
	{
		for(int i = 0; i < neuronLayer.length; i++)
		{
			neuronLayer[i].randomizeWeights(r);
		}
	}
	public void printValues()
	{
		for(int i = 0; i < neuronLayer.length; i++)
		{
			System.out.print(neuronLayer[i].getResoult()+" ");
		}
		System.out.println();
	}
	public void setInputValue(float value, int neueonIndex, int tabIndex)
	{
		neuronLayer[neueonIndex].setInputValue(value, tabIndex);
	}
	
	public Neuron getNeuron(int index)
	{
		return neuronLayer[index];
	}
	public float getNeuronResoult(int index)
	{
		return neuronLayer[index].getResoult();
	}
	
	public void setValueToAllTab(float value, int tabIndex)
	{
		for(int i = 0; i < getLayerSize(); i++)
		{
			neuronLayer[i].setInputValue(value, tabIndex);
		}
	}
	
	public void generateInputLayer(int outputSize, int patternType)
	{
		System.out.println("Tworzenie warstwy wejœciowej o wyjœciowej iloœci po³¹czeñ z ka¿dego neurona "+outputSize);
		Random r = new Random();
		for(int i = 0; i < neuronLayer.length; i++)
		{
			float bias = 0;
			if(r.nextInt(100)<50)
				bias = -r.nextFloat();
			else
				bias = r.nextFloat();
			neuronLayer[i] = new Neuron(1, outputSize, bias, patternType);
			neuronLayer[i].randomizeWeights();
		}
		setOutputsAsFullListOfNextLayer(outputSize);
	}
	public void generateInputLayerAllToAll(int outputSize, int patternType)
	{
		System.out.println("Tworzenie warstwy wejœciowej o wyjœciowej iloœci po³¹czeñ z ka¿dego neurona "+outputSize);
		Random r = new Random();
		for(int i = 0; i < neuronLayer.length; i++)
		{
			float bias = 0;
			if(r.nextInt(100)<50)
				bias = -r.nextFloat();
			else
				bias = r.nextFloat();
			neuronLayer[i] = new Neuron(1, outputSize, bias, patternType);
			neuronLayer[i].randomizeWeights();
		}
		setOutputsAsFullListOfNextLayer(outputSize);
	}
	public void generateMiddleLayer(int inputSize, int outputSize, int patternType)
	{
		System.out.println("Tworzenie warstwy œrodkowej "
				+ "o wejœciowej iloœci po³¹czeñ do ka¿dego neurona "+inputSize
				+ " o wyjœciowej iloœci po³¹czeñ z ka¿dego neurona "+outputSize);
		Random r = new Random();
		for(int i = 0; i < neuronLayer.length; i++)
		{
			float bias = 0;
			if(r.nextInt(100)<50)
				bias = -r.nextFloat();
			else
				bias = r.nextFloat();
			neuronLayer[i] = new Neuron(inputSize, outputSize, bias, patternType);
			neuronLayer[i].randomizeWeights();
		}
		setOutputsAsFullListOfNextLayer(outputSize);
	}
	
	public void generateLastLayer(int inputSize, int patternType)
	{
		System.out.println("Tworzenie warstwy koñczowej o wejœciowej iloœci po³¹czeñ do ka¿dego neurona "+inputSize);
		Random r = new Random();
		for(int i = 0; i < neuronLayer.length; i++)
		{
			float bias = 0;
			if(r.nextInt(100)<50)
				bias = -r.nextFloat();
			else
				bias = r.nextFloat();
			
			neuronLayer[i] = new Neuron(inputSize, 1, bias, patternType);
			neuronLayer[i].randomizeWeights();
		}
	}
	public void setOutputsAsFullListOfNextLayer(int outputSize)
	{
		for(int i = 0; i < neuronLayer.length; i++)
		{
		for(int j = 0; j < outputSize; j++)
		{
			neuronLayer[i].setOutput(j, j);
		}
		}
	}
	public int getLayerSize()
	{
		return neuronLayer.length;
	}
}

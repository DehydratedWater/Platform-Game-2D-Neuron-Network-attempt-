package neuronalNetwork;

import java.util.Random;

public class Network 
{
	private float inputTab[];
	private float outputTab[];
	private boolean resoultTab[];
	private Layer layerTab[];
	public static float learningConstant = 0.01f;
	
	public Network(int layerNum, int[] layersSize, int inputSize, int outputSize)
	{
		setInputTab(new float[inputSize]);
		outputTab = new float[outputSize];
		resoultTab = new boolean[outputSize];
		layerTab = new Layer[layerNum];
		for(int i = 0; i < layersSize.length; i++)
		{
			layerTab[i] = new Layer(layersSize[i]);
		}
	}

	
	public void generateStandardNetworkAllToAll(int patternType)
	{
		System.out.println("Generowanie standardowej sieci");
		//layerTab[0].generateInputLayer(layerTab[0].getLayerSize(), patternType);
		layerTab[0].generateMiddleLayer(layerTab[0].getLayerSize(), layerTab[1].getLayerSize(), patternType);
		for(int i = 1; i < layerTab.length-1; i++)
		{
			layerTab[i].generateMiddleLayer(layerTab[i-1].getLayerSize(), layerTab[i].getLayerSize(), patternType);
		}
		layerTab[layerTab.length-1].generateLastLayer(layerTab[layerTab.length-2].getLayerSize(), patternType);
		System.out.println("Wygenerowano standardow¹ sieæ");
	}
	
	public void calculateMistackes(float[] rightValues)
	{
		int layerNum = layerTab.length;
		for(int i = 0; i < layerTab[layerNum-1].getLayerSize(); i ++)
		{
			layerTab[layerNum-1].getNeuron(i).calculateMistakeForLastLayer(rightValues[i]);
		}
		for(int i = layerNum-2; i >= 0; i--)
		{
			Layer editedLayer = layerTab[i];
			Layer sourceLayer = layerTab[i+1];
			
			for(int k = 0; k < editedLayer.getLayerSize(); k++)
			{
				for(int j = 0; j < sourceLayer.getLayerSize(); j++)
				{
					float mistake = sourceLayer.getNeuron(j).getMistakeWithWeight(k);
					editedLayer.getNeuron(k).addMistake(mistake);
				}
			}
		}
	}
	public void printMistakes()
	{
		int layerNum = layerTab.length;
		for(int i = 0; i < layerTab[layerNum-1].getLayerSize(); i ++)
		{
			System.out.print(layerTab[layerNum-1].getNeuron(i).getMistake()+" ");
		}
		System.out.println();
	}
	
	public float getMedianMistake()
	{
		float mistakesSum = 0;
		int layerNum = layerTab.length;
		for(int i = 0; i < layerTab[layerNum-1].getLayerSize(); i ++)
		{
			mistakesSum+=Math.abs(layerTab[layerNum-1].getNeuron(i).getMistake());
		}
		return (float)mistakesSum/layerTab[layerNum-1].getLayerSize();
	}
	public float[] calculateFloatTabFromBooleanTab(boolean[] tab, float value)
	{
		float[] fTab = new float[tab.length];
		for(int i = 0; i < fTab.length; i++)
		{
			
			if(!tab[i])
				fTab[i] = 0.5f-value;
			else
				fTab[i] = 0.5f+value;
			
		}
		return fTab;
	}
	
	public void randomizeAllWeights()
	{
		System.out.println("Losowanie wag");
		Random r = new Random();
		for(int i = 0; i < layerTab.length; i++)
		{
			layerTab[i].randomizeWeights(r);
		}
	}
	
	public void setResoultsTab()
	{
		for(int i = 0; i < outputTab.length; i++)
		{
			outputTab[i] = layerTab[layerTab.length-1].getNeuronResoult(i);
		}
		generateResoultTab();
	}
	
	public void calculateResoult()
	{
		
		//System.out.println("Rozpoczywanie wyliczania wartosci");
		for(int i = 0; i < inputTab.length; i++)
		{
			for(int j = 0; j < inputTab.length; j++)
			{
			layerTab[0].setInputValue(inputTab[i], i, j);
			}
		}
		//System.out.println("Ustawiono wartoœæ pocztkowe");
		for(int i = 0; i < layerTab.length-1; i++)
		{
			layerTab[i].calculateValues();
			
			int inputLayerTabSize = layerTab[i].getLayerSize();
			int outputLayerTabSize = layerTab[i+1].getLayerSize();
		//	System.out.println("Wyliczono wartoœci warstwy "+i);
			for(int k = 0; k < inputLayerTabSize; k++)
			{
				float val = layerTab[i].getNeuronResoult(k);
				for(int j = 0; j < outputLayerTabSize; j++)
				{
					layerTab[i+1].setInputValue(val, j, k);
				}
			}
		}
		layerTab[layerTab.length-1].calculateValues();
		//System.out.println("Wyliczono wartoœci warstwy "+(layerTab.length-1));
		setResoultsTab();
		System.out.println(outputTab[0]+" "+outputTab[1]+" "+outputTab[2]+" "+outputTab[3]+" "+outputTab[4]);
//		for(int i = 0; i < layerTab.length; i++)
//		{
//			layerTab[i].printValues();
//		}
	}
	
	
	public void learnNetworkOnMistakes()
	{
		
		//System.out.println("Rozpoczywanie wyliczania wartosci");
		for(int i = 0; i < inputTab.length; i++)
		{
			for(int j = 0; j < inputTab.length; j++)
			{
			layerTab[0].setInputValue(inputTab[i], i, j);
			}
		}
		//System.out.println("Ustawiono wartoœæ pocztkowe");
		for(int i = 0; i < layerTab.length-1; i++)
		{
			layerTab[i].printValues();
			layerTab[i].lernNeurons();
			layerTab[i].calculateValues();
			
			int inputLayerTabSize = layerTab[i].getLayerSize();
			int outputLayerTabSize = layerTab[i+1].getLayerSize();
		//	System.out.println("Wyliczono wartoœci warstwy "+i);
			for(int k = 0; k < inputLayerTabSize; k++)
			{
				float val = layerTab[i].getNeuronResoult(k);
				for(int j = 0; j < outputLayerTabSize; j++)
				{
					layerTab[i+1].setInputValue(val, j, k);
				}
			}
		}
		layerTab[layerTab.length-1].printValues();
		layerTab[layerTab.length-1].lernNeurons();
		layerTab[layerTab.length-1].calculateValues();
		
		//System.out.println("Wyliczono wartoœci warstwy "+(layerTab.length-1));
		setResoultsTab();
		System.out.println("Wynik");
		System.out.println(outputTab[0]+" "+outputTab[1]+" "+outputTab[2]+" "+outputTab[3]+" "+outputTab[4]);
//		for(int i = 0; i < layerTab.length; i++)
//		{
//			layerTab[i].printValues();
//		}
	}
	public boolean[] getResoultTab() {
		return resoultTab;
	}

	
	public void printOutputTab() {
		for(int i = 0; i < outputTab.length; i++)
		{
			System.out.print(outputTab[i]+" ");
		}
		System.out.println();
	}

	public float[] getOutputTab() {
		return outputTab;
	}


	public void setOutputTab(float[] outputTab) {
		this.outputTab = outputTab;
	}


	public void setResoultTab(boolean[] resoultTab) {
		this.resoultTab = resoultTab;
	}


	public float normaliseValues(float input, float max)
	{
		return input/max;
	}
	
	public void generateResoultTab()
	{
		for(int i = 0; i < resoultTab.length; i++)
		{
			resoultTab[i] = (outputTab[i]>0.5) ? true : false;
		}
	}
	public float[] getInputTab() {
		return inputTab;
	}

	public void setInputTab(float inputTab[]) {
		System.out.println("ustawianie nowej tablicy wartosci");
//		for(int i = 0; i < inputTab.length; i++)
//			System.out.print(inputTab[i]+" ");
//		System.out.println();
		this.inputTab = inputTab;
	}
	
	
}

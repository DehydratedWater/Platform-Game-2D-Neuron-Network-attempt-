package managers;

public class ControlsManagerForNeuronNetwork
{
	public boolean UP, DOWN, LEFT, RIGHT;
	public boolean SPACE, CTRL, SHIFT, ALT;
	public boolean W,A,S,D,Q,E,R;
	
	public boolean mLEFT, mRIGHT, mCENTRE;
	public boolean cLEFT, cRIGHT, cCENTRE;
	public float mouseWeel;
	public int mX, mY; //ostatnia pozycja myszy
	public int DX, DY; //przesuniêcie myszy
	
	public ControlsManagerForNeuronNetwork() 
	{
		
	}
	
	
	public void ubdateButtonsSet(boolean[] buttonsSet) //W A S D SPACE
	{
		W = buttonsSet[0];
		A = buttonsSet[1];
		S = buttonsSet[2];
		D = buttonsSet[3];
		SPACE = buttonsSet[4];
	}
	
	public void reset()
	{
		DX=0;
		DY=0;
		mouseWeel = 0;
		cLEFT = false;
		cCENTRE = false;
		cRIGHT = false;
	}
	

}

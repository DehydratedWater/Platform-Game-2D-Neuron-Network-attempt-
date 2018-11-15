package collizions;

import java.awt.Color;
import java.awt.Graphics2D;

public class BBox 
{
	private float maxX, maxY;
	private float scaleX = 1, scaleY = 1;
	public float startX, startY;
	public float X, Y;
	public BBox(float startX, float startY, float maxX, float maxY) 
	{
		this.startX = startX;
		this.startY = startY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	public void moveTo(float X, float Y)
	{
		this.X = X;
		this.Y = Y;
	}
	public void setScale(float X, float Y)
	{
		this.scaleX = X;
		this.scaleY = Y;
	}
	public float[] getBBTab()
	{
		float[] tab = new float[6];
		float x = X+startX*scaleX;
		float y = Y+startY*scaleY;
		
		tab[0] = x;
		tab[1] = y;
		tab[2] = x+maxX*scaleX;
		tab[3] = y+maxY*scaleY;
		tab[4] = (x+x+maxX*scaleX)/2;
		tab[5] = (y+y+maxY*scaleY)/2;
		return tab;
	}
	
	public void drawBBTab(Graphics2D g)
	{
		float[] tab = new float[6];
		float x = X+startX*scaleX;
		float y = Y+startY*scaleY;
		
		tab[0] = x;
		tab[1] = y;
		tab[2] = x+maxX*scaleX;
		tab[3] = y+maxY*scaleY;
		tab[4] = (x+x+maxX*scaleX)/2;
		tab[5] = (y+y+maxY*scaleY)/2;
		g.setColor(Color.black);
		g.drawRect((int)x, (int)y, (int)(maxX*scaleX), (int)(maxY*scaleY));
	}
	
	public void drawBBTabWithOutColor(Graphics2D g)
	{
		float[] tab = new float[6];
		float x = X+startX*scaleX;
		float y = Y+startY*scaleY;
		
		tab[0] = x;
		tab[1] = y;
		tab[2] = x+maxX*scaleX;
		tab[3] = y+maxY*scaleY;
		tab[4] = (x+x+maxX*scaleX)/2;
		tab[5] = (y+y+maxY*scaleY)/2;
		g.drawRect((int)x, (int)y, (int)(maxX*scaleX), (int)(maxY*scaleY));
	}
}

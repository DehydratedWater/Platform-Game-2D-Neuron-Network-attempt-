package map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import collizions.BBox;
import collizions.BBoxCollizionChecker;
import managers.textureManager;
import windows.Window;

public class Map implements Constants
{
	private int[][] mapTab;
	private int SizeX, SizeY;
	private float X = 0, Y = 0, scaleX = 1, scaleY = 1;
	private static float[] itemValue = new float[]{0,1,5,10,20};
	
	public Map(int x, int y)
	{
		mapTab = new int[x][y];
		SizeX = x;
		SizeY = y;
	}
	public void drawMap(Graphics2D g, textureManager tm, int sizeX, int sizeY)
	{
		int sX = (int) -(X/(BLOCK_SIZE*scaleX))-1;
		int sY = (int) -(Y/(BLOCK_SIZE*scaleY))-1;
		if(sX<0)
			sX = 0;
		if(sY<0)
			sY = 0;
		if(sX>SizeX)
			sX = SizeX-1;
		if(sY>SizeY)
			sY = SizeY-1;
		int eX = (int)(sizeX/(BLOCK_SIZE*scaleX))+sX+3;
		int eY = (int)(sizeY/(BLOCK_SIZE*scaleY))+sY+3;
		if(eX>SizeX)
			eX=SizeX;
		if(eY>SizeY)
			eY=SizeY;
		//System.out.println(sX+" "+sY+" - "+eX+" "+eY);
		for(int i = sX; i < eX; i++)
		{
			for(int j = sY; j < eY; j++)
			{
				g.drawImage(tm.getTexture(getField(i, j)).texture, (int)(i*BLOCK_SIZE*scaleX+X), (int)(j*BLOCK_SIZE*scaleY+Y), (int)(BLOCK_SIZE*scaleX), (int)(BLOCK_SIZE*scaleY), null);
			}
		}
	}
	public void checkCollizionDrawBoundingBoxes(Graphics2D g, int sizeX, int sizeY, BBox[] mapTab, BBox player)
	{
		int sX = (int) -(X/(BLOCK_SIZE*scaleX))-1;
		int sY = (int) -(Y/(BLOCK_SIZE*scaleY))-1;
		if(sX<0)
			sX = 0;
		if(sY<0)
			sY = 0;
		if(sX>SizeX)
			sX = SizeX-1;
		if(sY>SizeY)
			sY = SizeY-1;
		int eX = (int)(sizeX/(BLOCK_SIZE*scaleX))+sX+3;
		int eY = (int)(sizeY/(BLOCK_SIZE*scaleY))+sY+3;
		if(eX>SizeX)
			eX=SizeX;
		if(eY>SizeY)
			eY=SizeY;
		//System.out.println(sX+" "+sY+" - "+eX+" "+eY);
		for(int i = sX; i < eX; i++)
		{
			for(int j = sY; j < eY; j++)
			{
				int type = getField(i, j);
				if(type==0)
				{
					
				}
				else
				{
					mapTab[type].moveTo((i*BLOCK_SIZE*scaleX+X), (j*BLOCK_SIZE*scaleY+Y));
					mapTab[type].drawBBTab(g);
					if(BBoxCollizionChecker.isColliding(player, mapTab[type]))
					{
						g.setColor(Color.red);
						g.setStroke(new BasicStroke(3));
						mapTab[type].drawBBTabWithOutColor(g);
						g.setStroke(new BasicStroke(1));
						g.setColor(Color.black);
					}
				}
			}
		}
	}
	public float[] checkCollizionWithMove(Graphics2D g, int sizeX, int sizeY, BBox[] mapTab, float locX, float locY, BBox player)
	{
		boolean down = false, up = false, left = false, right = false;
		boolean colisionWithLayer = false;
		//System.out.println("Rozpoczêto wykrywanie kolizji");
		int sX = (int) -(X/(BLOCK_SIZE*scaleX))-1;
		int sY = (int) -(Y/(BLOCK_SIZE*scaleY))-1;
		if(sX<0)
			sX = 0;
		if(sY<0)
			sY = 0;
		if(sX>SizeX)
			sX = SizeX-1;
		if(sY>SizeY)
			sY = SizeY-1;
		int eX = (int)(sizeX/(BLOCK_SIZE*scaleX))+sX+3;
		int eY = (int)(sizeY/(BLOCK_SIZE*scaleY))+sY+3;
		if(eX>SizeX)
			eX=SizeX;
		if(eY>SizeY)
			eY=SizeY;
		//System.out.println(sX+" "+sY+" - "+eX+" "+eY);

		for(int i = sX; i < eX; i++)
		{
			for(int j = sY; j < eY; j++)
			{
				int type = getField(i, j);
				if(type==0)
				{
					
				}
				else
				{
					mapTab[type].moveTo((i*BLOCK_SIZE*scaleX+X), (j*BLOCK_SIZE*scaleY+Y));
					mapTab[type].drawBBTab(g);
					
					
					float[] Vector = BBoxCollizionChecker.getCollisionVector(player, mapTab[type]);
					int side = BBoxCollizionChecker.getCollisionSide(player, mapTab[type]);
					if(Vector!=null)
					{
						if(getField(i, j)>3&&getField(i, j)<17)
						{
						if(!isItInternalCollision(i, j, side))
						{
							
							if(side==1)
							{
								//System.out.println("Od góry ");	
								if(up == false)
								{
									locX -= Vector[0];
									locY -= Vector[1];
									up = true;
									Window.canJump = false;
								}
							}
							if(side==2)
							{
								//System.out.println("Od do³u ");
								if(down == false)
								{
									locX -= Vector[0];
									locY -= Vector[1];
									down = true;
									Window.canJump = true;
								}
							}
							if(side==3)
							{
								//System.out.println("Od lewej ");
								if(left == false)
								{
									locX -= Vector[0];
									locY -= Vector[1];
									left = true;
								}
							}
							if(side==4)
							{
								//System.out.println("Od prawej ");
								if(right == false)
								{
									locX -= Vector[0];
									locY -= Vector[1];
									right = true;
								}
							}
						}
						}
						else if(getField(i, j)>16&&getField(i, j)<20)
						{
							if(side==2&&getField(i, j)==18)
							{
								//System.out.println("Od do³u drabina ");
								if(down == false)
								{
									locX -= Vector[0];
									locY -= Vector[1];
									down = true;
								}
							}
							Window.canJump = true;
							colisionWithLayer = true;
						}
						else if(getField(i, j)>0&&getField(i, j)<17)
						{
							if(side==2)
							{
								//System.out.println("Od do³u ");
								if(down == false)
								{
									locX -= Vector[0];
									locY -= Vector[1];
									down = true;
									Window.canJump = true;
								}
							}
						}
						else if(getField(i, j)==20)
						{
							Window.lifePoints = 0;
						}
						
					}
					Window.isOnLayer = colisionWithLayer;
//					
//					
				}
			}
		}
		//System.out.println("bounds "+bounds[0]+" "+bounds[1]+" "+bounds[2]+" "+bounds[3]);
		//System.out.println("Zakoñczono wykrywanie kolizji");
		return new float[]{locX, locY};
	}
	
	public boolean isItInternalCollision(int i, int j, int side)
	{
		if(side==1)
		{
			//System.out.println("Sprawdzanie przy kolizji od góry "+i+" "+j);
			int k = j+1;
			//System.out.println("Sprawdzanie bloku "+i+" "+k+" "+getField(i, k));
			if(k>SizeY-1)
				return false;
			if(getField(i, k)>3&&getField(i, k)<17)
				return true;
			else
				return false;
		}
		if(side==2)
		{
			//System.out.println("Sprawdzanie przy kolizji od do³u "+i+" "+j);
			int k = j-1;
			//System.out.println("Sprawdzanie bloku "+i+" "+k+" "+getField(i, k));
			if(k<0)
				return false;
			if(getField(i, k)>3&&getField(i, k)<17)
				return true;
			else
				return false;
		}
		if(side==3)
		{
			int k = i+1;
			if(k>SizeX-1)
				return false;
			if(getField(k, j)>3&&getField(k, j)<17)
				return true;
			else
				return false;
		}
		if(side==4)
		{
			int k = i-1;
			if(k<0)
				return false;
			if(getField(k, j)>3&&getField(k, j)<17)
				return true;
			else
				return false;
		}
		return false;
	}
	
	public void checkCollizionWithGrab(Graphics2D g, int sizeX, int sizeY, BBox[] mapTab, float locX, float locY, BBox player)
	{
		int sX = (int) -(X/(BLOCK_SIZE*scaleX))-1;
		int sY = (int) -(Y/(BLOCK_SIZE*scaleY))-1;
		if(sX<0)
			sX = 0;
		if(sY<0)
			sY = 0;
		if(sX>SizeX)
			sX = SizeX-1;
		if(sY>SizeY)
			sY = SizeY-1;
		int eX = (int)(sizeX/(BLOCK_SIZE*scaleX))+sX+3;
		int eY = (int)(sizeY/(BLOCK_SIZE*scaleY))+sY+3;
		if(eX>SizeX)
			eX=SizeX;
		if(eY>SizeY)
			eY=SizeY;
		//System.out.println(sX+" "+sY+" - "+eX+" "+eY);
		for(int i = sX; i < eX; i++)
		{
			for(int j = sY; j < eY; j++)
			{
				int type = getField(i, j);
				if(type==0)
				{
					
				}
				else
				{
					mapTab[type].moveTo((i*BLOCK_SIZE*scaleX+X), (j*BLOCK_SIZE*scaleY+Y));
					mapTab[type].drawBBTab(g);
					float[] Vector = BBoxCollizionChecker.getCollisionVector(player, mapTab[type]);
					if(Vector!=null)
					{
						Window.points += itemValue[getField(i, j)];
						setField(0, i, j);
					}
				}
			}
		}
	}
	
	
	
	public int getMaxItemValue()
	{
		int k = 0;
		for(int i = 0; i < SizeX; i++)
		{
			for(int j = 0; j < SizeY; j++)
			{
				k+=itemValue[getField(i, j)];
			}
		}
		return k;
	}
	public int[] getClicked(int x, int y)
	{
		float locX = x - X;
		float locY = y - Y;
		
		locX /= (BLOCK_SIZE*scaleX);
		locY /= (BLOCK_SIZE*scaleY);
		
		if(locX<0)
			return null;
		if(locY<0)
			return null;
		if(locX>SizeX)
			return null;
		if(locY>SizeY)
			return null;
		return new int[]{(int)locX, (int)locY};
	}
	
	public void setClicked(Graphics2D g, int x, int y)
	{
		float locX = x - X;
		float locY = y - Y;
		
		locX /= (BLOCK_SIZE*scaleX);
		locY /= (BLOCK_SIZE*scaleY);
		
		if(locX<0)
			return;
		if(locY<0)
			return;
		if(locX>SizeX)
			return;
		if(locY>SizeY)
			return;
		//System.out.println((int)locX+" "+(int)locY);
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(3));
		g.drawRect((int)((int)locX*BLOCK_SIZE*scaleX+X), (int)((int)locY*BLOCK_SIZE*scaleY+Y), (int)(BLOCK_SIZE*scaleX), (int)(BLOCK_SIZE*scaleY));
		g.setStroke(new BasicStroke(1));
	}
	
	public int getField(int x, int y)
	{
		return mapTab[x][y];
	}
	public int getFieldWithCheck(int x, int y)
	{
		if(x<0)
			return 0;
		if(y<0)
			return 0;
		if(x>SizeX-1)
			return 0;
		if(y>SizeY-1)
			return 0;
		return mapTab[x][y];
	}
	public void setField(int value, int x, int y)
	{
		mapTab[x][y] = value;
	}
	public void moveMap(float x, float y)
	{
		X+=x*scaleX;
		Y+=y*scaleY;
	}
	public float getScaleX() {
		return scaleX;
	}
	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}
	public float getScaleY() {
		return scaleY;
	}
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}
	public float getX() {
		return X;
	}
	public void setX(float x) {
		X = x;
	}
	public float getY() {
		return Y;
	}
	public void setY(float y) {
		Y = y;
	}
	public void scaleMap(float sx, float sy)
	{
		scaleX+=sx;
		scaleY+=sy;
		if(scaleX<0.1f)
			scaleX = 0.1f;
		if(scaleY<0.1f)
			scaleY = 0.1f;
	}
	public int getSizeY() {
		return SizeY;
	}
	public void setSizeY(int sizeY) {
		SizeY = sizeY;
	}
	public int getSizeX() {
		return SizeX;
	}
	public void setSizeX(int sizeX) {
		SizeX = sizeX;
	}
	public int[][] getMapTab() {
		return mapTab;
	}
	public void setMapTab(int[][] mapTab) {
		this.mapTab = mapTab;
	}
}

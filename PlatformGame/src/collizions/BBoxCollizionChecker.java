package collizions;

public class BBoxCollizionChecker 
{
	public static float[] getCollisionVector(BBox staticBox, BBox box)
	{
		float[] vector = null;
		
		float tab1[] = staticBox.getBBTab();
		float tab2[] = box.getBBTab();
		
		if(!(tab1[0]>=tab2[2]||tab1[2]<=tab2[0]||tab1[1]>=tab2[3]||tab1[3]<=tab2[1]))
		{
			vector = new float[2];
			float XPointer = tab1[4]-tab2[4];
			float YPointer = tab1[5]-tab2[5];
			float l1 = (tab1[3]-tab1[1])/2+(tab2[3]-tab2[1])/2;
			float l2 = (tab1[2]-tab1[0])/2+(tab2[2]-tab2[0])/2;
			//System.out.println("Kolizja dwóch prostok¹tów "+XPointer+" "+YPointer);
			boolean vertical = false;
			if(Math.abs(XPointer/l2)>=Math.abs(YPointer/l1))
				vertical = false;
			else
				vertical = true;
			//System.out.println("Kolizja dwóch prostok¹tów "+XPointer+" "+YPointer+" "+vertical);
			float L1, L2;
			if(vertical)
			{
				L1 = (tab1[3]-tab1[1])/2;
				L2 = (tab2[3]-tab2[1])/2;
				
				float dist = (L1+L2)-(Math.abs(tab1[5]-tab2[5]));
				//System.out.println("Zag³êbienie na "+dist);
				if(YPointer>0)
				{
					vector[0] = 0;
					vector[1] = -dist;
				}
				else
				{
					vector[0] = 0;
					vector[1] = +dist;
				}
			}
			else
			{
				
				L1 = (tab1[2]-tab1[0])/2;
				L2 = (tab2[2]-tab2[0])/2;
				
				float dist = (L1+L2)-(Math.abs(tab1[4]-tab2[4]));
				//System.out.println("Zag³êbienie na "+dist);
				if(XPointer>0)
				{
					vector[0] = -dist;
					vector[1] = 0;
				}
				else
				{
					vector[0] = +dist;
					vector[1] = 0;
				}
			}
		}
		return vector;
	}
	
	public static boolean isColliding(BBox staticBox, BBox box)
	{
		float tab1[] = staticBox.getBBTab();
		float tab2[] = box.getBBTab();
		
		if(!(tab1[0]>=tab2[2]||tab1[2]<=tab2[0]||tab1[1]>=tab2[3]||tab1[3]<=tab2[1]))
		{
			return true;
		}
		return false;
	}
	public static int getCollisionSide(BBox staticBox, BBox box)
	{
		float tab1[] = staticBox.getBBTab();
		float tab2[] = box.getBBTab();
		
		if(!(tab1[0]>=tab2[2]||tab1[2]<=tab2[0]||tab1[1]>=tab2[3]||tab1[3]<=tab2[1]))
		{
			float XPointer = tab1[4]-tab2[4];
			float YPointer = tab1[5]-tab2[5];
			float l1 = (tab1[3]-tab1[1])/2+(tab2[3]-tab2[1])/2;
			float l2 = (tab1[2]-tab1[0])/2+(tab2[2]-tab2[0])/2;
			//System.out.println("Kolizja dwóch prostok¹tów "+XPointer+" "+YPointer);
			boolean vertical = false;
			if(Math.abs(XPointer/l2)>=Math.abs(YPointer/l1))
				vertical = false;
			else
				vertical = true;
			//System.out.println("Kolizja dwóch prostok¹tów "+XPointer+" "+YPointer+" "+vertical);
			if(vertical)
			{
				if(YPointer>0)
				{
					return 1;
				}
				else
				{
					return 2;
				}
			}
			else
			{

				if(XPointer>0)
				{
					return 3;
				}
				else
				{
					return 4;
				}
			}
		}
		return -1;
	}
	
	public static float getCollisionSide(BBox box, int side)
	{
		float tab[] = box.getBBTab();
		if(side == 1)
		{
			return tab[0];
		} 
		else if(side == 2)
		{
			return tab[2];
		} 
		else if(side == 3)
		{
			return tab[1];
		} 
		else if(side == 4)
		{
			return tab[3];
		} 
		return 0;
	}
	
	
}

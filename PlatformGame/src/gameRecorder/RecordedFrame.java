package gameRecorder;

public class RecordedFrame 
{
	public float[] dataTab;
	public boolean[] keyTab;
	public int[][] itemTab;
	public float locX, locY, points, lifePoints;
	public boolean isOnLayer, canJump, gameEnd;
	
	public RecordedFrame(float[] dataTab, boolean[] keyTab, int[][] itemTab, int sizeX, int sizeY, float locX, float locY, float points, float lifePoints,
			boolean isOnLayer, boolean canJump, boolean gameEnd) {
		super();
		this.dataTab = new float[dataTab.length];
		for(int i = 0; i < dataTab.length; i++)
		{
			this.dataTab[i] = dataTab[i];
		}
		this.keyTab = new boolean[keyTab.length];
		for(int i = 0; i < keyTab.length; i++)
		{
			this.keyTab[i] = keyTab[i];
		}
		this.locX = locX;
		this.locY = locY;
		this.points = points;
		this.lifePoints = lifePoints;
		this.isOnLayer = isOnLayer;
		this.canJump = canJump;
		this.gameEnd = gameEnd;
		this.itemTab = new int[sizeX][sizeY];
		for(int i = 0; i < sizeX; i++)
		{
			for(int j = 0; j < sizeY; j++)
			{
				this.itemTab[i][j] = itemTab[i][j];
			}
		}
	}
	
}

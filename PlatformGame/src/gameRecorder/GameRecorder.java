package gameRecorder;

import java.util.ArrayList;

public class GameRecorder 
{
	private ArrayList<RecordedFrame> frameList;
	private int actualFrame = 0;
	
	public GameRecorder()
	{
		frameList = new ArrayList<RecordedFrame>();
	}
	
	public void addNewFrame(RecordedFrame frame)
	{
		frameList.add(frame);
	}
	
	public RecordedFrame getFrame(int index)
	{
		return frameList.get(index);
	}
	
	public int getFrameNum()
	{
		return frameList.size();
	}
	public void clearFrames()
	{
		frameList = new ArrayList<RecordedFrame>();
	}
	
	public void nextFrame()
	{
		actualFrame++;
		if(actualFrame == frameList.size())
			actualFrame = 0;
	}
	public RecordedFrame getActualFrame()
	{
		return frameList.get(actualFrame);
	}
}

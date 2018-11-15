package openSave;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import map.Map;

public class SaveMap 
{
	public static String name; 
	public static String path;
	public static void saveMap(Map map, Map items, int x, int y, int spawnX, int spawnY)
	{
		choosePath();
		File f = new File(path+".map");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return;
		}
		pw.println("MAP");
		pw.println(name);
		pw.println("PLAYERSPAWN");
		pw.println(spawnX+" "+spawnY);
		pw.println("MAPSIZE");
		pw.println(x+" "+y);
		pw.println("MAP");
		for(int i = 0; i < map.getSizeX(); i++)
		{
			for(int j = 0; j < map.getSizeY(); j++)
			{
				pw.print(map.getField(i, j)+" ");
			}
			pw.println();
		}
		pw.println("ITEMS");
		for(int i = 0; i < items.getSizeX(); i++)
		{
			for(int j = 0; j < items.getSizeY(); j++)
			{
				pw.print(items.getField(i, j)+" ");
			}
			pw.println();
		}
		pw.close();
	}
	
	
	private static void choosePath(){
		 JFrame parentFrame = new JFrame();
		 
		 JFileChooser chooser = new JFileChooser();
		 chooser.setDialogTitle("Specify a file to save");  
		 FileFilter filter = new FileNameExtensionFilter("AnimEngine animation files","map");
		 chooser.addChoosableFileFilter(filter);
		 chooser.setAcceptAllFileFilterUsed(true);
		 
		 int userSelection = chooser.showSaveDialog(parentFrame);
		  
		 if (userSelection == JFileChooser.APPROVE_OPTION) {
			 name = chooser.getSelectedFile().getName();
			 path = chooser.getSelectedFile().getAbsolutePath();
			 System.out.println(path);
		 }
		 
		 }
}

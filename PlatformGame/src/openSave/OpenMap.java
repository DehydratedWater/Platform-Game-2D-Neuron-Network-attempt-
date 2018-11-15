package openSave;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import map.Map;

public class OpenMap {
	public static String name; 
	public static String path;
	public static int[] openMap(Map map, Map items)
	{
		choosePath();
		File f = new File(path);
		Scanner s = null;

			try {
				s = new Scanner(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		s.nextLine();
		s.nextLine();
		s.nextLine();
		//System.out.println("OK");
		int[] spawn = new int[]{s.nextInt(), s.nextInt()};
		s.nextLine();
		s.nextLine();
		int[] sizeXY = new int[]{s.nextInt(), s.nextInt()};
		s.nextLine();
		map.setSizeX(sizeXY[0]);
		map.setSizeY(sizeXY[1]);
		items.setSizeX(sizeXY[0]);
		items.setSizeY(sizeXY[1]);
		s.nextLine();
		
		int mapTab[][] = new int[sizeXY[0]][sizeXY[1]];
		for(int i = 0; i < sizeXY[0]; i++)
		{
			for(int j = 0; j < sizeXY[1]; j++)
			{
				mapTab[i][j] = s.nextInt();
			}
			s.nextLine();
		}
		s.nextLine();
		int indexTab[][] = new int[sizeXY[0]][sizeXY[1]];
		for(int i = 0; i < sizeXY[0]; i++)
		{
			for(int j = 0; j < sizeXY[1]; j++)
			{
				indexTab[i][j] = s.nextInt();
			}
			s.nextLine();
		}
		s.close();
		map.setMapTab(mapTab);
		items.setMapTab(indexTab);
		
		return spawn;
	}
	public static int[] openMap(String mapPath, Map map, Map items)
	{
		File f = new File(mapPath);
		Scanner s = null;

			try {
				s = new Scanner(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		s.nextLine();
		s.nextLine();
		s.nextLine();
		//System.out.println("OK");
		int[] spawn = new int[]{s.nextInt(), s.nextInt()};
		s.nextLine();
		s.nextLine();
		int[] sizeXY = new int[]{s.nextInt(), s.nextInt()};
		s.nextLine();
		map.setSizeX(sizeXY[0]);
		map.setSizeY(sizeXY[1]);
		items.setSizeX(sizeXY[0]);
		items.setSizeY(sizeXY[1]);
		s.nextLine();
		
		int mapTab[][] = new int[sizeXY[0]][sizeXY[1]];
		for(int i = 0; i < sizeXY[0]; i++)
		{
			for(int j = 0; j < sizeXY[1]; j++)
			{
				mapTab[i][j] = s.nextInt();
			}
			s.nextLine();
		}
		s.nextLine();
		int indexTab[][] = new int[sizeXY[0]][sizeXY[1]];
		for(int i = 0; i < sizeXY[0]; i++)
		{
			for(int j = 0; j < sizeXY[1]; j++)
			{
				indexTab[i][j] = s.nextInt();
			}
			s.nextLine();
		}
		s.close();
		map.setMapTab(mapTab);
		items.setMapTab(indexTab);
		
		return spawn;
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

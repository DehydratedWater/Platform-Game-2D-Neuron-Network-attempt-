package map;

import java.awt.Color;

public interface Constants 
{
	public static final float BLOCK_SIZE = 64f;
	public static final float MOVE_MAP_SPEED = 10f;
	public static final float SCALE_MAP_SPEED = 0.1f;
	public static final float PLAYER_SPEED = 12f;
	public static final float PLAYER_SPEED_ON_LAYER = 6f;
	public static final float GRAVITY = 12f;
	public static final float JUMP_SPEED = 24f;
	public static final int[] BLOCK_VALUE_TAB = new int[]{0, 2, 3, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 6, 7, 8};
	public static final int[] ITEMS_VALUE_TAB = new int[]{0, 1};
	public static final Color[] COLOR_VALUES_BLOCKS = new Color[]{Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.GRAY, Color.darkGray, Color.lightGray, Color.red};
	public static final Color[] COLOR_VALUES_ITEMS = new Color[]{Color.BLUE, Color.YELLOW};
}

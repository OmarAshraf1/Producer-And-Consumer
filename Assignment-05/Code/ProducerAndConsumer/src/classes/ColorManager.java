package classes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class ColorManager {

	static Random rand = new Random();
	static ArrayList<Color> usedColors = new ArrayList<Color>();
	
	//Generate a new color for items/products. If it is already in the usedColors list or it is the default color of machines
	// or queues, generate a different color.
	public static Color generateNewItemColor() {
		
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, g, b);
		while(!isNewColor(randomColor))	{
			r = rand.nextFloat();
			g = rand.nextFloat();
			b = rand.nextFloat();
			randomColor = new Color(r, g, b);
		}
		
		return randomColor;
	}
	
	//Check if the random color generated is already in the usedColors list.
	public static boolean isNewColor(Color randomColor) {
		if (randomColor.equals(defaultMachineColor()) || randomColor.equals(defaultQueueColor()) ) {
			return false;
		}
		for (int i = 0; i < usedColors.size() ; i++) {
			if (usedColors.get(i).equals(randomColor)) {
				return false;
			}
		}
		return true;
	}
	
	//The default queue color (yellow)
	public static Color defaultQueueColor() {
		
		return new Color(255,255,0);
	}
	
	//The default machine color (teal)
	public static Color defaultMachineColor() {
		
		return new Color(0,128,128);
	}
}

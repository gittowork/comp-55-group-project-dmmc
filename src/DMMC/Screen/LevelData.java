package DMMC.Screen;

public abstract class LevelData {

	/*
	 * Entity IDs
	 * 
	 * Player: 0
	 * Sprout: 1
	 * CaliFr: 2
	 * CornMg: 3
	 * CornCn: 4
	 * Sword : 5
	 * 
	 * */

	static String[][] levelData = {
			{
				"14",				// map sizeX
				"10",				// map sizeY
				  "11111111111111"
				+ "10000000000001"
				+ "10000000000001"
				+ "10000111100001"
				+ "10000000000001"
				+ "11100000000111"
				+ "10000000000001"
				+ "10000111100001"
				+ "10000000000001"
				+ "11111111111111", // map layout
				"5",				// num Entities
				"0","2","2",		// add Entity [ID],[TilePosX],[TilePosY]
				"1","7","6",
				"1","6","2",
				"2","7","5",
				"3","7","6",

			},
			
			{
				"14",				
				"10",				
				  "11111111111111"
				+ "10000000000001"
				+ "10000001110001"
				+ "10000000000001"
				+ "11111100000111"
				+ "10000000000001"
				+ "11100001111111"
				+ "10000000000001"
				+ "11100000000111"
				+ "11111111111111", 
				"4",				
				"0","2","3",		
				"1","6","6",			
				"1","7","6",
				"1","6","3"
			},
			

			{
				"14",				
				"10",				
				  "11111111111111"
				+ "10000000000001"
				+ "11110001100001"
				+ "10000000000001"
				+ "11111100001111"
				+ "10000000000001"
				+ "11100000111111"
				+ "10000110000001"
				+ "10000000000111"
				+ "11111111111111", 
				"4",				
				"0","2","11",		
				"1","4","4",			
				"1","7","6",
				"1","7","2"
			}

			 
	};
	
	public static String[] getData(int levelId)
	{
		return levelData[levelId];
	}

}

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
				+ "11000000000011"
				+ "10000000000001"
				+ "11111000011111"
				+ "10000000000001"
				+ "10000111100001"
				+ "10000000000001"
				+ "11111000011111"
				+ "10000000000001"
				+ "11111111111111", // map layout
				"2",//wave 1		// num Entities
				"0","6","3",		// add Entity [ID],[TilePosX],[TilePosY]
				"1","7","8",
				"3",//wave 2
				"0", "6", "3",
				"1", "2", "6",
				"1", "11", "6",
				"4",//wave 3
				"0", "6", "3",
				"1", "2", "6",
				"1", "11", "6",
				"2", "6", "10",
				"6",//wave 4
				"0", "6", "3",
				"1", "2", "6",
				"1", "11", "6",
				"1", "2", "2",
				"1", "11", "2",
				"2", "6", "10",
				"7",//wave 5
				"0", "6", "3",
				"1", "2", "6",
				"1", "11", "6",
				"1", "2", "1",
				"1", "11", "1",
				"2", "6", "10",
				"3", "12", "2",
				"5"//total number of waves
			},
			
			{
				"14",				
				"10",				
				  "11111111111111"
				+ "11100000000111"
				+ "11000000000011"
				+ "10001111110001"
				+ "10000000000001"
				+ "11000000000011"
				+ "10001111110001"
				+ "10000000000001"
				+ "11100000000111"
				+ "11111111111111", 
				"2",//wave 1
				"0","6","8",		
				"1","6","3",
				"3",//wave 2
				"0","6","8",		
				"1","6","3",
				"1","6","6",
				"2"
/*				"4",//wave 3
				"0","6","8",		
				"1","6","3",
				"1","6","6",
				"2","6","1",
				"6",//wave 4
				"0","6","8",
				"1","1","4",
				"1","1","7",
				"1","12","4",
				"1","12","7",
				"2","6","1",
				"7",//wave 5
				"0","6","8",
				"1","1","4",
				"1","1","7",
				"1","12","4",
				"1","12","7",
				"2","6","1",
				"3","6","2",
				"5"//total number of waves
*/			},
			

			{
				"14",				
				"10",				
				  "00000000000000"
				+ "00000000000000"
				+ "00000111100000"
				+ "00000000000000"
				+ "01111000011110"
				+ "00000000000000"
				+ "00000111100000"
				+ "00000000000000"
				+ "00000000000000"
				+ "00000000000000", 
				"2",//wave 1				
				"0","6","5",		
				"1","6","1",
				"3",//wave 2
				"0","6","5",		
				"1","1","3",
				"1","11","3",
				"4",//wave 3
				"0","6","5",		
				"1","1","3",
				"1","11","3",
				"2","6","0",
				"6",//wave 4
				"0","6","5",		
				"1","1","3",
				"1","11","3",
				"1","6","1",
				"1","8","5",
				"2","6","0",
				"7",//wave 5
				"0","6","5",		
				"1","1","3",
				"1","11","3",
				"1","6","1",
				"1","8","5",
				"2","6","0",
				"3","6","1",
				"5"
			}

			 
	};
	
	public static String[] getData(int levelId)
	{
		return levelData[levelId];
	}

}

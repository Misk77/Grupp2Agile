import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GameMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	SaveLoad saveload;
	
	public GameMenu(){
		saveload = new SaveLoad();
	}
	@Override
	public String toString() {
		return "GameMenu [name=" + name + ", input=" + input + ", herotype=" + herotype + ", objectList="
				+ Arrays.toString(objectList) + ", fd=" + fd + ", game=" + game + ", GameMenuFirst()="
				 + ", playerName()=" + playerName() + ", Gamestart()="
				+ Arrays.toString(Gamestart()) + ", getName()=" + getName() + ", getInput()=" + getInput()
				+ ", getHerotype()=" + getHerotype() + ", getObjectList()=" + Arrays.toString(getObjectList())
				+ ", getFd()=" + getFd() + ", getGame()=" + getGame() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	static Scanner scanner = new Scanner(System.in);
	String name;
	String input;
	String herotype;
	Object[] objectList = new Object[4];
	 PlayMusic playmusic = new  PlayMusic();
	// System objects
	
	// Scanner scanner = new Scanner(System.in);
	static GameMenu gMenuMain = new GameMenu();

	// SaveLoad save = new SaveLoad();// Maybe shouldnt be here
	FileData fd = new FileData();
	Game game = new Game();
	
	public String playerName() {
		GuiConsole.io.println("\nWelcome player! \nPlease enter your name: ", Color.WHITE);
		GuiConsole.io.print(">> ");
		name = GuiConsole.io.nextLine();
		if (name.length() < 1) {
			GuiConsole.io.println("Something went wrong, please try again!", Color.RED);
			playerName();
		}
		return name;
	}
	// if player exist

	public void maping() {
		Map map = new Map();
		GuiConsole.io.println();
		GuiConsole.io.println("Choose map size:", Color.WHITE);
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("S", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("mall ", Color.orange);
		GuiConsole.io.print("(4x4)\n");
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("M", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("edium ", Color.orange);
		GuiConsole.io.print("(5x5)\n");
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("L", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("arge ", Color.orange);
		GuiConsole.io.print("(8x8)\n");
		
		GuiConsole.io.print(">> ");
		input = GuiConsole.io.nextLine();

		switch (input.toUpperCase()) {
		case "S":
			map.generateMap(4, 4);
			break;
		case "M":
			map.generateMap(5, 5);
			break;
		case "L":
			map.generateMap(8, 8);
			break;
		default:
			GuiConsole.io.println("Something went wrong, please try again!", Color.RED);
			maping();
		}
		objectList[0] = map;
	}

	public void cornerChoice() {
		GuiConsole.io.println();
		GuiConsole.io.println("Choose what corner of the map to start from:", Color.WHITE);
		// System.out.println("â•­â”„â”„â”„â”„â”„â”„â”„â”„â”„â•®\nâ”†1 2â”†\nâ”† â”†\nâ”† â”†\nâ”†3 4â”†\nâ•°â”„â”„â”„â”„â”„â”„â”„â”„â”„â•¯");
		GuiConsole.io.print("â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ\nâ–ˆ",Color.GRAY);
		GuiConsole.io.print(" A ", Color.orange);
		GuiConsole.io.print("â–ˆâ–ˆâ–ˆâ–ˆâ–ˆ",Color.GRAY);
		GuiConsole.io.print(" B ", Color.orange);
		GuiConsole.io.print("â–ˆ\nâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ\nâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ\nâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ\nâ–ˆ",Color.GRAY);
		GuiConsole.io.print(" C ", Color.orange);
		GuiConsole.io.print("â–ˆâ–ˆâ–ˆâ–ˆâ–ˆ",Color.GRAY);
		GuiConsole.io.print(" D ", Color.orange);
		GuiConsole.io.print("â–ˆ\nâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ\n",Color.GRAY);

		String corner = null;
		GuiConsole.io.print(">> ");
		input = GuiConsole.io.nextLine();

		switch (input.toUpperCase()) {
		case "A":
			corner = "NW";
			break;
		case "B":
			corner = "NE";
			break;
		case "C":
			corner = "SW";
			break;
		case "D":
			corner = "SE";
			break;
		default:
			GuiConsole.io.println("Something went wrong, please try again!", Color.RED);
			cornerChoice();
		}
		objectList[2] = corner;
	}

	public void cornerRandom() {
		Random rand = new Random();
		String[] corners = { "NW ", "NE ", "SW", "SE " };
		String corner = corners[rand.nextInt(corners.length)];
		objectList[2] = corner;
	}

	// AllseeingEyes EASTEREGG!!
	/*
	 * void allSeeingEye(Hero hero, Treasure tresure, Monster monster){
	 * System.out.println(
	 * "â•”â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•—\n");
	 * System.out.println(hero.name + "see a gliming " + tresure.treasuretype +
	 * "behind the " +monster.monstertype); System.out.println(
	 * "â•šâ•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�\n"); }
	 */
	
	
	// VÃ¤lj herotype for player
	public void heroChoice() {
		GuiConsole.io.println();
		GuiConsole.io.println("Choose your character:", Color.WHITE);
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("K", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("night\n", Color.orange);
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("R", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("ogue\n", Color.orange);
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("W", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("izard\n", Color.orange);

		
		GuiConsole.io.print(">> ");
		String input = GuiConsole.io.nextLine();

		switch (input.toUpperCase()) {
		case "K":
			input = "Knight";
			break;
		case "R":
			input = "Rogue";
			break;
		case "W":
			input = "Wizard";
			break;
		default:
			GuiConsole.io.println("Something went wrong, please try again!", Color.RED);
			heroChoice();
		}
		Hero hero = new Hero(input, name);
		objectList[1] = hero;
	}
	
	public void aiHeroChoice() {
		GuiConsole.io.println();
		GuiConsole.io.println("Choose AI character:", Color.WHITE);
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("K", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("night\n", Color.orange);
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("R", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("ogue\n", Color.orange);
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("W", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("izard\n", Color.orange);

		GuiConsole.io.print(">> ");
		String input = GuiConsole.io.nextLine();

		switch (input.toUpperCase()) {
		case "K":
			input = "Knight";
			break;
		case "R":
			input = "Rogue";
			break;
		case "W":
			input = "Wizard";
			break;
		default:
			GuiConsole.io.println("Something went wrong, please try again!", Color.RED);
			heroChoice();
		}
		Hero hero = new Hero(input, "AI " + input);
		hero.ai = true;
		objectList[1] = hero;
	}
	
	
	public void ReadChar() {
		GuiConsole.io.println(
				"|=======================================================================================================|",
				Color.RED);
		GuiConsole.io.println(
				"|=========================||----------| READ ABOUT THE CHARACTER |----------||==========================|",
				Color.GREEN);
		GuiConsole.io.println(
				"|=======================================================================================================|",
				Color.YELLOW);
		GuiConsole.io.println();

		GuiConsole.io.println("\n---------------------------Heros-----------------------", Color.MAGENTA);
		GuiConsole.io.println("Knight\n"
				+ "Initiative = 5\nHerotype = Herotype\nHealth = 9\nBaseattack = 6\nAvoidance = 4\n"
				+ "SpecialfÃ¶rmÃ¥ga: SkÃ¶ldblock. Riddaren blockerar alltid fÃ¶rsta attackenper strid med sin skÃ¶ld \noch behÃ¶ver dÃ¤rfÃ¶r varken undvika eller ta nÃ¥gon skada\n",
				Color.BLUE);

		GuiConsole.io.println("Wizard\n"
				+ "Initiative = 6\nHerotype = Herotype\nHealth = 4\nBaseattack = 9\nAvoidance = 5\n"
				+ "SpecialfÃ¶rmÃ¥ga: Ljussken. Trollkarlen kan gÃ¶ra monster blinda och hardÃ¤rfÃ¶r alltid 80% chans att fly frÃ¥n strider\n",
				Color.WHITE);

		GuiConsole.io.println("Rogue\n"
				+ "Initiative = 7\nHerotype = Herotype\nHealth = 5\nBaseattack = 5\nAvoidance = 7\n"
				+ "SpecialfÃ¶rmÃ¥ga: Kritisk trÃ¤ff. Tjuven har 25% chans att gÃ¶ra dubbel skada varje gÃ¥ng tjuven attackerar\n",
				Color.ORANGE);
		GuiConsole.io.println("--------------------------Monster----------------------");
		GuiConsole.io.println(
				"Giant Spider\nInitiative = 7\nMonstertype = Monster\nHealth = 1\nBaseattack = 2\nAvoidance = 3\n",
				Color.RED);
		GuiConsole.io.println(
				"Skeleton\nInitiative = 4\nMonstertype = Monster\nHealth = 2\nBaseattack = 3\nAvoidance = 3\n",
				Color.YELLOW);
		GuiConsole.io.println("Orc\nInitiative = 6\nMonstertype = Monster\nHealth = 3\nBaseattack = 4\nAvoidance = 4\n",
				Color.RED);
		GuiConsole.io.println(
				"Troll\nInitiative = 2\nMonstertype = Monster\nHealth = 4\nBaseattack = 7\nAvoidance = 2\n",
				Color.CYAN);
		GuiConsole.io.println("--------------------------Treasure----------------------");
		GuiConsole.io.println("Loose coins\nTreasuretype: Treasuretype\nValue: 2\n"
				+ "\nSmall bag of coins\nTreasuretype: Treasuretype\nValue: 6\n"
				+ "\nGold jewellry\nTreasuretype: Treasuretype\nValue: 10\n"
				+ "\nPrecious stone\nTreasuretype: Treasuretype\nValue: 14\n"
				+ "\nSmall treasure chest\nTreasuretype: Treasuretype\nValue: 20\n");
		Gamestart();
	}

	public void iGame() {
		/* Instruction about the game. */
		GuiConsole.io.println();

		GuiConsole.io.println(
				"|=======================================================================================================|",
				Color.RED);
		GuiConsole.io.println(
				"|=========================||----------| INSTRUCTIONS FOR THE GAME |----------||=========================|",
				Color.GREEN);
		GuiConsole.io.println(
				"|=======================================================================================================|",
				Color.YELLOW);

		GuiConsole.io.println();
		GuiConsole.io.println("\n" + "1. You need to pick a character.\r\n"
				+ "2. Choose the map size: Small(4, 4) - Medium(5, 5) - Large(8, 8).\r\n"
				+ "3. You will battle monsters.\r\n" + "4. Pick up Treasures!\r\n"
				+ "5. Game Over when the player leaves the map or gets defeated.\r\n"
				+ "6. The game command movement is: North, South, East, West. \r\n" + "", Color.BLUE);
	}

//Games start here, then NEW GAME the follow the methods one by one , into  Game class and the game is set to go running
	public Object[] GameMenuFirst() throws ClassNotFoundException {
		playmusic.disposeSound();
		String backgroundmusic = "/ExternalItems/Hypnotic-Puzzle3";
		playmusic.playBackGround(backgroundmusic);
		playmusic.disposeSound();
		
		GuiConsole.io.println(
				"|=======================================================================================================|",
				Color.RED);
		GuiConsole.io.println(
				"|================================||----------| DUNGEON RUN |----------||================================|",
				Color.GREEN);
		GuiConsole.io.println(
				"|=======================================================================================================|",
				Color.YELLOW);
		GuiConsole.io.println();
		GuiConsole.io.println(
				"        ================================ Load or start a new game ================================",
				Color.WHITE);
		GuiConsole.io.println();
		GuiConsole.io.print("[");
		GuiConsole.io.print("N", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.println("ew game", Color.orange);
		GuiConsole.io.print("[");
		GuiConsole.io.print("L", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.println("oad game\n", Color.orange);
		// System.out.println("[A]i Character \n");
		GuiConsole.io.print("[");
		GuiConsole.io.print("E", Color.red);
		GuiConsole.io.print("]");
		GuiConsole.io.println("xit\n", Color.red);
		// System.out.println("[M]eny\n");
		GuiConsole.io.print(">> ");
		input = GuiConsole.io.nextLine();

	
		if (input.equalsIgnoreCase("N")) {
			GuiConsole.io.println("Let the Adventure Begin...", Color.GREEN);
			objectList = Gamestart();

			
//		} else if (input.equalsIgnoreCase("A")) {
//			AiHeroChoice();
//			maping();
//			AiHero.cornerRandom();
		} else if (input.equalsIgnoreCase("E")) {
			GuiConsole.io.println("Too bad you're leaving....", Color.ORANGE);
			GuiConsole.io.println("Come back when you dare to enter the dungeons.....", Color.BLUE);
			System.exit(0);

		} else if (input.equalsIgnoreCase("L")) {
			 try {
				 FileInputStream fileIn = new FileInputStream("Dungensave.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         in.close();
		         fileIn.close();
		      } catch (IOException i) {
		         i.printStackTrace();
		         return objectList ;
		      }
		      
			 GuiConsole.io.println("Deserialized Dungensave...", Color.YELLOW);
			 GuiConsole.io.println("Name: " +getName(), Color.YELLOW);
			 GuiConsole.io.println("getHerotype: " + getHerotype(), Color.YELLOW);
			
		   
			// save.LoadFromDisk(0, 0, 0, 0, herotype, herotype, 0, 0, 0);
			GuiConsole.io.println("Denna metod gÃ¶rs senare.. GuiConsole.io.println(\"[L]oad-DENNA METOD GÃ–RS SENARE -TEST NU MED SERI LOAD your character..\", Color.YELLOW);LOAD CHARACTER. IFPLAYEREXIST METHOD", Color.YELLOW);
			GameMenuFirst();

		} else {
			GuiConsole.io.println("No such option in menu", Color.RED);
			GuiConsole.io.println("\t try again........", Color.RED);
			GameMenuFirst();
		}
		return objectList;
	}

	public Object[] Gamestart() {
	
		// boolean running = true;
		// GAME: // This can be uses as at startpoint, then ever we wanna get back here,
		// have GAME; like a break but put us here instead

		// while (running) {
		GuiConsole.io.println();
		GuiConsole.io.println();
		GuiConsole.io.println(
				"|=======================================================================================================|",
				Color.RED);
		GuiConsole.io.println(
				"|=================================||----------| GAME MENU |----------||=================================|",
				Color.GREEN);
		GuiConsole.io.println(
				"|=======================================================================================================|",
				Color.YELLOW);
		GuiConsole.io.println();
																								// stÃ¥r om spelet, spelrunda
		GuiConsole.io.println("[N]ew-Create new character", Color.RED); // tar in hero metoden
		GuiConsole.io.println("[L]oad - Load your character"); // lÃ¤ser frÃ¥n load metoden och tar in befiltlig spelare
		GuiConsole.io.println("[A]i character", Color.GRAY); //
		//GuiConsole.io.println("[S]ave - Save your character"); // lÃ¤ser frÃ¥n save metoden och sparar befiltlig spelare
		GuiConsole.io.println("[H]ighscore - See highscore (treasure points) for character", Color.GREEN); //
		GuiConsole.io.println("[R]ead - Read about the characters", Color.ORANGE);// om spelkaraktÃ¤rer
		GuiConsole.io.println("[I]nstructions-How to play the game", Color.YELLOW); // Readfile eller metod dÃ¤r allt
		GuiConsole.io.println("[E]XIT/SAVE\n", Color.WHITE);// THen exit automatic save the game
		GuiConsole.io.print(">> ");
		// input = scanner.next();
		input = GuiConsole.io.nextLine();
		// Valen i menu

		switch (input.toUpperCase()) {

		case "I":
			GuiConsole.io.println();
			iGame();
			break;
		case "N":
			playerName();
			heroChoice();
			maping();
			cornerChoice();
			// save.saveToDisk(hero.deadgiantspiders, hero.deadorcs, hero.deadskeletons,
			// hero.deadtrolls, hero.herotype,
			// hero.name, hero.treasure, hero.visitedrooms, hero.adventures);
			break;
		case "A":
			aiHeroChoice();
			maping();
			Game.dramaticPause = 80;
			//Map.clearScreenWhenEnteringRoom = true;
			cornerRandom();
			break;
		case "L":
				//Daniels loading
				//should probably print a list of names here
				GuiConsole.io.print("Which ");
				GuiConsole.io.print("character", Color.orange);
				GuiConsole.io.println(" do you want to load?");
				GuiConsole.io.print(">> ");
				String name = GuiConsole.io.nextLine();
				String [] heroinfo = saveload.load(name);
				if(heroinfo.length<2) {
					//hero doesnt exist
					//or savefile is fucked up
				}
				else {
					Hero hero = new Hero(heroinfo[0], heroinfo[1]);
					hero.treasure = Integer.parseInt(heroinfo[2]);
					hero.deadgiantspiders = Integer.parseInt(heroinfo[3]);
					hero.deadskeletons = Integer.parseInt(heroinfo[4]);
					hero.deadorcs = Integer.parseInt(heroinfo[5]);
					hero.deadtrolls = Integer.parseInt(heroinfo[6]);
					objectList[1] = hero;
					maping();
					cornerChoice();
				}
				//Daniels loading
			 /*try {
				 FileInputStream fileIn = new FileInputStream("Dungensave.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         in.close();
		         fileIn.close();
		      } catch (IOException i) {
		         i.printStackTrace();
		         return objectList ;
		      }
		      
			 GuiConsole.io.println("Deserialized Dungensave...", Color.YELLOW);
			 GuiConsole.io.println("Name: " +getName(), Color.YELLOW);
			 GuiConsole.io.println("getHerotype: " + getHerotype(), Color.YELLOW);
			
			GuiConsole.io.println("[L]oad-DENNA METOD GÃ–RS SENARE -TEST NU MED SERI LOAD your character..", Color.YELLOW);
			Gamestart();
			*/
			break;
		case "S":
			// endMenu(Hero hero);
			// Alternativ...1. read from file method in saveLoad 2. gÃ¶ra metod med allt
			GuiConsole.io.println("[S]ave-DENNA METOD GÃ–RS SENARE(TEST) - SAVE your character..", Color.YELLOW);
			// SaveLoad.saveToDisk();
			Gamestart();
			break;
		case "H":
			GuiConsole.io.println(
					"[H]ighscore-DENNA METOD GÃ–RS SENARE - See highscore (treasure points) for character..", Color.RED);
		//	endMenu(null); // MÃ¥ste ha parameter
			// Alternativ...1. read from file method in saveLoad 2. gÃ¶ra metod med allt
			Gamestart();
			break;
		case "R":
			GuiConsole.io.println();

			ReadChar();
			GuiConsole.io.println();
			break;
		case "E":
			
			GuiConsole.io.println("You now exit the game....", Color.YELLOW);
			
			GuiConsole.io.println("Data will be automatic saved.....", Color.YELLOW);
			System.exit(0);
			GuiConsole.io.println();
		default:
			
			GuiConsole.io.println("No such option in menu", Color.RED);
			GuiConsole.io.println("\t try again........", Color.YELLOW);
			Gamestart();
			break;

		}

		return objectList;
	}// END GameMenu

	public String endMenu(Hero hero) {
		playmusic.disposeSound();
		saveload.save(hero);
		//SaveLoad data=new  SaveLoad(name);
		/*  Denna verkar spara
		try {
			data.save(hero, "Dungensave.ser");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//SaveLoad data=new  SaveLoad(name);
		//test imorse
		 try {
	         FileOutputStream fileOut =
	         new FileOutputStream("Dungeonsave.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(hero);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in Dungeonsave.ser");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
		// save goes here
		/*
		 * Detta funkar inte.. else klagar.... PlayMusic playmusic = new PlayMusic();
		 * String gameover; playmusic.playBackGround(gameover);
		 */
			if(hero.dead) {
				PlayMusic playmusic = new  PlayMusic();
		          
				String gameover ="/ExternalItems/gameover";
				playmusic.playBackGround(gameover);
				GuiConsole.io.print("Your");
				GuiConsole.io.print(" adventures ",Color.white);
				GuiConsole.io.print("are over, these are your ");
				GuiConsole.io.print("accomplishments", Color.white);
				GuiConsole.io.println(":");
			}
			else {
				GuiConsole.io.print("You managed to get out of the dungeon");
				GuiConsole.io.print(" alive ", Color.green);
				GuiConsole.io.print(", these are your ");
				GuiConsole.io.print("accomplishments", Color.white);
				GuiConsole.io.println(":");
			}
			GuiConsole.io.print("Treasure", Color.orange);
			GuiConsole.io.print(" worth ");
			GuiConsole.io.print(hero.treasure, Color.white);
			GuiConsole.io.println(" coins collected");
			
			GuiConsole.io.print(hero.visitedrooms, Color.white);
			GuiConsole.io.print(" rooms ", Color.white);
			GuiConsole.io.println("visited");
			
			GuiConsole.io.print(hero.deadgiantspiders, Color.white);
			GuiConsole.io.print(" Giant spiders ", Color.orange.darker());
			GuiConsole.io.println("slain", Color.white);
			
			GuiConsole.io.print(hero.deadskeletons, Color.white);
			GuiConsole.io.print(" Skeletons ", Color.orange.darker());
			GuiConsole.io.println("slain", Color.white);

			GuiConsole.io.print(hero.deadorcs, Color.white);
			GuiConsole.io.print(" Orcs ", Color.orange.darker());
			GuiConsole.io.println("slain", Color.white);
		
			GuiConsole.io.print(hero.deadtrolls, Color.white);
			GuiConsole.io.print(" Trolls ", Color.orange.darker());
			GuiConsole.io.println("slain", Color.white);
			
			GuiConsole.io.print((hero.deadgiantspiders+hero.deadskeletons+hero.deadorcs+hero.deadtrolls), Color.white);
			GuiConsole.io.print(" monsters ", Color.orange.darker());
			GuiConsole.io.print("slain", Color.white);
			GuiConsole.io.println(" in total");
			
			GuiConsole.io.print(hero.adventures, Color.white);
			GuiConsole.io.print(" adventures ", Color.green);
			GuiConsole.io.println("undertaken\n");
			
			GuiConsole.io.print("[");
			GuiConsole.io.print("M", Color.white);
			GuiConsole.io.print("]");
			GuiConsole.io.println("ain menu", Color.white);
			
			GuiConsole.io.print("[");
			GuiConsole.io.print("Q", Color.red);
			GuiConsole.io.print("]");
			GuiConsole.io.println("uit game", Color.red);
			while(true) {
				String option = GuiConsole.io.nextLine().toLowerCase();
				if(option.equals("m")) {
					return "menu";
				}
				else if(option.equals("q")) {
					System.exit(0);
				}
				else {
					continue;
				}
			}
		}
	// play a music

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Scanner getScanner() {
		return scanner;
	}

	public static void setScanner(Scanner scanner) {
		GameMenu.scanner = scanner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getHerotype() {
		return herotype;
	}

	public void setHerotype(String herotype) {
		this.herotype = herotype;
	}

	public Object[] getObjectList() {
		return objectList;
	}

	public void setObjectList(Object[] objectList) {
		this.objectList = objectList;
	}

	public static GameMenu getgMenuMain() {
		return gMenuMain;
	}

	public static void setgMenuMain(GameMenu gMenuMain) {
		GameMenu.gMenuMain = gMenuMain;
	}

	public FileData getFd() {
		return fd;
	}

	public void setFd(FileData fd) {
		this.fd = fd;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
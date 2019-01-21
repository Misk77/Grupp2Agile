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

	// System objects
	
	// Scanner scanner = new Scanner(System.in);
	static GameMenu gMenuMain = new GameMenu();

	// String[] corners = { "NW ", "NE ", "SW", "SE " };
	// AiHero ahero = new AiHero(herotype);
	// SaveLoad save = new SaveLoad();// Maybe shouldnt be here
	FileData fd = new FileData();
	Game game = new Game();

	public void maping() {
		Map map = new Map();
		GuiConsole.io.println();
		GuiConsole.io.println("Choose map size:", Color.WHITE);
		GuiConsole.io.println("Press 1 for map: 4x4", Color.BLUE);
		GuiConsole.io.println("Press 2 for map: 5x5", Color.RED);
		GuiConsole.io.println("Press 3 for map: 8x8", Color.YELLOW);
		GuiConsole.io.println();
		int operator = GuiConsole.io.nextInt();
		switch (operator) {
		case 1:
			map.generateMap(4, 4);
			break;
		case 2:
			map.generateMap(5, 5);
			break;
		case 3:
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
		GuiConsole.io.println("Choose a corner to start in:", Color.WHITE);
		// System.out.println("╭┄┄┄┄┄┄┄┄┄╮\n┆1 2┆\n┆ ┆\n┆ ┆\n┆3 4┆\n╰┄┄┄┄┄┄┄┄┄╯");
		GuiConsole.io.println(
				" ___________ \n|           |\n| 1       2 |\n|           |\n|           |\n| 3       4 |\n|___________|",
				Color.CYAN);
		GuiConsole.io.println();
		String corner = null;
		int operator = GuiConsole.io.nextInt();
		switch (operator) {
		case 1:
			corner = "NW";
			break;
		case 2:
			corner = "NE";
			break;
		case 3:
			corner = "SW";
			break;
		case 4:
			corner = "SE";
			break;
		default:
			GuiConsole.io.println("Something went wrong, please try again!", Color.BLUE);
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

	// AllseeingEyes
	/*
	 * void allSeeingEye(Hero hero, Treasure tresure, Monster monster){
	 * System.out.println(
	 * "╔══════════════════════════════════════════════════════════════════╗\n");
	 * System.out.println(hero.name + "see a gliming " + tresure.treasuretype +
	 * "behind the " +monster.monstertype); System.out.println(
	 * "╚══════════════════════════════════════════════════════════════════╝\n"); }
	 */
	// Välj herotype for player
	public void HeroChoice() {
		GuiConsole.io.println("Choose your character:\n", Color.WHITE);
		GuiConsole.io.println("Press 1 for Knight", Color.RED);
		GuiConsole.io.println("Press 2 for Rogue", Color.GREEN);
		GuiConsole.io.println("Press 3 for Wizard", Color.YELLOW);
		GuiConsole.io.println();
		int operator = GuiConsole.io.nextInt();
		String type = "";
		switch (operator) {
		case 1:
			type = "Knight";
			break;
		case 2:
			type = "Rogue";
			break;
		case 3:
			type = "Wizard";
			break;
		case 4:
			AiHeroChoice();
			break;

		default:
			GuiConsole.io.println("Something went wrong, please try again!", Color.GREEN);
			HeroChoice();
		}
		Hero hero = new Hero(type, name);

		objectList[1] = hero;

	}

	public void AiHeroChoice() {// Denna ska bort? Johannes har en bättre
		GuiConsole.io.println("Press 1 for AI Knight\nPress 2 for AI Rogue\nPress 3 for AI Wizard", Color.GREEN);

		int operator = GuiConsole.io.nextInt();
		String type = "";

		switch (operator) {
		case 1:
			type = "Knight";
			break;
		case 2:
			type = "Rogue";
			break;
		case 3:
			type = "Wizard";
			break;

		default:
			GuiConsole.io.println("Something went wrong, please try again!", Color.RED);
			AiHeroChoice();
		}
		Hero hero = new Hero(type, "AI" + type);
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
				+ "Specialförmåga: Sköldblock. Riddaren blockerar alltid första attackenper strid med sin sköld \noch behöver därför varken undvika eller ta någon skada\n",
				Color.BLUE);

		GuiConsole.io.println("Wizard\n"
				+ "Initiative = 6\nHerotype = Herotype\nHealth = 4\nBaseattack = 9\nAvoidance = 5\n"
				+ "Specialförmåga: Ljussken. Trollkarlen kan göra monster blinda och hardärför alltid 80% chans att fly från strider\n",
				Color.WHITE);

		GuiConsole.io.println("Rogue\n"
				+ "Initiative = 7\nHerotype = Herotype\nHealth = 5\nBaseattack = 5\nAvoidance = 7\n"
				+ "Specialförmåga: Kritisk träff. Tjuven har 25% chans att göra dubbel skada varje gång tjuven attackerar\n",
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
		 PlayMusic playmusic = new  PlayMusic();
         
		 String backgroundmusic = "/Hypnotic-Puzzle3";
		playmusic.playBackGround(backgroundmusic);
		
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
		GuiConsole.io.println("[L]OAD GAME \n", Color.MAGENTA);
		GuiConsole.io.println("[N]EW GAME \n", Color.BLUE);
		// System.out.println("[A]i Character \n");
		GuiConsole.io.println("[E]XIT\n", Color.RED);
		GuiConsole.io.println();
		// System.out.println("[M]eny\n");

		input = GuiConsole.io.nextLine();

		try {
			Thread.sleep(300);
		} catch (InterruptedException e2) {
			GuiConsole.io.println("Badness...", Color.RED);
		}

		if (input.equalsIgnoreCase("N")) {
			GuiConsole.io.println("Let the Adventures Begin...", Color.GREEN);
			objectList = Gamestart();

			try {
				Thread.sleep(300);
			} catch (InterruptedException e2) {
				GuiConsole.io.println("Badness", Color.RED);
			}

			try {
				Thread.sleep(300);
			} catch (InterruptedException e2) {
				GuiConsole.io.println("Badness", Color.RED);
			}
		} else if (input.equalsIgnoreCase("A")) {
			AiHeroChoice();
			maping();
			AiHero.cornerRandom();
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
			GuiConsole.io.println("Denna metod görs senare.. GuiConsole.io.println(\"[L]oad-DENNA METOD GÖRS SENARE -TEST NU MED SERI LOAD your character..\", Color.YELLOW);LOAD CHARACTER. IFPLAYEREXIST METHOD", Color.YELLOW);
			GameMenuFirst();

		} else {
			GuiConsole.io.println("No such option in menu", Color.RED);
			GuiConsole.io.println("\t try again........", Color.RED);
			GameMenuFirst();
		}
		return objectList;
	}

	

	public String playerName() {
		GuiConsole.io.println("Welcome player! \nPlease enter your name: ", Color.GREEN);
		name = GuiConsole.io.nextLine();
		return name;

	}
	// if player exist

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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		GuiConsole.io.println("[I]nstructions-How to play the game...", Color.YELLOW); // Readfile eller metod där allt
																						// står om spelet, spelrunda
		GuiConsole.io.println("[N]ew-Create new character..", Color.RED); // tar in hero metoden
		GuiConsole.io.println("[A]i character ..", Color.GRAY); //
		GuiConsole.io.println("[L]oad - Load your character.."); // läser från load metoden och tar in befiltlig spelare
		GuiConsole.io.println("[S]ave - Save your character.."); // läser från save metoden och sparar befiltlig spelare
		GuiConsole.io.println("[H]ighscore - See highscore (treasure points) for character..", Color.GREEN); //
		GuiConsole.io.println("[R]ead - Read about the characters..", Color.ORANGE);// om spelkaraktärer
		GuiConsole.io.println("[E]XIT/SAVE\n", Color.WHITE);// THen exit automatic save the game
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
			HeroChoice();
			maping();
			cornerChoice();
			// save.saveToDisk(hero.deadgiantspiders, hero.deadorcs, hero.deadskeletons,
			// hero.deadtrolls, hero.herotype,
			// hero.name, hero.treasure, hero.visitedrooms, hero.adventures);
			break;
		case "A":
			AiHeroChoice();
			maping();
			Game.dramaticPause = 80;
			Map.clearScreenWhenEnteringRoom = true;
			cornerRandom();
			break;
		case "L":
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
			
			GuiConsole.io.println("[L]oad-DENNA METOD GÖRS SENARE -TEST NU MED SERI LOAD your character..", Color.YELLOW);
			Gamestart();
			break;
		case "S":
			// endMenu(Hero hero);
			// Alternativ...1. read from file method in saveLoad 2. göra metod med allt
			GuiConsole.io.println("[S]ave-DENNA METOD GÖRS SENARE(TEST) - SAVE your character..", Color.YELLOW);
			// SaveLoad.saveToDisk();
			Gamestart();
			break;
		case "H":
			GuiConsole.io.println(
					"[H]ighscore-DENNA METOD GÖRS SENARE - See highscore (treasure points) for character..", Color.RED);
		//	endMenu(null); // Måste ha parameter
			// Alternativ...1. read from file method in saveLoad 2. göra metod med allt
			Gamestart();
			break;
		case "R":
			GuiConsole.io.println();

			ReadChar();
			GuiConsole.io.println();
			break;
		case "E":
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			GuiConsole.io.println("You now exit the game....", Color.YELLOW);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			GuiConsole.io.println("Data will be automatic saved.....", Color.YELLOW);
			System.exit(0);
			GuiConsole.io.println();
		default:
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			GuiConsole.io.println("No such option in menu", Color.RED);
			GuiConsole.io.println("\t try again........", Color.YELLOW);
			Gamestart();
			break;

		}

		return objectList;
	}// END GameMenu

	public String endMenu(Hero hero) {
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
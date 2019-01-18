
import java.awt.Color;
import java.util.Scanner;

public class GameMenu {
	
	static Scanner scanner = new Scanner(System.in);
	String name;
	String input;
	String herotype;
	Object[] objectList = new Object[4];

	// System objects

	// Scanner scanner = new Scanner(System.in);
	static GameMenu gMenuMain = new GameMenu();
	
	//String[] corners = { "NW ", "NE ", "SW", "SE " };
	//AiHero ahero = new AiHero(herotype);
	SaveLoad<?> save = new SaveLoad<Object>();// Maybe shouldnt be here
	FileData fd = new FileData();
	Game game = new Game();

	public void maping() {
		Map map = new Map();
		GuiConsole.io.println();
		GuiConsole.io.println("Choose map size:",Color.MAGENTA);
		GuiConsole.io.println("Press 1 for map: 4x4",Color.BLUE);
		GuiConsole.io.println("Press 2 for map: 5x5",Color.RED);
		GuiConsole.io.println("Press 3 for map: 8x8",Color.WHITE);
		int operator = GuiConsole. io.nextInt();
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
			GuiConsole.io.println("Something went wrong, please try again!",Color.RED);
			maping();
		}
		objectList[0] = map;
	}

	public void cornerChoice() {
		GuiConsole.io.println();
		GuiConsole.io.println("Choose a corner to start in:",Color.GREEN);
		// System.out.println("╭┄┄┄┄┄┄┄┄┄╮\n┆1 2┆\n┆ ┆\n┆ ┆\n┆3 4┆\n╰┄┄┄┄┄┄┄┄┄╯");
		GuiConsole.io.println(
				" ___________ \n|           |\n| 1       2 |\n|           |\n|           |\n| 3       4 |\n|___________|",Color.CYAN);
		String corner = null;
		int operator = GuiConsole. io.nextInt();
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
			GuiConsole.io.println("Something went wrong, please try again!",Color.BLUE);
			cornerChoice();
		}
		objectList[2] = corner;
	}  

	// Välj herotype for player
	public void HeroChoice() {
		GuiConsole.io.println("Choose your character:\n",Color.GREEN);
		GuiConsole.io.println("Press 1 for Knight",Color.RED);
		GuiConsole.io.println("Press 2 for Rogue",Color.GREEN);
		GuiConsole.io.println("Press 3 for Wizard",Color.YELLOW);

		int operator = GuiConsole. io.nextInt();
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
			GuiConsole.io.println("Something went wrong, please try again!",Color.GREEN);
			HeroChoice();
		}
		Hero hero = new Hero(type, name);

		objectList[1] = hero;

	}

	public void AiHeroChoice() {
		GuiConsole.io.println("Press 1 for \"AI Knight\nPress 2 for \"AI Rogue\nPress 3 for AI Wizard",Color.GREEN);

		int operator = GuiConsole. io.nextInt();
		String type = "";

		switch (operator) {
		case 1:
			type = "AI Knight";
			break;
		case 2:
			type = "AI Rogue";
			break;
		case 3:
			type = "AI Wizard";
			break;

		default:
			GuiConsole.io.println("Something went wrong, please try again!",Color.RED);
			AiHeroChoice();
		}
		AiHero aihero = new AiHero(type);

		objectList[1] = aihero;

	}

	public void ReadChar() {
		GuiConsole.io.println("|=======================================================================================================|",Color.RED);
		GuiConsole.io.println("|=========================||----------| READ ABOUT THE CHARACTER |----------||==========================|",Color.GREEN);
		GuiConsole.io.println("|=======================================================================================================|",Color.YELLOW);
		GuiConsole.io.println();
		
		GuiConsole.io.println("\n---------------------------Heros-----------------------",Color.MAGENTA);
		GuiConsole.io.println("Knight\n"
				+ "Initiative = 5\nHerotype = Herotype\nHealth = 9\nBaseattack = 6\nAvoidance = 4\n"
				+ "Specialförmåga: Sköldblock. Riddaren blockerar alltid första attackenper strid med sin sköld \noch behöver därför varken undvika eller ta någon skada\n",Color.BLUE);

		GuiConsole.io.println("Wizard\n"
				+ "Initiative = 6\nHerotype = Herotype\nHealth = 4\nBaseattack = 9\nAvoidance = 5\n"
				+ "Specialförmåga: Ljussken. Trollkarlen kan göra monster blinda och hardärför alltid 80% chans att fly från strider\n",Color.WHITE);

		GuiConsole.io.println("Rogue\n"
				+ "Initiative = 7\nHerotype = Herotype\nHealth = 5\nBaseattack = 5\nAvoidance = 7\n"
				+ "Specialförmåga: Kritisk träff. Tjuven har 25% chans att göra dubbel skada varje gång tjuven attackerar\n",Color.ORANGE);
		GuiConsole.io.println("--------------------------Monster----------------------");
		GuiConsole.io.println(
				"Giant Spider\nInitiative = 7\nMonstertype = Monster\nHealth = 1\nBaseattack = 2\nAvoidance = 3\n",Color.RED);
		GuiConsole.io.println(
				"Skeleton\nInitiative = 4\nMonstertype = Monster\nHealth = 2\nBaseattack = 3\nAvoidance = 3\n",Color.YELLOW);
		GuiConsole.io.println("Orc\nInitiative = 6\nMonstertype = Monster\nHealth = 3\nBaseattack = 4\nAvoidance = 4\n",Color.RED);
		GuiConsole.io.println("Troll\nInitiative = 2\nMonstertype = Monster\nHealth = 4\nBaseattack = 7\nAvoidance = 2\n",Color.CYAN);
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
		
		
		GuiConsole.io.println("|=======================================================================================================|",Color.RED);
		GuiConsole.io.println("|=========================||----------| INSTRUCTIONS FOR THE GAME |----------||=========================|",Color.GREEN);
		GuiConsole.io.println("|=======================================================================================================|",Color.YELLOW);
		
		GuiConsole.io.println();
		GuiConsole.io.println("\n" + "1. You need to pick a character.\r\n"
				+ "2. Choose the map size: Small(4, 4) - Medium(5, 5) - Large(8, 8).\r\n"
				+ "3. You will battle monsters.\r\n" + "4. Pick up Treasures!\r\n"
				+ "5. Game Over when the player leaves the map or gets defeated.\r\n"
				+ "6. The game command movement is: North, South, East, West. \r\n" + "",Color.BLUE);
	}

//Games start here, then NEW GAME the follow the methods one by one tha nfinally into  Game class and the game is set to go running
	public Object[] GameMenuFirst() {
		GuiConsole.io.println();
		GuiConsole.io.println("|=======================================================================================================|",Color.RED);
		GuiConsole.io.println("|================================||----------| DUNGEON RUN |----------||================================|",Color.GREEN);
		GuiConsole.io.println("|=======================================================================================================|",Color.YELLOW);
		GuiConsole.io.println();
		GuiConsole.io.println("        ================================ Load or start a new game ================================",Color.WHITE);
		GuiConsole.io.println();
		GuiConsole.io.println("[L]OAD GAME \n",Color.MAGENTA);
		GuiConsole.io.println("[N]EW GAME \n",Color.BLUE);
		//System.out.println("[A]i Character \n");
		GuiConsole.io.println("[E]XIT\n",Color.RED);
		GuiConsole.io.println();
		// System.out.println("[M]eny\n");

		input =GuiConsole.io.nextLine();

		try {
			Thread.sleep(300);
		} catch (InterruptedException e2) {
			GuiConsole.io.println("Badness...",Color.RED);
		}

		if (input.equalsIgnoreCase("N")) {
			GuiConsole.io.println("Let the Adventures Begin...",Color.GREEN);
			objectList = Gamestart();

			try {
				Thread.sleep(300);
			} catch (InterruptedException e2) {
				GuiConsole.io.println("Badness",Color.RED);
			}

			try {
				Thread.sleep(300);
			} catch (InterruptedException e2) {
				GuiConsole.io.println("Badness",Color.RED);
			}
		} else if (input.equalsIgnoreCase("A")) {
			AiHeroChoice();
			maping();
			AiHero.cornerRandom();
		} else if (input.equalsIgnoreCase("E")) {
			GuiConsole.io.println("Too bad you're leaving....",Color.ORANGE);
			GuiConsole.io.println("Come back when you dare to enter the dungeons.....",Color.BLUE);
			System.exit(0);

		} else if (input.equalsIgnoreCase("L")) {
			save.LoadFromDisk();
			GuiConsole.io.println("Denna metod görs senare.. LOAD CHARACTER. IFPLAYEREXIST METHOD",Color.YELLOW);
			GameMenuFirst();

		} else {
			GuiConsole.io.println("No such option in menu",Color.RED);
			GuiConsole.io.println("\t try again........",Color.RED);
			GameMenuFirst();
		}
		return objectList;
	}

	public String playerName() {
		GuiConsole.io.println("Welcome player! \nPlease enter your name: ",Color.GREEN);
		name = GuiConsole.io.nextLine();
		return name;

	}

	public Object[] Gamestart() {
		// boolean running = true;
		// GAME: // This can be uses as at startpoint, then ever we wanna get back here,
		// have GAME; like a break but put us here instead

		// while (running) {
		GuiConsole.io.println();
		GuiConsole.io.println();
		GuiConsole.io.println("|=======================================================================================================|",Color.RED);
		GuiConsole.io.println("|=================================||----------| GAME MENU |----------||=================================|",Color.GREEN);
		GuiConsole.io.println("|=======================================================================================================|",Color.YELLOW);
		GuiConsole.io.println();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		GuiConsole.io.println("[H]elp - Read about the game...",Color.YELLOW); // Readfile eller metod där allt står om spelet, //
																// spelrunda
		GuiConsole.io.println("[N]ew  - Create new character..",Color.RED); // tar in hero metoden
		GuiConsole.io.println("[A]i character ..",Color.GRAY); // läser från load metoden oc
		GuiConsole.io.println("[L]oad - Load your character.."); // läser från load metoden och tar in befiltlig spelare
		GuiConsole.io.println("[S]ee  - See highscore (treasure points) for character..",Color.GREEN); //
		GuiConsole.io.println("[R]ead - Read about the characters..",Color.ORANGE);// om spelkaraktärer
		GuiConsole.io.println("[E]XIT/SAVE\n",Color.WHITE);// THen exit automatic save the game
		//input = scanner.next();
		input =GuiConsole.io.nextLine();
		// Valen i menu

		switch (input.toUpperCase()) {

		case "H":
			GuiConsole.io.println(); /* Aiham */
			// Alternativ...1. read from file method in saveLoad 2. göra metod med allt
			// Fånga denna senare innan demo är klart GÄLLER ALLA DESSA TRY/CATCH
			// exempel: System.out.printf("BADNESS...",e);

			iGame();
			break;
		case "N": // Returnera en object list för map och hero för att köra spelet.
			playerName();
			HeroChoice();
			maping();
			cornerChoice();
			// game.playerCombatAction(); parametrar? scanner,hero,monster,maps?
			// Här ska den in i battle??
			break;
		// System.out.println("[C]-Choose your charachter..");// När Hero metoden är
		// klar....Disk med Daniel senare
		case "A": // Returnera en object list för map och hero för att köra spelet.
			// playerName();
			AiHeroChoice();
			maping();
			AiHero.cornerRandom();
			// game.playerCombatAction(); parametrar? scanner,hero,monster,maps?
			// Här ska den in i battle??
			break;

		case "L":
			// Alternativ...1. read from file method in saveLoad
			// 2. göra metod med allt
			GuiConsole.io.println("[L]-DENNA METOD GÖRS SENARE - Load your character..",Color.YELLOW);
			Gamestart();
			break;
		case "S":
			GuiConsole.io.println("[S]-DENNA METOD GÖRS SENARE - See highscore (treasure points) for character..",Color.RED);
			// Alternativ...1. read from file method in saveLoad 2. göra metod med allt
			Gamestart();
			break;
		case "R":
			GuiConsole.io.println();
			// Alternativ...1. read from file method in saveLoad 2. göra metod med allt
			ReadChar();
			GuiConsole.io.println();
			break;
		case "E":
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			GuiConsole.io.println("You now exit the game....",Color.YELLOW);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			GuiConsole.io.println("Data will be automatic saved.....",Color.YELLOW);
			System.exit(0);
			GuiConsole.io.println();
		default:
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			GuiConsole.io.println("No such option in menu",Color.RED);
			GuiConsole.io.println("\t try again........",Color.YELLOW);
			Gamestart();
			break;

		}
		// }
	//	save.saveToDisk(objectList);
		//save.saveToDisk(name,herotype);
		//save.saveToDisk(objectList);
		return objectList;
	}// END GameMenu
	
	public String endMenu(Hero hero) {
		//save here
		if(hero.dead) 
			GuiConsole.io.println("Your adventures are over, these are your accomplishments:",Color.YELLOW);
		else
			GuiConsole.io.println("You managed to get out of the dungeon alive, these are your accomplishments:",Color.BLUE);
		GuiConsole.io.println("Treasure worth "+hero.treasure+" coins collected\n"
						+ hero.visitedrooms+" rooms visited\n"
						+ hero.deadgiantspiders+" Giant Spiders slain\n"
						+ hero.deadskeletons+" Skeletons slain\n"
						+ hero.deadorcs+" Orcs slain\n"
						+ hero.deadtrolls+" Trolls slain\n"
						+ (hero.deadgiantspiders+hero.deadskeletons+hero.deadorcs+hero.deadtrolls)+" monsters slain in total\n"
						+ hero.adventures+" adventures undertaken\n",Color.RED);
		GuiConsole.io.println("[M]ain menu\n[Q]uit game",Color.GREEN);
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

}


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
		System.out.println("Choose map size:");
		System.out.println("Press 1 for map: 4x4\nPress 2 for map: 5x5\nPress 3 for map: 8x8");
		int operator = scanner.nextInt();
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
			System.out.println("Something went wrong, please try again!");
			maping();
		}
		objectList[0] = map;
	}

	public void cornerChoice() {
		System.out.println("Choose a corner to start in:");
		// System.out.println("╭┄┄┄┄┄┄┄┄┄╮\n┆1 2┆\n┆ ┆\n┆ ┆\n┆3 4┆\n╰┄┄┄┄┄┄┄┄┄╯");
		System.out.println(
				" ___________ \n|           |\n| 1       2 |\n|           |\n|           |\n| 3       4 |\n|___________|");
		String corner = null;
		int operator = scanner.nextInt();
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
			System.out.println("Something went wrong, please try again!");
			cornerChoice();
		}
		objectList[2] = corner;
	}  

	// Välj herotype for player
	public void HeroChoice() {
		System.out.println("Choose your character:");
		System.out.println("Press 1 for Knight\nPress 2 for Rogue\nPress 3 for Wizard");

		int operator = scanner.nextInt();
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
			System.out.println("Something went wrong, please try again!");
			HeroChoice();
		}
		Hero hero = new Hero(type, name);

		objectList[1] = hero;

	}

	public void AiHeroChoice() {
		System.out.println("Press 1 for \"AI Knight\nPress 2 for \"AI Rogue\nPress 3 for AI Wizard");

		int operator = scanner.nextInt();
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
			System.out.println("Something went wrong, please try again!");
			AiHeroChoice();
		}
		AiHero aihero = new AiHero(type);

		objectList[1] = aihero;

	}

	public void ReadChar() {
		System.out.println();
		System.out
				.println("============================== Read about the character ===================================");
		System.out.println();
		System.out.println("\n---------------------------Heros-----------------------");
		System.out.println("Knight\n"
				+ "Initiative = 5\nHerotype = Herotype\nHealth = 9\nBaseattack = 6\nAvoidance = 4\n"
				+ "Specialförmåga: Sköldblock. Riddaren blockerar alltid första attackenper strid med sin sköld \noch behöver därför varken undvika eller ta någon skada\n");

		System.out.println("Wizard\n"
				+ "Initiative = 6\nHerotype = Herotype\nHealth = 4\nBaseattack = 9\nAvoidance = 5\n"
				+ "Specialförmåga: Ljussken. Trollkarlen kan göra monster blinda och hardärför alltid 80% chans att fly från strider\n");

		System.out.println("Rogue\n"
				+ "Initiative = 7\nHerotype = Herotype\nHealth = 5\nBaseattack = 5\nAvoidance = 7\n"
				+ "Specialförmåga: Kritisk träff. Tjuven har 25% chans att göra dubbel skada varje gång tjuven attackerar\n");
		System.out.println("--------------------------Monster----------------------");
		System.out.println(
				"Giant Spider\nInitiative = 7\nMonstertype = Monster\nHealth = 1\nBaseattack = 2\nAvoidance = 3\n");
		System.out.println(
				"Skeleton\nInitiative = 4\nMonstertype = Monster\nHealth = 2\nBaseattack = 3\nAvoidance = 3\n");
		System.out.println("Orc\nInitiative = 6\nMonstertype = Monster\nHealth = 3\nBaseattack = 4\nAvoidance = 4\n");
		System.out.println("Troll\nInitiative = 2\nMonstertype = Monster\nHealth = 4\nBaseattack = 7\nAvoidance = 2\n");
		System.out.println("--------------------------Treasure----------------------");
		System.out.println("Loose coins\nTreasuretype: Treasuretype\nValue: 2\n"
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
				"============================== Instructions for the game ===================================",Color.YELLOW);
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
		GuiConsole.io.println("Demo No:1\nWelcome to the Dungeon Run!\n",Color.YELLOW);
		GuiConsole.io.println("============================== Load or start a new game ===================================",Color.BLACK);
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
			GuiConsole.io.println("Badness...");
		}

		if (input.equalsIgnoreCase("N")) {
			GuiConsole.io.println("Let the Adventures Begin...");
			objectList = Gamestart();

			try {
				Thread.sleep(300);
			} catch (InterruptedException e2) {
				GuiConsole.io.println("Badness");
			}

			try {
				Thread.sleep(300);
			} catch (InterruptedException e2) {
				GuiConsole.io.println("Badness");
			}
		} else if (input.equalsIgnoreCase("A")) {
			AiHeroChoice();
			maping();
			AiHero.cornerRandom();
		} else if (input.equalsIgnoreCase("E")) {
			GuiConsole.io.println("Too bad you're leaving....");
			GuiConsole.io.println("Come back when you dare to enter the dungeons.....");
			System.exit(0);

		} else if (input.equalsIgnoreCase("L")) {
			save.LoadFromDisk();
			GuiConsole.io.println("Denna metod görs senare.. LOAD CHARACTER. IFPLAYEREXIST METHOD");
			GameMenuFirst();

		} else {
			GuiConsole.io.println("No such option in menu");
			GuiConsole.io.println("\t try again........");
			GameMenuFirst();
		}
		return objectList;
	}

	public String playerName() {
		GuiConsole.io.println("Welcome player! \nPlease enter your name: ");
		name = scanner.next();
		return name;

	}

	public Object[] Gamestart() {
		// boolean running = true;
		// GAME: // This can be uses as at startpoint, then ever we wanna get back here,
		// have GAME; like a break but put us here instead

		// while (running) {
		GuiConsole.io.println();
		GuiConsole.io.println("============================== GAME MENU ===================================",Color.BLUE);
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
			GuiConsole.io.println("[L]-DENNA METOD GÖRS SENARE - Load your character..");
			Gamestart();
			break;
		case "S":
			GuiConsole.io.println("[S]-DENNA METOD GÖRS SENARE - See highscore (treasure points) for character..");
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
			GuiConsole.io.println("You now exit the game....");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			GuiConsole.io.println("Data will be automatic saved.....");
			System.exit(0);
			GuiConsole.io.println();
		default:
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			GuiConsole.io.println("No such option in menu");
			GuiConsole.io.println("\t try again........");
			Gamestart();
			break;

		}
		// }
		save.saveToDisk(objectList);
		//save.saveToDisk(name,herotype);
		//save.saveToDisk(objectList);
		return objectList;
	}// END GameMenu

}

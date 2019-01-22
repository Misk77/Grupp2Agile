import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
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

	public GameMenu() {
		saveload = new SaveLoad();
	}

	static Scanner scanner = new Scanner(System.in);
	String name;
	String input;
	String herotype;
	Object[] objectList = new Object[4];

	// System objects
	PlayMusic playmusic = new PlayMusic();
	// Scanner scanner = new Scanner(System.in);
	static GameMenu gMenuMain = new GameMenu();

	Game game = new Game();

	public String playerName() {
		GuiConsole.io.println("\nWelcome player! \nPlease enter your name: ", Color.WHITE);
		GuiConsole.io.print(">> ");
		name = GuiConsole.io.nextLine();
		if (name.length() < 1 || name.contains("%") || saveload.findinfile(name)) {
			GuiConsole.io.println(
					"Your name must be 1 character or longer, can not contain '%' and there can not be a character already existing with the same name",
					Color.RED);
			playerName();
		}
		return name;
	}

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
		// Gör dörr ljudet, bara bortmarkera , samt den som stänger ljudet på slutet
		playmusic.disposeSound();
		PlayMusic playmusic = new PlayMusic();
		String dungeongatesopens = "/ExternalItems/creepydungeondoorslam";
		playmusic.playBackGround(dungeongatesopens);
		GuiConsole.io.println();
		GuiConsole.io.println("Choose what corner of the map to start from:", Color.WHITE);
		GuiConsole.io.print("█████████████\n█", Color.GRAY);
		GuiConsole.io.print(" A ", Color.orange);
		GuiConsole.io.print("█████", Color.GRAY);
		GuiConsole.io.print(" B ", Color.orange);
		GuiConsole.io.print("█\n█████████████\n█████████████\n█████████████\n█", Color.GRAY);
		GuiConsole.io.print(" C ", Color.orange);
		GuiConsole.io.print("█████", Color.GRAY);
		GuiConsole.io.print(" D ", Color.orange);
		GuiConsole.io.print("█\n█████████████\n", Color.GRAY);

		String corner = null;
		GuiConsole.io.print(">> ");
		input = GuiConsole.io.nextLine();

		switch (input.toUpperCase()) {
		case "A":
			corner = "NW";
			Map.theme = "blue";
			break;
		case "B":
			corner = "NE";
			Map.theme = "red";
			break;
		case "C":
			corner = "SW";
			Map.theme = "blue";
			break;
		case "D":
			corner = "SE";
			Map.theme = "red";
			break;
		default:
			GuiConsole.io.println("Something went wrong, please try again!", Color.RED);
			cornerChoice();
		}
		playmusic.disposeSound();
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
	 * "â•”â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•—\n"
	 * ); System.out.println(hero.name + "see a gliming " + tresure.treasuretype +
	 * "behind the " +monster.monstertype); System.out.println(
	 * "â•šâ•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�\n"
	 * ); }
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
				"|======================||----------| HEROES, MONSTERS AND TREASURES |----------||=======================|",
				Color.GREEN);
		GuiConsole.io.println(
				"|=======================================================================================================|",
				Color.YELLOW);
		GuiConsole.io.println();

		GuiConsole.io.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Heroes ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n", Color.orange);
		
		GuiConsole.io.print("The Knight\n", Color.red);
		GuiConsole.io.print("Initiative: ", Color.orange);
		GuiConsole.io.print("5\n", Color.white);
		GuiConsole.io.print("Health: ", Color.orange);
		GuiConsole.io.print("9\n", Color.white);
		GuiConsole.io.print("Baseattack: ", Color.orange);
		GuiConsole.io.print("6\n", Color.white);
		GuiConsole.io.print("Avoidance: ", Color.orange);
		GuiConsole.io.print("4\n", Color.white);
		GuiConsole.io.print("Special ability:\n", Color.orange);
		GuiConsole.io.print("Shield block", Color.white);
		GuiConsole.io.print(" - The Knight always blocks the first attack with his shield.\n\n\n");
		
		GuiConsole.io.print("The Wizard\n", Color.red);
		GuiConsole.io.print("Initiative: ", Color.orange);
		GuiConsole.io.print("6\n", Color.white);
		GuiConsole.io.print("Health: ", Color.orange);
		GuiConsole.io.print("4\n", Color.white);
		GuiConsole.io.print("Baseattack: ", Color.orange);
		GuiConsole.io.print("9\n", Color.white);
		GuiConsole.io.print("Avoidance: ", Color.orange);
		GuiConsole.io.print("5\n", Color.white);
		GuiConsole.io.print("Special ability:\n", Color.orange);
		GuiConsole.io.print("Blinding flash", Color.white);
		GuiConsole.io.print(" - The Wizard can blind the monsters, giving him an 80% chance of fleeing successfully.\n\n\n");
		
		GuiConsole.io.print("The Rogue\n", Color.red);
		GuiConsole.io.print("Initiative: ", Color.orange);
		GuiConsole.io.print("7\n", Color.white);
		GuiConsole.io.print("Health: ", Color.orange);
		GuiConsole.io.print("5\n", Color.white);
		GuiConsole.io.print("Baseattack: ", Color.orange);
		GuiConsole.io.print("5\n", Color.white);
		GuiConsole.io.print("Avoidance: ", Color.orange);
		GuiConsole.io.print("7\n", Color.white);
		GuiConsole.io.print("Special ability:\n", Color.orange);
		GuiConsole.io.print("Critical hit", Color.white);
		GuiConsole.io.print(" - The Rogue has a 25% chance of dealing double damage every time he attacks.\n\n\n");
		
		GuiConsole.io.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Monsters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n", Color.orange);
		
		GuiConsole.io.print("Giant Spiders\n", Color.red);
		GuiConsole.io.print("Initiative: ", Color.orange);
		GuiConsole.io.print("7\n", Color.white);
		GuiConsole.io.print("Health: ", Color.orange);
		GuiConsole.io.print("1\n", Color.white);
		GuiConsole.io.print("Baseattack: ", Color.orange);
		GuiConsole.io.print("2\n", Color.white);
		GuiConsole.io.print("Avoidance: ", Color.orange);
		GuiConsole.io.print("3\n\n\n", Color.white);
		
		GuiConsole.io.print("Skeletons\n", Color.red);
		GuiConsole.io.print("Initiative: ", Color.orange);
		GuiConsole.io.print("4\n", Color.white);
		GuiConsole.io.print("Health: ", Color.orange);
		GuiConsole.io.print("2\n", Color.white);
		GuiConsole.io.print("Baseattack: ", Color.orange);
		GuiConsole.io.print("3\n", Color.white);
		GuiConsole.io.print("Avoidance: ", Color.orange);
		GuiConsole.io.print("3\n\n\n", Color.white);

		GuiConsole.io.print("Orcs\n", Color.red);
		GuiConsole.io.print("Initiative: ", Color.orange);
		GuiConsole.io.print("6\n", Color.white);
		GuiConsole.io.print("Health: ", Color.orange);
		GuiConsole.io.print("3\n", Color.white);
		GuiConsole.io.print("Baseattack: ", Color.orange);
		GuiConsole.io.print("4\n", Color.white);
		GuiConsole.io.print("Avoidance: ", Color.orange);
		GuiConsole.io.print("4\n\n\n", Color.white);
		
		GuiConsole.io.print("Trolls\n", Color.red);
		GuiConsole.io.print("Initiative: ", Color.orange);
		GuiConsole.io.print("2\n", Color.white);
		GuiConsole.io.print("Health: ", Color.orange);
		GuiConsole.io.print("4\n", Color.white);
		GuiConsole.io.print("Baseattack: ", Color.orange);
		GuiConsole.io.print("7\n", Color.white);
		GuiConsole.io.print("Avoidance: ", Color.orange);
		GuiConsole.io.print("2\n\n\n", Color.white);
		
		GuiConsole.io.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Treasures ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n", Color.orange);
		
		GuiConsole.io.print("Loose coins\n", Color.red);
		GuiConsole.io.print("Value: ", Color.orange);
		GuiConsole.io.print("2\n\n\n", Color.white);

		GuiConsole.io.print("Small bag of coins\n", Color.red);
		GuiConsole.io.print("Value: ", Color.orange);
		GuiConsole.io.print("6\n\n\n", Color.white);
		
		GuiConsole.io.print("Gold jewellry\n", Color.red);
		GuiConsole.io.print("Value: ", Color.orange);
		GuiConsole.io.print("10\n\n\n", Color.white);
		
		GuiConsole.io.print("Precious stone\n", Color.red);
		GuiConsole.io.print("Value: ", Color.orange);
		GuiConsole.io.print("14\n\n\n", Color.white);
		
		GuiConsole.io.print("Small treasure chest\n", Color.red);
		GuiConsole.io.print("Value: ", Color.orange);
		GuiConsole.io.print("20\n\n\n", Color.white);
		
		Gamestart();
	}

	public void iGame() {
		/* Instructions for the game. */
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
		GuiConsole.io.print("1", Color.orange);
		GuiConsole.io.print(" - You need to pick a character\n");
		GuiConsole.io.print("2", Color.orange);
		GuiConsole.io.print(" - Choose the map size: Small (4, 4) - Medium (5, 5) - Large (8, 8)\n");
		GuiConsole.io.print("3", Color.orange);
		GuiConsole.io.print(" - You will battle monsters\n");
		GuiConsole.io.print("4", Color.orange);
		GuiConsole.io.print(" - Pick up Treasures!\n");
		GuiConsole.io.print("5", Color.orange);
		GuiConsole.io.print(" - Game ends when the player leaves the map or gets defeated\n");
		GuiConsole.io.print("6", Color.orange);
		GuiConsole.io.print(" - The game command movement is: North, South, East, West\n");
		Gamestart();
	}

//Games start here, then NEW GAME the follow the methods one by one , into  Game class and the game is set to go running
	public Object[] GameMenuFirst() throws ClassNotFoundException {
		playmusic.disposeSound();
		String backgroundmusic = "/ExternalItems/mysterymusic";

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

		} else if (input.equalsIgnoreCase("E")) {
			GuiConsole.io.println("Too bad you're leaving....", Color.ORANGE);
			GuiConsole.io.println("Come back when you dare to enter the dungeons.....", Color.BLUE);
			System.exit(0);

		} else if (input.equalsIgnoreCase("L")) {

			GuiConsole.io.println(
					"Denna metod gÃ¶rs senare.. GuiConsole.io.println(\"[L]oad-DENNA METOD GÃ–RS SENARE -TEST NU MED SERI LOAD your character..\", Color.YELLOW);LOAD CHARACTER. IFPLAYEREXIST METHOD",
					Color.YELLOW);
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
	
		GuiConsole.io.print("[");
		GuiConsole.io.print("N", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("ew", Color.orange);
		GuiConsole.io.print(" - Create a new character\n");
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("L", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("oad", Color.orange);
		GuiConsole.io.print(" - Load your character\n");
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("A", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("I", Color.orange);
		GuiConsole.io.print(" - Computer assisted speedrun\n");
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("H", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("ighscores", Color.orange);
		GuiConsole.io.print(" - The most successful treasure hunters\n");
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("R", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("ead", Color.orange);
		GuiConsole.io.print(" - About Heroes, Monsters & Treasures\n");
		
		GuiConsole.io.print("[");
		GuiConsole.io.print("I", Color.orange);
		GuiConsole.io.print("]");
		GuiConsole.io.print("nstructions", Color.orange);
		GuiConsole.io.print(" - How to play the game\n\n");

		GuiConsole.io.print("[");
		GuiConsole.io.print("E", Color.red);
		GuiConsole.io.print("]");
		GuiConsole.io.print("xit & Save\n\n", Color.red);
		
		GuiConsole.io.print(">> ");
		input = GuiConsole.io.nextLine();
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
			// Map.clearScreenWhenEnteringRoom = true;
			cornerRandom();

			break;
		case "L":
			// Daniels loading
			// printing list of all saved characters
			ArrayList<String[]> heronameclasslist = saveload.namesAndClassList();
			GuiConsole.io.println("Characterlist: ");
			for (String[] nameclass : heronameclasslist) {
				GuiConsole.io.println();
				for (String nameorclass : nameclass) {
					GuiConsole.io.print(nameorclass + " ", Color.orange);
				}
			}
			GuiConsole.io.print("\n\nWhich ");
			GuiConsole.io.print("character", Color.orange);
			GuiConsole.io.println(" do you want to load?");
			GuiConsole.io.print(">> ");
			String name = GuiConsole.io.nextLine();
			String[] heroinfo = saveload.load(name);
			if (heroinfo.length < 2) {
				GuiConsole.io.println("No hero by that name exists");
				Gamestart();
			} else {
				Hero hero = new Hero(heroinfo[0], heroinfo[1]);
				hero.treasure = Integer.parseInt(heroinfo[2]);
				hero.deadgiantspiders = Integer.parseInt(heroinfo[3]);
				hero.deadskeletons = Integer.parseInt(heroinfo[4]);
				hero.deadorcs = Integer.parseInt(heroinfo[5]);
				hero.deadtrolls = Integer.parseInt(heroinfo[6]);
				hero.adventures = Integer.parseInt(heroinfo[7]);
				objectList[1] = hero;
				maping();
				cornerChoice();
			}
			// Daniels loading

			break;
		case "S":
			// KANSKE DENNA SKA BORT
			
			GuiConsole.io.println("[S]ave-DENNA METOD GÖRS SENARE(TEST) - SAVE your character..", Color.YELLOW);
			// SaveLoad.saveToDisk();
			Gamestart();
			break;
		case "H":
			
			ArrayList<Hero> sortedherolist = saveload.highscore();
			GuiConsole.io.println("Highscores:\n");
			for (Hero hero : sortedherolist) {
				GuiConsole.io.println(hero.treasure + " " + hero.name);
			}
			
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
		// save goes here
	
		//GuiConsole.io.gotoEnd();// Bara för att säkerställa att den scrolllar till slutet speciellt för AI
		//effects
		playmusic.disposeSound();
		PlayMusic playmusic = new PlayMusic();
		String gameover = "/ExternalItems/gameover";
		playmusic.playBackGround(gameover);
		saveload.save(hero);
		
		if (hero.dead) {

			GuiConsole.io.print("Your");
			GuiConsole.io.print(" adventures ", Color.white);
			GuiConsole.io.print("are over, these are your ");
			GuiConsole.io.print("accomplishments", Color.white);
			GuiConsole.io.println(":");
		} else {
			GuiConsole.io.print("You managed to get out of the dungeon");
			GuiConsole.io.print(" alive ", Color.green);
			GuiConsole.io.print(", these are your ");
			GuiConsole.io.print("accomplishments", Color.white);
			GuiConsole.io.println(":");
		}
		GuiConsole.io.gotoEnd();

		printStatistics(hero);

		GuiConsole.io.print("[");
		GuiConsole.io.print("M", Color.white);
		GuiConsole.io.print("]");
		GuiConsole.io.println("ain menu", Color.white);

		GuiConsole.io.print("[");
		GuiConsole.io.print("Q", Color.red);
		GuiConsole.io.print("]");
		GuiConsole.io.println("uit game", Color.red);
		while (true) {
			String option = GuiConsole.io.nextLine().toLowerCase();
			if (option.equals("m")) {
				playmusic.disposeSound();
				return "menu";
			} else if (option.equals("q")) {
				System.exit(0);
			} else {

				continue;
			}

		}

	}

	// play a music
	public void printStatistics(Hero hero) {
		GuiConsole.io.print("Treasure", Color.orange);
		GuiConsole.io.print(" worth ");
		GuiConsole.io.print(hero.treasure, Color.white);
		GuiConsole.io.println(" coins collected");

		GuiConsole.io.print(hero.visitedrooms, Color.white);
		GuiConsole.io.print(" rooms ", Color.white);
		GuiConsole.io.println("visited this run");

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

		GuiConsole.io.print((hero.deadgiantspiders + hero.deadskeletons + hero.deadorcs + hero.deadtrolls),
				Color.white);
		GuiConsole.io.print(" monsters ", Color.orange.darker());
		GuiConsole.io.print("slain", Color.white);
		GuiConsole.io.println(" in total");

		GuiConsole.io.print(hero.adventures, Color.white);
		GuiConsole.io.print(" adventures ", Color.green);
		GuiConsole.io.println("undertaken\n");
	}

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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class GameMenuMain {
	String name;
	String input;

	// System objects

	Scanner scanner = new Scanner(System.in);
	static GameMenuMain gMenuMain = new GameMenuMain();
	SaveLoad save = new SaveLoad();
	FileData fd = new FileData();
	
	public void ReadChar() {
		System.out.println("\n---------------------------Heros-----------------------");
		System.out.println("Knight\n"
				+ "initiative = 5\nherotype = herotype\nhealth = 9\nbaseattack = 6\navoidance = 4\n"
				+ "Specialförmåga: Sköldblock. Riddaren blockerar alltid första attackenper strid med sin sköld \noch behöver därför varken undvika eller ta någon skada\n");
		
		System.out.println("Wizard\n"
				+ "initiative = 6\nherotype = herotype\nhealth = 4\nbaseattack = 9\navoidance = 5\n"
				+ "Specialförmåga: Ljussken. Trollkarlen kan göra monster blinda och hardärför alltid 80% chans att fly från strider\n");
		
	
		System.out.println("Rogue\n"
				+ "initiative = 7\nherotype = herotype\nhealth = 5\nbaseattack = 5\navoidance = 7\n"
				+ "Specialförmåga: Kritisk träff. Tjuven har 25% chans att göra dubbel skada varje gång tjuven attackerar\n");
		System.out.println("--------------------------Monster----------------------");
		System.out.println("Giant Spider\ninitiative = 7\nMonstertype = Monster\nhealth = 1\nbaseattack = 2\navoidance = 3\n");
		System.out.println("Skeleton\ninitiative = 4\nMonstertype = Monster\nhealth = 2\nbaseattack = 3\navoidance = 3\n");
		System.out.println("Orc\ninitiative = 6\nMonstertype = Monster\nhealth = 3\nbaseattack = 4\navoidance = 4\n");
		System.out.println("Troll\ninitiative = 6\nMonstertype = Monster\nhealth = 3\nbaseattack = 4\navoidance = 4\n");
		System.out.println("--------------------------Treasure----------------------");
		System.out.println("Loose coins\ntreasuretype: treasuretype\nValue: 2\n"
				+ "\nSmall bag of coins\ntreasuretype: treasuretype\nValue: 6\n"
				+ "\nGold jewellry\ntreasuretype: treasuretype\nValue: 10\n"
				+ "\nPrecious stone\ntreasuretype: treasuretype\nValue: 14\n"
				+ "\nSmall treasure chest\ntreasuretype: treasuretype\nValue: 20\n");
	}
	
	public void intro() {
		
		System.out.println("1. You will need to pick yourself a character of your choice.\r\n" + 
				"2. Choose your size of the game, small, medium or large. It’s the map.\r\n" + 
				"3. You will battle monsters.\r\n" + 
				"4. Pick up Treasures!\r\n" + 
				"5. The adventure is Game Over when the player leaves the map or gets defeated.\r\n" + 
				"6. The commands to the games movement is “In need to be written” North, South, East, West. \r\n" + 
				"");
	}
	
	

	public GameMenuMain() {
		// TODO Auto-generated constructor stub
		JOptionPane.showMessageDialog(null, "Demo1", "Hello\n Welcome to the Dungeon Run "
				+ System.getProperty("user.name") + "!" + "\nKlick [OK] to start the console program.", 0);
		System.getProperties();
		save.saveToDisk();
		System.out.println("Enter the Dungeon now....\n[Y]es or [N]o");
		input = scanner.next();

		try {
			Thread.sleep(300);
		} catch (InterruptedException e2) {
			System.out.printf("Badness...", e2);
		}

		if (input.equals("Y")) {
			System.out.println("Du tryckte Y: Adventures Begins...");

			try {
				Thread.sleep(300);
			} catch (InterruptedException e2) {
				System.out.printf("Badness", e2);
			}

			try {
				Thread.sleep(300);
			} catch (InterruptedException e2) {
				System.out.printf("Badness", e2);
			}

		} else if (input.equals("N")) {
			System.out.println("To bad your leaving....");
			System.out.println("Come back then you dare to enter the dungeons.....");
			System.exit(0);

		}

		// Game start

		boolean running = true;
		GAME: while (running) {
			System.out.println();
			System.out
					.println("============================== Lets the Game begin! ===================================");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("[H]-Help..."); // Readfile eller metod där allt står om spelet, spelrunda
			System.out.println("[C]-Choose your charachter.."); // tar in hero metoden
			System.out.println("[L]-Load your charachter.."); // läser från load metoden och tar in befiltlig spelare
			System.out.println("[S]-See highscore(treasure points) charachter.."); //
			System.out.println("[R]-Read about the charachter..");// om spelkaraktärer
			input = scanner.next();
			// Valen i menu

			switch (input) {

			case "H":
				System.out.println("[H]-Read about the game..."); /* Aiham */
				// Alternativ...1. read from file method in saveLoad 2. göra metod med allt
				intro();
				break;
			case "C":
				System.out.println("[C]-Choose your charachter..");// När Hero metoden är klar....Disk med Daniel senare
				break;
			case "L":
				System.out.println("[L]-Load your charachter.."); // Alternativ...1. read from file method in saveLoad
																	// 2. göra metod med allt
				System.out.println("[L]-DENNA METOD GÖRS SENARE - Load your charachter..");
				break;
			case "S":
				System.out.println("[S]-DENNA METOD GÖRS SENARE - See highscore(treasure points) charachter..");
				// Alternativ...1. read from file method in saveLoad 2. göra metod med allt
				break;
			case "R":
				System.out.println("[R]-Read about the charachters...");
				// Alternativ...1. read from file method in saveLoad 2. göra metod med allt
				ReadChar();
				break;

			default:
				System.out.println("No such option in menu");
				System.out.println("\t try again........");
				break;

			}

		}

	}// END GameMenuMain

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		gMenuMain.getClass();

	}// End Main

}

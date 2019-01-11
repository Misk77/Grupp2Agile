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
		System.out.println("\nKnight\n"
				+ "initiative = 5\nherotype = herotype\nhealth = 9\nbaseattack = 6\navoidance = 4\n"
				+ "Specialförmåga: Sköldblock. Riddaren blockerar alltid första attackenper strid med sin sköld \noch behöver därför varken undvika eller ta någon skada\n");
		
		System.out.println("Wizard\n"
				+ "initiative = 6\nherotype = herotype\nhealth = 4\nbaseattack = 9\navoidance = 5\n"
				+ "Specialförmåga: Ljussken. Trollkarlen kan göra monster blinda och hardärför alltid 80% chans att fly från strider\n");
		
	
		System.out.println("Rogue\n"
				+ "initiative = 7\nherotype = herotype\nhealth = 5\nbaseattack = 5\navoidance = 7\n"
				+ "Specialförmåga: Kritisk träff. Tjuven har 25% chans att göra dubbel skada varje gång tjuven attackerar\n");

		System.out.println("Giant Spider\ninitiative = 7\nMonstertype = Monster\nhealth = 1\nbaseattack = 2\navoidance = 3\n");
		System.out.println("Skeleton\ninitiative = 4\nMonstertype = Monster\nhealth = 2\nbaseattack = 3\navoidance = 3\n");
		System.out.println("Orc\ninitiative = 6\nMonstertype = Monster\nhealth = 3\nbaseattack = 4\navoidance = 4\n");
		System.out.println("Troll\ninitiative = 6\nMonstertype = Monster\nhealth = 3\nbaseattack = 4\navoidance = 4\n");
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
				System.out.println("[H]-Read about the game...");
				helpmenu();// Alternativ...1. read from file method in saveLoad 2. göra metod med allt
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

	void helpmenu() {
		System.out.println("|=================================|\r\n" + "|----------| HELP MENU |----------|\r\n"
				+ "|=================================|\r\n" + "\r\n"
				+ "Spelkaraktärer:\n Spelaren ska kunna välja bland tre olika karaktärer: (Riddaren, Trollkarlen och Tjuven) som särskiljer sig genom olika attributvärden samt varsin specialförmåga. Hur dessa värden påverkar striderna förklaras under Strid. Alla specialförmågor är passiva och behöver inte aktiveras av spelaren. \r\n"
				+ "Riddaren Attribut Värde Initiativ 5 Tålighet 9 Attack 6 Smidighet 4 Specialförmåga: Sköldblock. Riddaren blockerar alltid första attacken per strid med sin sköld, och behöver därför varken undvika eller ta någon skada.  \r\n"
				+ "Trollkarlen " + "\r\n"
				+ "|====================== INFO ABOUT DUNGENS Project ========================|\r\n");
		System.out.println("Current Player name:");
		System.out.println("Here shows your player name");
		// System.out.println(gMenuMain.name);
		System.out.println("info about Dungeons (gMenuMain) class: ");
		// System.out.println(gMenuMain.toString()); onödig kanske? info om klassen
		System.out.println("Here shows info om  klassen ");

		System.out.println();
		System.out.println("\n|====================== INFO ABOUT YOUR SYSTEM ========================|\r\n");
		System.out.printf("%-5s %5s %5s%n", "|====== Computer Name is: ", (System.getProperty("user.name")), "======|");
		Properties p = System.getProperties();
		Enumeration<Object> keys = p.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = (String) p.get(key);
			System.out.println(key + ": " + value);
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		gMenuMain.getClass();

	}// End Main

}

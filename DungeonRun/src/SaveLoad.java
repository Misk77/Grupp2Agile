import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.Scanner;

public class SaveLoad<GameMenuMain> {
	Object[] usertext;
	String filepath = "src/DungeonRun.ser";

	// PATH ("DungeonRunSaves.tmp");
	public void saveToDisk(String name, String herotype) {// need to add the proper arguments, like name and treasure
		{
			GameMenu gamemenu = new GameMenu();
			try {
				FileOutputStream fileOut = new FileOutputStream(filepath);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(gamemenu.objectList);
				out.close();
				fileOut.close();
				System.out.printf("SAVE TO: DungeonRunSaves\n");// sparar filen
			} catch (IOException i) {
				i.printStackTrace();
			}
			// game.Gamestart();
			System.out.printf("Lämnat saveToDIsk Metoden\n");// sparar filen
			System.out.println();
		}

	}

	public void LoadFromDisk() {
		GameMenu gamemenu = new GameMenu();
		try {
			FileInputStream fileIn = new FileInputStream(filepath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			usertext = (Object[]) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("DungeonRunSaves  not found\n");
			c.printStackTrace();
			return;
		}
		System.out.println();
		System.out.println("LOAD FROM:  DungeonRunSaves...\n");
		System.out.println("Name: " + gamemenu.name);
		System.out.println("Herotype: " + gamemenu.herotype);
		System.out.println("PlayerName: " + gamemenu.playerName());
		System.out.println("Object: " + gamemenu.objectList);
		System.out.printf("Lämnat LoadFromDisk Metoden\n");// sparar filen
		System.out.println();
		// game.Gamestart();
	}// LoadFromDisk

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////// BELOW THIS NOT IN USE!!!
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////// BELOW THIS NOT IN USE!!! ////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////// BELOW THIS NOT IN USE!!! ////////////////////////////////////////////////////////////
	public void writeToFile() {

		// main metoden d�r man skriver in till texten och som h�mtar metoden i
		// writefile classen
		Scanner sc = new Scanner(System.in);

		String filepath = "C:\\\\Users\\\\miche\\\\Desktop\\\\writemytextfile.txt";

		WriteFile wf = new WriteFile(filepath, true);

		try {
			System.out.println("Skriv till filen");
			String usertext = sc.nextLine();
			wf.writeToFile(usertext);
			System.out.printf("Du har skrivit till filen:\n%s", filepath);
		}

		catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Du har inte skrivit till filen.\nN�got gick fel");
		}

		sc.close();

	}
}// END of WriteToFile

/*
 * • Scanner har en konstruktor som tar ett Fileobjekt som parameter •
 * Alternativt kan man koppla ihop den med en Reader-klass • File f = new
 * File(“minfil.txt”); • Scanner in = new Scanner(f); //Kastar ett kontrollerat
 * undantag • Scanner in2 = new Scanner(new FileReader(“minfil.txt”)); • String
 * str = in2.next(); • in2.close();
 */
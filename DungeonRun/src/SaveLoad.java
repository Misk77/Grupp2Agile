import java.awt.Color;
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
				out.writeObject(usertext);
				out.close();
				fileOut.close();
				GuiConsole.io.println("SAVE TO: DungeonRunSaves\n",Color.GREEN);// sparar filen
			} catch (IOException i) {
				i.printStackTrace();
			}
			// game.Gamestart();
			GuiConsole.io.println("Lämnat saveToDIsk Metoden\n",Color.BLUE);// sparar filen
			GuiConsole.io.println();
		}

	}

	public void LoadFromDisk() {
		GameMenu gamemenu = new GameMenu();
		try {
			FileInputStream fileIn = new FileInputStream(filepath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			//System.out.println("Data: \n"+in.readObject().toString());
			usertext = (Object[]) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			GuiConsole.io.println("DungeonRunSaves  not found\n",Color.RED);
			c.printStackTrace();
			return;
		}
		System.out.println();
		GuiConsole.io.println("LOAD FROM:  DungeonRunSaves...\n",Color.WHITE);
		GuiConsole.io.println("Name: " + gamemenu.name,Color.BLUE);
		GuiConsole.io.println("Herotype: " + gamemenu.herotype,Color.BLACK);
		GuiConsole.io.println("PlayerName: " + gamemenu.playerName(),Color.GREEN);
		GuiConsole.io.println("Object: " + usertext,Color.WHITE);
		GuiConsole.io.println("Lämnat LoadFromDisk Metoden\n",Color.RED);// sparar filen
		GuiConsole.io.println();
		//gamemenu.maping();
		// game.Gamestart();
	}// LoadFromDisk

	

	public void saveToDisk(Object[] objectList) {
		// TODO Auto-generated method stub
		GameMenu gamemenu = new GameMenu();
		try {
			FileOutputStream fileOut = new FileOutputStream(filepath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(usertext);
			out.close();
			fileOut.close();
			GuiConsole.io.println("SAVE TO: DungeonRunSaves\n",Color.RED);// sparar filen
		} catch (IOException i) {
			i.printStackTrace();
		}
		// game.Gamestart();
		GuiConsole.io.println("Lämnat saveToDIsk Metoden\n",Color.RED);// sparar filen
		GuiConsole.io.println();

	}

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
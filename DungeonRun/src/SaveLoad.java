import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SaveLoad<GameMenuMain> {
	
	public void saveToDisk() {//need to add the proper arguments, like name and treasure
			// main metoden d�r man skriver in till texten och som h�mtar metoden i
			// writefile classen
			Scanner sc = new Scanner(System.in);//This will be changed so pnly one object of scanner are implementet,maybye should be from GameMenuMain

			String filepath = "C:\\\\Users\\\\miche\\\\Desktop\\\\DungenSaves.txt";

			WriteFile wf = new WriteFile(filepath, true);
			// Just now its saves player name direct, maybe or also this save then the hero ends a game, same method or another??

			try {

				
				String usertext = JOptionPane.showInputDialog(

						null, "Enter you nick ", "Welcome player", JOptionPane.PLAIN_MESSAGE);
				// if the user presses Cancel, this will be null and not properly saved..hmmm
				System.out.printf("TYour nick is set to: '%s'.\n", usertext);

				wf.writeToFile(usertext);
				System.out.printf("Du har sparats.\nSpelar namn\n%s:\n%s", usertext, filepath);//Denna ska bort!!
			}

			catch (IOException e) {

				System.out.println("Ditt nick har ej sparatsfilen.\nNågot gick fel");
			}

		
	
	}//saveTODisk
	public void LoadFromDisk() {
		
	}//LoadFromDisk
	
	
	
}



/*
• Scanner har en konstruktor som tar ett Fileobjekt som parameter
• Alternativt kan man koppla ihop den med
en Reader-klass
• File f = new File(“minfil.txt”);
• Scanner in = new Scanner(f);
//Kastar ett kontrollerat undantag
• Scanner in2 = new Scanner(new
FileReader(“minfil.txt”));
• String str = in2.next();
• in2.close();
*/
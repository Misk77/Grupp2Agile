import java.io.IOException;
import java.util.Scanner;

public class SaveLoad<GameMenuMain> {
	String usertext;

	public void saveToDisk(Object[] objectList) {// need to add the proper arguments, like name and treasure
		// main metoden d�r man skriver in till texten och som h�mtar metoden i
		// writefile classen
		Scanner sc = new Scanner(System.in);// This will be changed so pnly one object of scanner are implementet,maybye
											// should be from GameMenu

		String filepath = "DungenSaves.txt";

		WriteFile wf = new WriteFile(filepath, true);
		// Just now its saves player name direct, maybe or also this save then the hero
		// ends a game, same method or another??

		try {

			String usertext = sc.next();

			wf.writeToFile(usertext);
			System.out.printf("Du har sparats.\nSpelar namn\n%s:\n%s", usertext, filepath);// Denna ska bort!!
		}

		catch (IOException e) {

			System.out.println("Ditt nick har ej sparatsfilen.\nNågot gick fel");
		}
		sc.close();
	}// saveTODisk

	public void LoadFromDisk() {

	}// LoadFromDisk
	
	public void writeToFile() {
		
		
		// main metoden d�r man skriver in till texten och som h�mtar metoden i writefile classen
		Scanner sc = new Scanner(System.in);
	
		String filepath = "C:\\\\Users\\\\miche\\\\Desktop\\\\writemytextfile.txt";
		
				
		WriteFile wf = new WriteFile(filepath,true);
		
		
		try {
			System.out.println("Skriv till filen");
			String usertext=sc.nextLine();
			wf.writeToFile(usertext);
			System.out.printf("Du har skrivit till filen:\n%s",filepath);
		}
		
		catch(IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Du har inte skrivit till filen.\nN�got gick fel");
		}
		

		
		
		
		
		sc.close();

}
}//END of WriteToFile

/*
 * • Scanner har en konstruktor som tar ett Fileobjekt som parameter •
 * Alternativt kan man koppla ihop den med en Reader-klass • File f = new
 * File(“minfil.txt”); • Scanner in = new Scanner(f); //Kastar ett kontrollerat
 * undantag • Scanner in2 = new Scanner(new FileReader(“minfil.txt”)); • String
 * str = in2.next(); • in2.close();
 */
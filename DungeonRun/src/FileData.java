
import java.io.IOException;
import java.util.Scanner;

public class FileData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// main metoden där man skriver in till texten och som hämtar metoden i writefile classen
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
			System.out.println("Du har inte skrivit till filen.\nNågot gick fel");
		}
		

		
		
		
		
		sc.close();
		// END
	}

}


import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class WriteFile {

	// konstruktor
	String path; // s�kv�gen till texten
	boolean append; // true -nya rader - false, skriver �ver

	public WriteFile(String file_path, boolean append_value) {
		path = file_path;
		append = append_value;
	}

	// metoden som skriver in
	public void writeToFile(String text) throws IOException {
		FileWriter write = new FileWriter(path, append);// l�ser bara bytes
		PrintWriter print = new PrintWriter(write);
		print.printf("%s" + "%n", text);
		write.close();
		print.close();

	}

}

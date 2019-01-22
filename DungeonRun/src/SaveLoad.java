import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveLoad {
	
	final static String filename = "DungeonRunSaveData.txt";
	
	public SaveLoad() {
		try {
			File file = new File(filename);
			if(!file.exists()) {
				file.createNewFile();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void printall() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean findinfile(String findthis) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println(line);
				String[] linearray = (line.split("%"));
				if(linearray[1].equals(findthis))
					return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
				
	
	public void save(Hero hero) {
		try {
			boolean exists = false;
			ArrayList<String> tester = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String[] linearray = line.split("%");
				if(linearray[1].equals(hero.name)) {
					exists = true;
					tester.add((hero.herotype + "%" + hero.name + "%" + hero.treasure + "%" + hero.deadgiantspiders + "%" + hero.deadskeletons + "%" + hero.deadorcs + "%" + hero.deadtrolls + "%" + hero.dead));
				}
				else {
					tester.add(line);
				}
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			for(String line : tester) {
				bw.write(line+System.getProperty("line.separator"));
			}
			if(!exists) {
				bw.write((hero.herotype + "%" + hero.name + "%" + hero.treasure + "%" + hero.deadgiantspiders + "%" + hero.deadskeletons + "%" + hero.deadorcs + "%" + hero.deadtrolls + "%" + hero.dead));
			}
			
			bw.close();
		}
		catch(Exception e) {
			System.out.println("Probably didnt enter the filename correctly");
			//shouldnt get here
		}
	}
	
	public String [] load(String name) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String [] linearray = line.split("%");
				if(linearray[1].equals(name)) {
					//System.out.println(linearray[0]+" "+linearray[1]);
					return linearray;
				}
			}
		} catch (Exception e) {
			System.out.println("Probably didnt enter the filename correctly");
			//shouldnt get here
		}
		String [] emptyarray = {};
		return emptyarray;
	}
	public ArrayList<String[]> namesAndClassList(){
		ArrayList<String[]> heronameclass = new ArrayList<String[]>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String [] linelist = line.split("%");
				String [] nameclasslist = {linelist[1], linelist[0]};
				heronameclass.add(nameclasslist);
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return heronameclass;
	}
}




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveLoad {
	
	final static String filename = "DungeonRunSaveData.txt";
	
	public SaveLoad() {
		try {
			File file = new File(filename);
			if(!file.exists()) {
				file.createNewFile();
			}
		
		} catch (IOException e) {
			GuiConsole.io.println(e);
		}
		
	}
	
	public void printall() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println(line);
			}
		} catch (IOException e) {
			GuiConsole.io.println(e);
		}
	}
	
	public boolean findinfile(String findthis) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				GuiConsole.io.println(line);             //Ändrade denna System.out.println(line);
				String[] linearray = (line.split("%"));
				if(linearray[1].equals(findthis))
					return true;
			}
		} catch (IOException e) {
			GuiConsole.io.println(e);
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
					tester.add((hero.herotype + "%" + hero.name + "%" + hero.treasure + "%" + hero.deadgiantspiders + "%" + hero.deadskeletons + "%" + hero.deadorcs + "%" + hero.deadtrolls + "%" + hero.adventures + "%" + hero.dead + "%" + hero.ai));
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
				bw.write((hero.herotype + "%" + hero.name + "%" + hero.treasure + "%" + hero.deadgiantspiders + "%" + hero.deadskeletons + "%" + hero.deadorcs + "%" + hero.deadtrolls + "%" + hero.adventures +  "%" + hero.dead + "%" + hero.ai));
			}
			
			bw.close();
		}
		catch(Exception e) {
			GuiConsole.io.println(e);
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
			GuiConsole.io.println(e);
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
				if(linelist[9].equals("true")) {
					//character is ai
					
				}
				else if(linelist[8].equals("false")) {
					//character is not dead
					String [] nameclasslist = {linelist[1], linelist[0]};
					heronameclass.add(nameclasslist);
				}
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			GuiConsole.io.println(e);
		}
		return heronameclass;
	}
	
	public ArrayList<Hero> highscore(){
		ArrayList<Hero> herolist = new ArrayList<Hero>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String [] linesplit = line.split("%");
				if(linesplit[9].equals("true")) {
					//we ignore ai scores
				}
				else {
					Hero hero = new Hero(linesplit[0], linesplit[1]);
					hero.treasure = Integer.parseInt(linesplit[2]);
					hero.deadgiantspiders = Integer.parseInt(linesplit[3]);
					hero.deadskeletons = Integer.parseInt(linesplit[4]);
					hero.deadorcs = Integer.parseInt(linesplit[5]);
					hero.deadtrolls = Integer.parseInt(linesplit[6]);
					hero.adventures = Integer.parseInt(linesplit[7]);
					herolist.add(hero);
				}
			}
		//sort them
		for(int i = 0; i<herolist.size(); i++) {
			for(int e = 0; e<herolist.size(); e++) {
				if(herolist.get(i).treasure > herolist.get(e).treasure) {
					Hero helpvar = herolist.get(i);
					herolist.set(i, herolist.get(e));
					herolist.set(e, helpvar);
				}
			}
		}
		}
		catch (IOException e) {
			GuiConsole.io.println(e);
		}
		return herolist;
	}
}




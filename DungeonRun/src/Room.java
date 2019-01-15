import java.util.ArrayList;
import java.util.Random;

public class Room {
	ArrayList<Monster> monsterlist = new ArrayList<Monster>();
	ArrayList<Treasure> treasurelist = new ArrayList<Treasure>();
	int x; //x room coordinates
	int y; //y rom coordinates
	boolean exit = false;
	boolean visited = false;
	
	Random rand;
	
	public Room() {
		rand = new Random();
		//generating monster for room
		if(rand.nextInt(100)+1 <= 20) {
			Monster monster = new Monster("Giant spider");
			this.monsterlist.add(monster);
		}
		if(rand.nextInt(100)+1 <= 15) {
			Monster monster = new Monster("Skeleton");
			this.monsterlist.add(monster);
		}
		if(rand.nextInt(100)+1 <= 10) {
			Monster monster = new Monster("Orc");
			this.monsterlist.add(monster);
		}
		if(rand.nextInt(100)+1 <= 5) {
			Monster monster = new Monster("Troll");
			this.monsterlist.add(monster);
		}
		
		//generating treasures for room
		if(rand.nextInt(100)+1 <= 40) {
			Treasure treasure = new Treasure("Loose coins");
			this.treasurelist.add(treasure);
		}
		if(rand.nextInt(100)+1 <= 20) {
			Treasure treasure = new Treasure("Small bag of coins");
			this.treasurelist.add(treasure);
		}
		if(rand.nextInt(100)+1 <= 15) {
			Treasure treasure = new Treasure("Gold jewellry");
			this.treasurelist.add(treasure);
		}
		if(rand.nextInt(100)+1 <= 10) {
			Treasure treasure = new Treasure("Precious stone");
			this.treasurelist.add(treasure);
		}
		if(rand.nextInt(100)+1 <= 5) {
			Treasure treasure = new Treasure("Small treasure chest");
			this.treasurelist.add(treasure);
		}
	}
	//generate monsters
	//generate treasures
	//according to proper percentages
}

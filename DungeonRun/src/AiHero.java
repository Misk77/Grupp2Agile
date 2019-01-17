import java.util.Random;
import java.util.Scanner;

public class AiHero {

	int initiative;
	int health;
	int baseattack;
	int avoidance;
	String herotype;
	int treasure = 0;
	boolean dead = false;
	boolean player = true;
	boolean turntaken = false;
	int lastinitiativeroll = 0;

	boolean block = false;
	static Scanner scanner = new Scanner(System.in);
	static Random rand;
	String[] corners = { "NW ", "NE ", "SW", "SE " };
	static Object[] objectList = new Object[3];
	
	
	public AiHero(String herotype) {
		
		rand = new Random();
		if (herotype.equals("Knight")) {

			this.initiative = 5;
			this.herotype = herotype;
			this.health = 9;
			this.baseattack = 6;
			this.avoidance = 4;
			this.block = true; // this needs to be set to true at the start or end of every room
		} else if (herotype.equals("Wizard")) {

			this.initiative = 6;
			this.herotype = herotype;
			this.health = 4;
			this.baseattack = 9;
			this.avoidance = 5;
		} else if (herotype.equals("Rogue")) {

			this.initiative = 7;
			this.herotype = herotype;
			this.health = 5;
			this.baseattack = 5;
			this.avoidance = 7;
		}
	}// Aihero herotype ends

	public int attackRoll() {
		int attack = 0;
		for (int i = 0; i < baseattack; i++) {
			attack = attack + rand.nextInt(6) + 1;
		}
		return attack;
	}

	public int defendRoll() {
		int avoid = 0;
		for (int i = 0; i < avoidance; i++) {
			avoid = avoid + rand.nextInt(6) + 1;
		}
		return avoid;
	}

	public void takeDamage(int damageTaken) {
		if (this.herotype.equals("Knight")) {
			if (block) {
				block = false;
			}
		} else {
			this.health = this.health - damageTaken;
			if (health <= 0) {
				dead = true;
			}
		}
	}// take damage ends
	
	 static Object cornerRandom() {
		String[] corners = { "NW ", "NE ", "SW", "SE " };
		String corner = corners[rand.nextInt(corners.length)];
		//String str = null;
		
		return objectList[2] = corner;
	}// cornerRando m ends
	 
	 void battle(){
		
			
					System.out.println("You tried to walk through a wall, unsuccessfully");
			
	 }
	
	
	
	
	/////////////////////////////////ENDS
}//Aihero class ends

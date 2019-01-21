import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

public class Hero implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String name;
	
	int initiative;
	int health;
	int baseattack;
	int avoidance;
	String herotype;
	int treasure = 0;
	boolean dead = false;
	boolean player = true;
	boolean turntaken = false;
	int deadgiantspiders = 0;
	int deadskeletons = 0;
	int deadorcs = 0;
	int deadtrolls = 0;
	int adventures = 0;
	int visitedrooms = 0;
	boolean ai = false;
	
	int lastinitiativeroll = 0;
	
	boolean block = false;
	
	Random rand;
	
	
	public Hero(String herotype, String name) {
		rand = new Random();
		if(herotype.equals("Knight")) {
			this.name = name;
			this.initiative = 5;
			this.herotype = herotype;
			this.health = 9;
			this.baseattack = 6;
			this.avoidance = 4;
			this.block = true; //this needs to be set to true at the start or end of every room
		}
		else if(herotype.equals("Wizard")) {
			this.name = name;
			this.initiative = 6;
			this.herotype = herotype;
			this.health = 4;
			this.baseattack = 9;
			this.avoidance = 5;
		}
		else if(herotype.equals("Rogue")) {
			this.name = name;
			this.initiative = 7;
			this.herotype = herotype;
			this.health = 5;
			this.baseattack = 5;
			this.avoidance = 7;
		}
	}
	
	
	public int attackRoll() {
		int attack = 0;
		for(int i = 0; i < baseattack; i++) {
			attack = attack + rand.nextInt(6)+1;
		}
		return attack;
	}
	
	
	public int defendRoll() {
		int avoid = 0;
		for(int i =0; i < avoidance; i++) {
			avoid = avoid + rand.nextInt(6)+1;
		}
		return avoid;
	}
	
	public void takeDamage(int damageTaken) {
		if(this.herotype.equals("Knight") && this.block) {
			if(block) {
				block = false;
			}
		}
		else {
			this.health = this.health - damageTaken;
			if(health <= 0) {
				dead = true;
			}
		}
	}
	
	public int dealDamage() {
		if(this.herotype.equals("Rogue")) {
			if(rand.nextInt(100)+1 <= 25) {
				return 2;
			}
		}
		return 1;
	}
	
	public void initiativeRoll() {
		int totalInitiative = 0;
		for(int i = 0; i < initiative; i++) {
			totalInitiative = totalInitiative + rand.nextInt(6)+1;
		}
		this.lastinitiativeroll = totalInitiative;
	}
	
	public boolean flee() {
		if(this.herotype.equals("Wizard")) {
			if(rand.nextInt(100)+1 <= 80) {
				return true;
			}
		}
		else {
			if(rand.nextInt(100)+1 <= this.avoidance*10) {
				return true;
			}
		}
		return false;
	}


	public boolean allseeing(Hero hero, Treasure tresure, Monster monster) {
		 {
			 GuiConsole.io.println("╔══════════════════════════════════════════════════════════════════╗\n",Color.RED);
			 GuiConsole.io.println(hero.name + "see a gliming " + tresure.treasuretype + "behind the " +monster.monstertype,Color.RED);
			 GuiConsole.io.println("╚══════════════════════════════════════════════════════════════════╝\n",Color.RED);
			 
		 }

		return false;
	}
}

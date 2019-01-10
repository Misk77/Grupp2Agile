import java.util.Random;

public class Monster {
	int initiative;
	int health;
	int baseattack;
	int avoidance;
	String monstertype;
	boolean dead = false;
	
	Random rand;
	
	public Monster(String monstertype) {
		rand = new Random();
		if(monstertype.equals("Giant spider")) {
			this.initiative = 7;
			this.health = 1;
			this.baseattack = 2;
			this.avoidance = 3;
			this.monstertype = monstertype;
		}
		else if(monstertype.equals("Skeleton")) {
			this.initiative = 4;
			this.health = 2;
			this.baseattack = 3;
			this.avoidance = 3;
			this.monstertype = monstertype;
		}
		else if(monstertype.equals("Orc")) {
			this.initiative = 6;
			this.health = 3;
			this.baseattack = 4;
			this.avoidance = 4;
			this.monstertype = monstertype;
		}
		else if(monstertype.equals("Troll")) {
			this.initiative = 2;
			this.health = 4;
			this.baseattack = 7;
			this.avoidance = 2;
			this.monstertype = monstertype;
		}
	}
	
	public int attack() {
		int attack = 0;
		for(int i = 0; i < baseattack; i++) {
			attack = attack + rand.nextInt(6)+1;
		}
		return attack;
	}
	
	
	public int defend() {
		int avoid = 0;
		for(int i =0; i < avoidance; i++) {
			avoid = avoid + rand.nextInt(6)+1;
		}
		return avoid;
	}
	
	public void takeDamage(int damageTaken) {
		this.health = this.health - damageTaken;
		if(health <= 0) {
			dead = true;
		}
	}
	
	public int dealDamage() {
		return 1;
	}
	
	public int initiativeRoll() {
		int totalInitiative = 0;
		for(int i = 0; i < initiative; i++) {
			totalInitiative = totalInitiative + rand.nextInt(6)+1;
		}
		return totalInitiative;
	}
	
	public void resetMonsterHealth() {
		//does dead monsters get their health reset when the player flees a room?
		if(monstertype.equals("Giant spider")) {
			health = 1;
			dead = false;
		}
		else if(monstertype.equals("Skeleton")) {
			health = 2;
			dead = false;
		}
		else if (monstertype.equals("Orc")) {
			health = 3;
			dead = false;
		}
		else if(monstertype.equals("Troll")) {
			health = 4;
			dead = false;
		}
	}
}


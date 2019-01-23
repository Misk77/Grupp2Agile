import java.io.Serializable;
import java.util.Random;

public class Monster implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int initiative;
	int health;
	int maxHealth;
	int baseattack;
	int avoidance;
	String monstertype;
	boolean dead = false;
	int lastinititativeroll = 0;
	boolean player = false;

	Random rand;

	public Monster(String monstertype) {
		rand = new Random();
		if (monstertype.equals("Giant spider")) {
			this.initiative = 7;
			this.health = 1;
			this.maxHealth = this.health;
			this.baseattack = 2;
			this.avoidance = 3;
			this.monstertype = monstertype;
		} else if (monstertype.equals("Skeleton")) {
			this.initiative = 4;
			this.health = 2;
			this.maxHealth = this.health;
			this.baseattack = 3;
			this.avoidance = 3;
			this.monstertype = monstertype;
		} else if (monstertype.equals("Orc")) {
			this.initiative = 6;
			this.health = 3;
			this.maxHealth = this.health;
			this.baseattack = 4;
			this.avoidance = 4;
			this.monstertype = monstertype;
		} else if (monstertype.equals("Troll")) {
			this.initiative = 2;
			this.health = 4;
			this.maxHealth = this.health;
			this.baseattack = 7;
			this.avoidance = 2;
			this.monstertype = monstertype;
		}
	}

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
		this.health = this.health - damageTaken;
		if (health <= 0) {
			dead = true;
		}
	}

	public int dealDamage() {
		return 1;
	}

	public void initiativeRoll() {
		int totalInitiative = 0;
		for (int i = 0; i < initiative; i++) {
			totalInitiative = totalInitiative + rand.nextInt(6) + 1;
		}
		this.lastinititativeroll = totalInitiative;
	}

	public void resetMonsterHealth() {
		if (monstertype.equals("Giant spider")) {
			if (!this.dead)
				health = 1;
		} else if (monstertype.equals("Skeleton")) {
			if (!this.dead)
				health = 2;
		} else if (monstertype.equals("Orc")) {
			if (!this.dead)
				health = 3;
		} else if (monstertype.equals("Troll")) {
			if (!this.dead)
				health = 4;
		}
	}
}

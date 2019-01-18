import java.util.ArrayList;
import java.util.Random;

public class AI {
	static int pause = 300;
	static Random rand = new Random();

	public AI() {

	}

	public String chooseDirection(Map map, Hero hero) {
		int[] willPower = new int[4]; // Urge to go in either direction (N,S,W,E)
		int[] xDiff = new int[] {-1,1,0,0}; 
		int[] yDiff = new int[] {0,0,-1,1};
		String[] direction = new String[] {"north","south","west","east"};
		for(int way=0; way<4; way++) {
			for(Room room : map.room) {
				if(room.x == (map.currentroomx + xDiff[way] ) && room.y == (map.currentroomy + yDiff[way]) ) {
					willPower[way] = 20; // Room exists and the direction will be evaluated.
					if (!room.visited) {
						willPower[way] += 10; // Unexplored rooms are always prioritized.
					} else {
						if(map.lastroomvisitedx == (map.currentroomx + xDiff[way]) && map.lastroomvisitedy == (map.currentroomy + xDiff[way]) ) {
							willPower[way] -= 10; // Never look back! If the AI always chose the comfiest path, it'd get stuck.
						}

						for (Monster monster : room.monsterlist) {
							// The heros can decide differently when faced with a room known to have monsters in them:
							if (!monster.dead) {
								if(hero.herotype.equals("Knight")) {
									willPower[way] -= monster.baseattack;
								}
								if(hero.herotype.equals("Wizard")) {
									willPower[way] -= monster.baseattack;
								}
								if(hero.herotype.equals("Rogue")) {
									willPower[way] -= monster.baseattack;
								}
							}
						}
						if(rand.nextInt(100)+1 <= 10) {
							// We need a wild card to stop the idiots from walking in circles
							willPower[way] = 100;
						}

					}
				} 
			}
		}
		int bestWay = 0; // Get index of the highest willPower
		for (int i = 1; i < 4; i++ ) {
			if ( willPower[i] > willPower[bestWay] ) bestWay = i;
		}

		//		for (int i = 0; i < 4; i++ ) {
		//			System.out.println("Urge to go " + direction[i]+ ": " + willPower[i]);
		//		}


		ArrayList<String> bestChoices = new ArrayList<String>();
		for (int i = 0; i < 4; i++ ) {
			if (willPower[i] == willPower[bestWay]) {
				bestChoices.add(direction[i]);
			}
		}

		try {Thread.sleep(pause);} catch (InterruptedException e2) {System.out.printf("Badness", e2);}
		String decision = bestChoices.get(rand.nextInt(bestChoices.size()));
		System.out.println(decision);
		return decision;
	}


	public String fightOrFlight(ArrayList<Monster> monsterlist, Hero hero) {
		String decision = null;
		int monInitiative = 0;
		int monHealth = 0;
		int monAttack = 0;
		int monAvoidance = 0;
		for(Monster monster : monsterlist) {
			if(!monster.dead) {
				monInitiative += monster.initiative;
				monHealth += monster.health;
				monAttack += monster.baseattack;
				monAvoidance += monster.avoidance;
			}
		}

		if(hero.herotype.equals("Knight")) {
			if(rand.nextInt(100)+1 <= 90) {
				decision = "a";
			} else {
				decision = "f";
			}
		}

		if(hero.herotype.equals("Wizard")) {
			if ( (hero.health == 4) && (monAttack < 4)) {
				decision = "a";
			} else {
				if(rand.nextInt(100)+1 <= 20) {
					decision = "a";
				} else {
					decision = "f";
				}
			}
		}

		if(hero.herotype.equals("Rogue")) {
			if(rand.nextInt(100)+1 <= 70) {
				decision = "a";
			} else {
				decision = "f";
			}
		}

		try {Thread.sleep(pause);} catch (InterruptedException e2) {System.out.printf("Badness", e2);}
		System.out.println(decision);
		return decision;
	}



	public String chooseMonster(ArrayList<Monster> monsterlist) {
		String target = null;
		for(Monster monster : monsterlist) {
			if(!monster.dead) {
				target = monster.monstertype;
			}
		}
		try {Thread.sleep(pause);} catch (InterruptedException e2) {System.out.printf("Badness", e2);}
		return target;
	}



}

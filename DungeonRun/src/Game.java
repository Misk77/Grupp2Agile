import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		//test commit from Michel
		//test commit from Michel
		int deadmonstercount = 0;
		Map map = new Map();
		Scanner scanner = new Scanner(System.in);
		map.generateMap(4, 4);
		Game game = new Game();
		Hero hero = new Hero("Rogue", "myfirstrogue");
		for(Room room : map.room) {
			System.out.println(room.x+" "+room.y);
		}
		Room currentroom = map.startingPoint("SE");
		System.out.println("CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
		while(true) {
			if(currentroom != null) {
				System.out.println("\nMONSTERS");
				for(Monster monster : currentroom.monsterlist) {
					System.out.println(monster.monstertype);
				}
				
				System.out.println("\nTREASURES");
				for(Treasure treasure : currentroom.treasurelist) {
					System.out.println(treasure.treasuretype);
				}
				collectTreasures(currentroom.treasurelist, hero);
				
				game.sortMonsters(currentroom.monsterlist);
				for(Monster monster : currentroom.monsterlist) {
					System.out.println(monster.lastinititativeroll);
				}
				
				
				hero.initiativeRoll();
				boolean fighting = true;
				int monstercount = 0;
				int backstep = 0;
				while(!currentroom.monsterlist.isEmpty() && fighting) {
					hero.turntaken = false;
					monstercount = 0;
					for(int i = 0; i < currentroom.monsterlist.size(); i++) {
						backstep = 0;
						if(hero.lastinitiativeroll > currentroom.monsterlist.get(i).lastinititativeroll && hero.turntaken == false) {
							backstep = game.playerCombatAction(scanner, hero, currentroom.monsterlist);
							if(currentroom.monsterlist.isEmpty()) {
								System.out.println("All monsters in the room have been slain");
								break;
							}
						}
						game.monsterAttack(hero, currentroom.monsterlist.get(i));
						monstercount++;
						if(monstercount == currentroom.monsterlist.size() && hero.turntaken == false) {
							 backstep =game.playerCombatAction(scanner, hero, currentroom.monsterlist);
							if(currentroom.monsterlist.isEmpty()) {
								System.out.println("All monsters in the room have been slain");
								break;
							}
						}
						//i = i-backstep;
					}
				}
			}
			System.out.println("What direction?");
			System.out.print("\n>> ");
			String whereto = scanner.nextLine();
			if(whereto.equals("north") || whereto.equals("south") || whereto.equals("west") || whereto.equals("east")) {
				if(whereto.equals("north"))
					currentroom = map.goNorth();
				else if(whereto.equals("south"))
					currentroom = map.goSouth();
				else if(whereto.equals("east"))
					currentroom = map.goEast();
				else if(whereto.equals("west"))
					currentroom = map.goWest();
			
			}
			System.out.println("NEW CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
		}
	}
	
	
	public void sortMonsters(ArrayList<Monster> monsterlist) {
		for(Monster monster : monsterlist) {
			monster.initiativeRoll();
		}
		for(int i = 0; i<monsterlist.size(); i++) {
			for(int e = 0; e<monsterlist.size(); e++) {
				if(monsterlist.get(i).lastinititativeroll > monsterlist.get(e).lastinititativeroll) {
					Monster helpvar = monsterlist.get(i);
					monsterlist.set(i, monsterlist.get(e));
					monsterlist.set(e, helpvar);
				}
			}
		}
	}

	public static void collectTreasures(ArrayList<Treasure> treasurelist, Hero hero) {
		int treasureSum = 0;
		for(Treasure treasure : treasurelist) {
			treasureSum += treasure.value;
		}
		treasurelist.clear();
		if (treasureSum > 0) {
			hero.treasure += treasureSum;
			System.out.println("\nCollected treasures worth " + treasureSum + " coins.\nYou now have " + hero.treasure + " coins.");
		}
	
	}
	
	public int playerCombatAction(Scanner scanner, Hero hero, ArrayList<Monster> monsterlist) {
		System.out.println("Do you want to [F]lee or [A]ttack");
		String fleeorattack = scanner.nextLine().toLowerCase();
		hero.turntaken = true;
		if(fleeorattack.equals("f")) {
			if(hero.flee()) {
				System.out.println("You fled successfully!");
				//actually put the player in the previous room here
			}
		}
		else if(fleeorattack.equals("a")) {
			System.out.println("Which monster do you want to attack?");
			for(Monster monster : monsterlist) {
				if(!monster.dead) {
					System.out.println(monster.monstertype);
				}
			}
			boolean exists = false;
			boolean attacking = true;
			while(attacking) {
				System.out.print(">> ");
				String attacktarget = scanner.nextLine().toLowerCase();
				String formattedattacktarget = attacktarget.substring(0,1).toUpperCase()+attacktarget.substring(1);
				for(Monster monster : monsterlist) {
					if(monster.monstertype.equals(formattedattacktarget)) {
						exists = true;
					}
				}
				if(exists) {
				for(int i = 0; i < monsterlist.size(); i++) {
					if(monsterlist.get(i).monstertype.equals(formattedattacktarget)) {
						if(hero.attackRoll() > monsterlist.get(i).defendRoll()) {
							int herodmg = hero.dealDamage();
							monsterlist.get(i).takeDamage(herodmg);
							System.out.println("Player hit "+monsterlist.get(i).monstertype+" for "+herodmg);
							if(monsterlist.get(i).dead) {
								System.out.println(monsterlist.get(i).monstertype+" has been slain");
								monsterlist.remove(i);
								return 1;
							}
							break;
						}
						else {
							System.out.println("Player's attack missed");
							break;
						}
					}
				}
				break;
				}
				else {
					System.out.println("Please enter a valid target");
					continue;
				}
			}
		}
		return 0;
	}
	
	public void monsterAttack(Hero hero, Monster monster) {
		int monsterattackroll = monster.attackRoll();
		int herodefendroll = hero.defendRoll();
		//System.out.println("ROLLS MONSTERATTACKROLL "+monsterattackroll+"\nHERO DEFENDROLL "+herodefendroll);
		if(monsterattackroll > herodefendroll) {
			int monsterdmg = monster.dealDamage();
			hero.takeDamage(monsterdmg);
			System.out.println(monster.monstertype+" hit the player for "+monsterdmg);
		}
		else {
			System.out.println(monster.monstertype+" missed!");
		}
	}


}


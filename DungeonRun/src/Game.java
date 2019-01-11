import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		//test commit from Michel
		//test commit from Michel
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
				int monstercount = 0;
				for(Monster monster : currentroom.monsterlist) {
					if(hero.lastinitiativeroll > monster.lastinititativeroll) {
						//player gets option to flee or attack
						//need to check somehwere is player is dead
						System.out.println("Do you want to [F]lee or [A]ttack");
						String fleeorattack = scanner.nextLine().toLowerCase();
						if(fleeorattack.equals("f")) {
							if(hero.flee()) {
								System.out.println("You fled successfully!");
								//actually put the player in the previous room here
								currentroom = map.goLast();
							}
						}
						else if(fleeorattack.equals("a")) {
							if(hero.attackRoll() >= monster.defendRoll()) {
								int herodmg = hero.dealDamage();
								monster.takeDamage(herodmg);
								System.out.println("You hit the monster for "+herodmg);
							}
						}
						//if flee, flee gets rolled and if true, success
						//player gets to choose which monster to attack
						//if attack, attackroll and monster defendrolls, if attackroll > defendroll, player hits the monster
					}
					if(!monster.dead) {
						if(monster.attackRoll() > hero.defendRoll()) {
							hero.takeDamage(monster.dealDamage());
							System.out.println("Monster hit the player!");
						}
						else {
							System.out.println("Monster missed!");
						}
						monstercount++;
						if(monstercount == currentroom.monsterlist.size()) {
							//player has lowest init roll and gets to play here
						}
					}
				}
			}
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


}


import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		//test commit from Michel
		//test commit from Michel
		Map map = new Map();
		Scanner scanner = new Scanner(System.in);
		map.generateMap(4, 4);
		Hero hero = new Hero("Rogue", "myfirstrogue");
		for(Room room : map.room) {
			System.out.println(room.x+" "+room.y);
		}
		Room currentroom = map.startingPoint("SE");
		System.out.println("CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
		while(true) {
			if(currentroom != null) {
				currentroom.monsterlist.add(hero);
				System.out.println("\nMONSTERS");
				for(Object object : currentroom.monsterlist) {
					
					//object is monster
					try {
					Monster monster = ((Monster) object);
					if(monster.player)
						System.out.println("is a player");
					System.out.println(monster.monstertype);
					monster.initiativeRoll();
					System.out.println(monster.lastinititativeroll);
					}
					
					//object is a hero
					
					catch(Exception monsterishero) {
						Hero thehero = ((Hero) object);
						System.out.println(thehero.name);
						thehero.initiativeRoll();
						System.out.println(thehero.lastinitiativeroll);
					}
				}
				//sort list according to initiative roll results, highest first
				int initsort = 0;
				Object monsterorhero;
				for(Object object: currentroom.monsterlist) {
					try {
						Monster monster = ((Monster) object);
					}
					catch(Exception monsterishero) {
						Hero thehero = ((Hero) object);
					}
					for(Object object2: currentroom.monsterlist) {
						try {
							Monster monster2 = ((Monster) object2);
						}
						catch(Exception monsterishero) {
							Hero thehero2 = ((Hero) object2);
						}
					}
				}
				
				System.out.println("\nTREASURES");
				for(Treasure treasure : currentroom.treasurelist) {
					System.out.println(treasure.treasuretype);
				}
				collectTreasures(currentroom.treasurelist, hero);
				
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

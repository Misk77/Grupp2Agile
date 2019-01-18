import java.util.ArrayList;
import java.util.Scanner;

public class Game implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		//Map map = new Map();
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		while(running) {
			int deadmonstercount = 0;
			GameMenu gamemenu = new GameMenu();
			Object [] objects = gamemenu.GameMenuFirst();
			Hero hero = (Hero) objects[1]; //need the correct index
			AiHero aihero = (AiHero)objects[3];
			Map map = (Map) objects[0]; //need the correct index
			String corner = (String) objects[2]; //need the correct index
			//map.generateMap(4, 4);
			Game game = new Game();
			AI ai = new AI();
			//Hero hero = new Hero("Rogue", "myfirstrogue");
			//for(Room room : map.room) {
			//	System.out.println(room.x+" "+room.y);
			//}
			Room currentroom = map.startingPoint(corner);
			map.generateExit();
			map.clearCurrentRoom();
			//System.out.println("CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
			boolean firstround = true;
			map.visitedrooms.clear();
			boolean dungeoning = true;
			while(dungeoning) {
			if(currentroom == null) {
				System.out.println("You tried to walk through a wall, unsuccessfully");
			}
			else {
				//counting rooms
				if(!map.visitedrooms.contains(currentroom)) {
					map.visitedrooms.add(currentroom);
					hero.visitedrooms++;
				}
				//counting rooms
				if(currentroom.monsterlist.isEmpty() && currentroom.treasurelist.isEmpty() && !currentroom.exit && !firstround) {
					System.out.println("There is nothing here...");
				}
				firstround = false;
				if(currentroom.exit) {
					System.out.println("You have found the exit, do you want to leave, [Y]es [N]o");
					String yesorno = null;
					if (hero.ai) {
						yesorno = "y";
					} else {
						yesorno = scanner.nextLine().toLowerCase();
					}
					if(yesorno.equals("y")) {
						System.out.println("You have successfully found your way out of the dungeon");
						System.out.println("You managed to find treasures worth "+hero.treasure+" coins");
						break;
					}
					else if(yesorno.equals("n")) {
						System.out.println("Continuing on...");
					}
					else {
						System.out.println("Please enter a valid option");
					}
					
				}
				if(!currentroom.monsterlist.isEmpty()) {
					System.out.println("You encountered these monsters in the room:");
					for(Monster monster : currentroom.monsterlist) {
						if(!monster.dead)
						System.out.println(monster.monstertype);
					}
				}
				
				//System.out.println("\nTREASURES");
				//for(Treasure treasure : currentroom.treasurelist) {
				//	System.out.println(treasure.treasuretype);
				//}
				
				
				//for(Monster monster : currentroom.monsterlist) {
				//	System.out.println(monster.lastinititativeroll);//this is the shit that gets printed
				//}
				
				game.sortMonsters(currentroom.monsterlist);
				hero.initiativeRoll();
				boolean fighting = true;
				int monstercount = 0;
				fighting = true;
				deadmonstercount = 0;
				
				while(fighting && !currentroom.monsterlist.isEmpty()) {
					monstercount = 0;
					deadmonstercount = 0;
					hero.turntaken = false;
					for(Monster monster : currentroom.monsterlist) {
						if(hero.dead) {
							System.out.println("YOU DIED");
							fighting = false;
							break;
							
						}
						monstercount++;
						if(hero.lastinitiativeroll > monster.lastinititativeroll && !hero.turntaken && !monster.dead && !hero.dead) {
							if(game.playerCombatAction(scanner, hero, currentroom.monsterlist, map, ai).equals("break")) {
								fighting = false;
								break;
							}
						}
						if(!monster.dead) {
							game.monsterAttack(hero, monster);
						}
						else {
							deadmonstercount++;
						}
					}
					if(monstercount == currentroom.monsterlist.size() && !hero.turntaken && deadmonstercount != currentroom.monsterlist.size() && !hero.dead) {
						if(game.playerCombatAction(scanner, hero, currentroom.monsterlist, map, ai).equals("break")) {
							fighting = false;
							break;
						}
					}
					if(deadmonstercount == currentroom.monsterlist.size()) {
						System.out.println("All monsters in the room have been slain");
						currentroom.monsterlist.clear();
						fighting = false;
						break;
					}
					if(hero.dead) {
						System.out.println("YOU DIED");
						dungeoning = false;
						fighting = false;
						break;
					}
				}
				fighting = true;
				//game.collectTreasures(currentroom.treasurelist, hero);
				game.collectTreasures(map, hero);
			}
			if(!hero.dead) {
				System.out.println("What direction?");
				System.out.print(">> ");
				while(true) {
					//count checks
					System.out.println("ROOMS VISITED "+hero.visitedrooms);
					System.out.println("GIANT SPIDERS KILLED "+hero.deadgiantspiders);
					System.out.println("SKELETONS KILLED "+hero.deadskeletons);
					System.out.println("ORCS KILLED "+hero.deadorcs);
					System.out.println("TROLLS KILLED "+hero.deadtrolls);
					//count checks
					String whereto = null;
					if (hero.ai) {
						whereto = ai.chooseDirection(map, hero);
					} else {
						whereto = scanner.nextLine();
					}
					hero.block = true;
					if(whereto.equals("north") || whereto.equals("south") || whereto.equals("west") || whereto.equals("east")) {
						if(whereto.equals("north")) {
							currentroom = map.goNorth();
							break;
						}
						else if(whereto.equals("south")) {
							currentroom = map.goSouth();
							break;
							}
						else if(whereto.equals("east")) {
							currentroom = map.goEast();
							break;
						}
						else if(whereto.equals("west")) {
							currentroom = map.goWest();
							break;
						}
					}
					else {
						System.out.println("Please choose a direction, North, West, South or East\n>> ");
						continue;
					}
			}
			}
			//System.out.println("NEW CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
		}
		//cant test this, supposed to count amount of runs
		hero.adventures++;
		System.out.println("DO WE GET HERE");
		//cant test this, supposed to count amount of runs
		String cont = gamemenu.endMenu(hero);
		if(cont.equals("menu"))
			continue;
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


	public void collectTreasures(Map map, Hero hero) {
	//public void collectTreasures(ArrayList<Treasure> treasurelist, Hero hero) {
		/* Johannes Ã¤ndrat:
		 * Eftersom collectTreasures kallas efter ev. flykt, och game.currentroom inte uppdateras av map.goLast()
		 * sÃ¥ fick man skatterna frÃ¥n ett rum Ã¤ven om man flytt dÃ¤rifrÃ¥n. HÃ¤mtar dÃ¤rfÃ¶r treasurelist via map.currentx/y istÃ¤llet.
		 */
		if(!hero.dead) {
			ArrayList<Treasure> treasurelist = null;
			for(Room room : map.room) {
				if(room.x == map.currentroomx && room.y == map.currentroomy) {
					treasurelist = room.treasurelist;
				}
			}
			if(!treasurelist.isEmpty()) {
				System.out.println("\nYou found these treasures in the room:");
				for(Treasure treasure : treasurelist) {
					System.out.println(treasure.treasuretype);
				}
			}
			int treasureSum = 0;
			for(Treasure treasure : treasurelist) {
				treasureSum += treasure.value;
			}
			treasurelist.clear();
			if (treasureSum > 0) {
				hero.treasure += treasureSum;
				System.out.println("\nCollected treasures worth " + treasureSum + " coins.\nYou now have " + hero.treasure + " coins.\n");
			}
		}
	
	}
	
	public String playerCombatAction(Scanner scanner, Hero hero, ArrayList<Monster> monsterlist, Map map, AI ai) {
		System.out.println("Do you want to [F]lee or [A]ttack?");
		boolean wronginput = true;
		while(wronginput) {
			String fleeorattack = null;
			if (hero.ai) {
				fleeorattack = ai.fightOrFlight(monsterlist, hero);
			} else {
				fleeorattack = scanner.nextLine().toLowerCase();
			}
			hero.turntaken = true;
			if(fleeorattack.equals("f")) {
				wronginput = false;
				if(hero.flee()) {
					System.out.println("You fled back to the previous room successfully!");
					for(Monster monster : monsterlist) {
						monster.resetMonsterHealth();
					}
					map.goLast();
					//System.out.println("NEW CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
					return "break";
				}
				else {
					System.out.println("Your attempt to flee failed");
				}
			}
			else if(fleeorattack.equals("a")) {
				wronginput = false;
				System.out.println("Which monster do you want to attack?");
				for(Monster monster : monsterlist) {
					if(!monster.dead) {
						System.out.println(monster.monstertype);
					}
				}
				boolean exists = false;
				boolean attacking = true;
				while(attacking) {
					exists = false;
					System.out.print(">> ");
					String attacktarget = null;
					if (hero.ai) {
						attacktarget = ai.chooseMonster(monsterlist).toLowerCase();
					} else {
						attacktarget = scanner.nextLine().toLowerCase();
					}
					String formattedattacktarget = attacktarget.substring(0,1).toUpperCase()+attacktarget.substring(1);
					for(Monster monster : monsterlist) {
						if(monster.monstertype.equals(formattedattacktarget) && !monster.dead) {
							exists = true;
						}
					}
					if(exists) {
					for(int i = 0; i < monsterlist.size(); i++) {
						if(monsterlist.get(i).monstertype.equals(formattedattacktarget)) {
							if(hero.attackRoll() > monsterlist.get(i).defendRoll()) {
								int herodmg = hero.dealDamage();
								int monsterhealth = monsterlist.get(i).health;
								monsterlist.get(i).takeDamage(herodmg);
								System.out.println(hero.name+" hit "+monsterlist.get(i).monstertype+" for "+herodmg);
								if(monsterhealth != monsterlist.get(i).health && monsterlist.get(i).health > 0) {
									System.out.println(monsterlist.get(i).monstertype+"'s health is "+monsterlist.get(i).health);
								}
								if(monsterlist.get(i).dead) {
									//counting dead monsters
									if(monsterlist.get(i).monstertype.equals("Giant spider"))
										hero.deadgiantspiders++;
									else if(monsterlist.get(i).monstertype.equals("Skeleton"))
										hero.deadskeletons++;
									else if(monsterlist.get(i).monstertype.equals("Orc"))
										hero.deadorcs++;
									else if(monsterlist.get(i).monstertype.equals("Troll"))
										hero.deadtrolls++;
									//counting dead monsters
									System.out.println(monsterlist.get(i).monstertype+" has been slain");
								}
								break;
							}
							else {
								System.out.println("Player's attack missed");
								break;
							}
						}
					}
					attacking = false;
					break;
					}
					else {
						System.out.println("Please enter a valid target");
						continue;
					}
				}
			}
			else {
				System.out.println("Please enter a valid option");
				continue;
			}
		}
		return "";
	}
	
	public void monsterAttack(Hero hero, Monster monster) {
		int monsterattackroll = monster.attackRoll();
		int herodefendroll = hero.defendRoll();
		//System.out.println("ROLLS MONSTERATTACKROLL "+monsterattackroll+"\nHERO DEFENDROLL "+herodefendroll);
		if(hero.herotype.equals("Knight") && hero.block) {
			hero.takeDamage(monster.dealDamage());
			System.out.println(hero.name+" blocked the "+monster.monstertype+"'s attack!");
		}
		else if(monsterattackroll > herodefendroll) {
			int monsterdmg = monster.dealDamage();
			int herohealth = hero.health;
			hero.takeDamage(monsterdmg);
			System.out.println(monster.monstertype+" hit the player for "+monsterdmg);
			if(hero.health != herohealth) {
				System.out.println(hero.name+"'s health is "+hero.health);
			}
		}
		else {
			System.out.println(monster.monstertype+" missed its attack!");
		}
	}
}


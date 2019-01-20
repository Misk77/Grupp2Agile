import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

public class Game implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int dramaticPause = 500;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		while(running) {
			int deadmonstercount = 0;
			 //Music background This must be here start with MAIN METHOD and then must be in the Guiconsole, so start with also with gui
			PlayMusic playmusic = new  PlayMusic();
			 String music = "/Hypnotic-Puzzle3";
			playmusic.playBackGround(music);
			
			GameMenu gamemenu = new GameMenu();
			Object [] objects = gamemenu.GameMenuFirst();
			Hero hero = (Hero) objects[1]; //need the correct index
			//AiHero aihero = (AiHero)objects[3];
			Map map = (Map) objects[0]; //need the correct index
			//Map.clearScreenWhenEnteringRoom = true;
			String corner = (String) objects[2]; //need the correct index
			Game game = new Game();
			AI ai = new AI();
			Room currentroom = map.startingPoint(corner);
			map.generateExit();
			map.clearCurrentRoom();
			//System.out.println("CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
			boolean firstround = true;
			map.visitedrooms.clear();
			boolean dungeoning = true;
			while(dungeoning) {
			if(currentroom == null) {
				GuiConsole.io.println("You tried to walk through a wall, unsuccessfully",Color.GREEN);
			}
			else {
				//counting rooms
				if(!map.visitedrooms.contains(currentroom)) {
					map.visitedrooms.add(currentroom);
					hero.visitedrooms++;
				}
				//counting rooms
				if(currentroom.monsterlist.isEmpty() && currentroom.treasurelist.isEmpty() && !currentroom.exit && !firstround) {
					GuiConsole.io.println("There is nothing here...");
					AI.deadSteps++;
				}
				firstround = false;
				if(currentroom.exit) {
					GuiConsole.io.println("You have found the exit, do you want to leave, [Y]es [N]o",Color.WHITE);
					String yesorno = null;
					if (hero.ai) {
						yesorno = "y";
					} else {
						yesorno = GuiConsole.io.nextLine().toLowerCase();
					}
					if(yesorno.equals("y")) {
						GuiConsole.io.println("You have successfully found your way out of the dungeon",Color.BLACK);
						GuiConsole.io.println("You managed to find treasures worth "+hero.treasure+" coins",Color.RED);
						break;
					}
					else if(yesorno.equals("n")) {
						GuiConsole.io.println("Continuing on...",Color.YELLOW);
					}
					else {
						GuiConsole.io.println("Please enter a valid option",Color.RED);
					}
					
				}
				if(!currentroom.monsterlist.isEmpty()) {
					GuiConsole.io.println("In the darkness something are waiting.......",Color.RED);
					try {
						Thread.sleep(dramaticPause);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(dramaticPause);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Somethings in the dark starring at you.......");
					try {
						Thread.sleep(dramaticPause);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						Thread.sleep(dramaticPause);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					GuiConsole.io.println("You encountered these monsters in the room:",Color.RED);
					for(Monster monster : currentroom.monsterlist) {
						if(!monster.dead)
							GuiConsole.io.println(monster.monstertype,Color.WHITE);
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
							GuiConsole.io.println("YOU DIED",Color.RED);
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
						GuiConsole.io.println("All monsters in the room have been slain",Color.RED);
						currentroom.monsterlist.clear();
						fighting = false;
						break;
					}
					if(hero.dead) {
						GuiConsole.io.println("YOU DIED",Color.RED);
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
				GuiConsole.io.println("What direction?",Color.RED);
				GuiConsole.io.print(">> ");
				while(true) {
					//count checks
					/*
					GuiConsole.io.println("ROOMS VISITED "+hero.visitedrooms,Color.RED);
					GuiConsole.io.println("GIANT SPIDERS KILLED "+hero.deadgiantspiders,Color.MAGENTA);
					GuiConsole.io.println("SKELETONS KILLED "+hero.deadskeletons,Color.YELLOW);
					GuiConsole.io.println("ORCS KILLED "+hero.deadorcs,Color.BLUE);
					GuiConsole.io.println("TROLLS KILLED "+hero.deadtrolls,Color.BLACK);
					*/
					//count checks
					String whereto = null;
					if (hero.ai) {
						whereto = ai.chooseDirection(map, hero);
					} else {
						whereto = GuiConsole.io.nextLine();
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
						GuiConsole.io.print("Please choose a direction, North, West, South or East\n>> ",Color.WHITE);
						continue;
					}
			}
			}
			//System.out.println("NEW CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
		}
		//cant test this, supposed to count amount of runs
		hero.adventures++;
		GuiConsole.io.println("DO WE GET HERE",Color.RED);
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
		if(!hero.dead) {
			ArrayList<Treasure> treasurelist = null;
			for(Room room : map.room) {
				if(room.x == map.currentroomx && room.y == map.currentroomy) {
					treasurelist = room.treasurelist;
				}
			}
			if(!treasurelist.isEmpty()) {
				GuiConsole.io.println("\nYou found these treasures in the room:",Color.RED);
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
				GuiConsole.io.println("\nCollected treasures worth " + treasureSum + " coins.\nYou now have " + hero.treasure + " coins.\n",Color.WHITE);
			}
		}
	}
	
	public String playerCombatAction(Scanner scanner, Hero hero, ArrayList<Monster> monsterlist, Map map, AI ai) {
		GuiConsole.io.println("Do you want to [F]lee or [A]ttack?",Color.RED);
		boolean wronginput = true;
		while(wronginput) {
			String fleeorattack = null;
			GuiConsole.io.print(">> ");
			if (hero.ai) {
				fleeorattack = ai.fightOrFlight(monsterlist, hero);
			} else {
				fleeorattack = GuiConsole.io.nextLine().toLowerCase();
			}
			//allseeingeye
			/*
			hero.turntaken = true;
			if(fleeorattack.equals("seeing")) {
				 GuiConsole.io.println("╔══════════════════════════════════════════════════════════════════╗\n",Color.RED);
				 GuiConsole.io.println(hero.name + "see a gliming " + treasure.treasuretype + "behind the " +monster.monstertype,Color.RED);
				 GuiConsole.io.println("╚══════════════════════════════════════════════════════════════════╝\n",Color.RED);
					return "break";
				}
				*/
			hero.turntaken = true;
			if(fleeorattack.equals("f")) {
				wronginput = false;
				if(hero.flee()) {
					
					for(Monster monster : monsterlist) {
						monster.resetMonsterHealth();
					}
					map.goLast();
					GuiConsole.io.println("You fled back to the previous room successfully!",Color.GREEN);
					AI.deadSteps++;
					//System.out.println("NEW CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
					return "break";
				}
				else {
					GuiConsole.io.println("Your attempt to flee failed",Color.RED);
				}
			}
			else if(fleeorattack.equals("a")) {
				wronginput = false;
				GuiConsole.io.println("Which monster do you want to attack?",Color.YELLOW);
				for(Monster monster : monsterlist) {
					if(!monster.dead) {
						GuiConsole.io.println(monster.monstertype,Color.WHITE);
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
						attacktarget =  GuiConsole.io.nextLine().toLowerCase();
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
								GuiConsole.io.println(hero.name+" hit "+monsterlist.get(i).monstertype+" for "+herodmg,Color.RED);
								if(monsterhealth != monsterlist.get(i).health && monsterlist.get(i).health > 0) {
									GuiConsole.io.println(monsterlist.get(i).monstertype+"'s health is "+monsterlist.get(i).health,Color.GREEN);
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
									GuiConsole.io.println(monsterlist.get(i).monstertype+" has been slain",Color.CYAN);
									ai.monsterSlain();
								}
								break;
							}
							else {
								GuiConsole.io.println("Player's attack missed",Color.RED);
								break;
							}
						}
					}
					attacking = false;
					break;
					}
					else {
						GuiConsole.io.println("Please enter a valid target",Color.ORANGE);
						continue;
					}
				}
			}
			else {
				GuiConsole.io.println("Please enter a valid option",Color.RED);
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
			GuiConsole.io.println(hero.name+" blocked the "+monster.monstertype+"'s attack!",Color.WHITE);
		}
		else if(monsterattackroll > herodefendroll) {
			int monsterdmg = monster.dealDamage();
			int herohealth = hero.health;
			hero.takeDamage(monsterdmg);
			GuiConsole.io.println(monster.monstertype+" hit the player for "+monsterdmg,Color.RED);
			if(hero.health != herohealth) {
				GuiConsole.io.println(hero.name+"'s health is "+hero.health,Color.WHITE);
			}
		}
		else {
			GuiConsole.io.println(monster.monstertype+" missed its attack!",Color.RED);
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}


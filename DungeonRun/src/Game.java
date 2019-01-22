import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static int dramaticPause = 500;

	public static void main(String[] args) {
		/*
		 * System.out.println("WH"); Hero herotest = new Hero("Wizard", "anotherchar");
		 * SaveLoad sl = new SaveLoad(); sl.printall(); sl.save(herotest); String [] ar
		 * = sl.load(herotest); for(int i = 0; i<ar.length; i++) {
		 * System.out.println(ar[i]); }
		 */
		// savetests
		Scanner scanner = new Scanner(System.in);

		new PlayMusic();

		boolean running = true;
		while (running) {

			int deadmonstercount = 0;
			// Music background This must be here start with MAIN METHOD and then must be in
			// the Guiconsole, so start with also with gui
			// PlayMusic playmusic = new PlayMusic();

			// String backgroundmusic ="/ExternalItems/Hypnotic-Puzzle3";
			// playmusic.playBackGround(backgroundmusic);

			GameMenu gamemenu = new GameMenu();

			Object[] objects = null;
			try {
				objects = gamemenu.GameMenuFirst();
			} catch (ClassNotFoundException e) {
				GuiConsole.io.print(e);
			}
			Hero hero = (Hero) objects[1]; // need the correct index
			// AiHero aihero = (AiHero)objects[3];
			Map map = (Map) objects[0]; // need the correct index
			// Map.clearScreenWhenEnteringRoom = true; // Testa gï¿½rna denna och sï¿½g vad
			// ni tycker! Cleanare enligt mig. /Johannes
			String corner = (String) objects[2];
			Game game = new Game();
			AI ai = new AI();
			Room currentroom = map.startingPoint(corner);
			map.generateExit();
			map.clearCurrentRoom();
			// System.out.println("CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
			boolean firstround = true;
			map.visitedrooms.clear();
			boolean dungeoning = true;
			while (dungeoning) {
				if (currentroom == null) {
					GuiConsole.io.print("You tried to walk through a");
					GuiConsole.io.print(" wall ", Color.GRAY);
					GuiConsole.io.println("unsuccessfully");
				} else {
					// counting rooms
					if (!map.visitedrooms.contains(currentroom)) {
						map.visitedrooms.add(currentroom);
						hero.visitedrooms++;
					}
					// counting rooms
					if (currentroom.monsterlist.isEmpty() && currentroom.treasurelist.isEmpty() && !currentroom.exit
							&& !firstround) {
						GuiConsole.io.print("There is");
						GuiConsole.io.print(" nothing ", Color.gray);
						GuiConsole.io.println("here...");
						AI.deadSteps++;

					}
					firstround = false;
					if (currentroom.exit) {
						GuiConsole.io.print("You have found the");
						GuiConsole.io.print(" exit", Color.green);
						// GuiConsole.io.println(", do you want to leave, [Y]es [N]o");
						// Pillade dit lite fÃ¤rg, Ã¤ndra tillbaka om det inte var sÃ¥ du tÃ¤nkt! /J
						GuiConsole.io.print(", do you want to leave? [");
						GuiConsole.io.print("Y", Color.green);
						GuiConsole.io.print("]");
						GuiConsole.io.print("es", Color.green);
						GuiConsole.io.print(" [");
						GuiConsole.io.print("N", Color.red);
						GuiConsole.io.print("]");
						GuiConsole.io.print("o\n", Color.red);
						GuiConsole.io.print(">> ");

						String yesorno = null;
						if (hero.ai) {
							yesorno = "y";
							GuiConsole.io.print("y\n");
						} else {
							yesorno = GuiConsole.io.nextLine().toLowerCase();
						}
						if (yesorno.equals("y")) {
							GuiConsole.io.print("\nYou have");
							GuiConsole.io.print(" successfully ", Color.green);
							GuiConsole.io.println("found your way out of the dungeon");
							// GuiConsole.io.print("You managed to find treasures worth "+hero.treasure+"
							// coins");
							// GuiConsole.io.print(" treasure ",Color.orange);
							// GuiConsole.io.print("worth "+hero.treasure+" coins");
							break;
						} else if (yesorno.equals("n")) {
							GuiConsole.io.println("Continuing on...");
						} else {
							GuiConsole.io.println("Please enter a valid option");
						}

					}
					if (!currentroom.monsterlist.isEmpty()) {
						/*
						 * GuiConsole.io.println("In the darkness something are waiting.......",Color.
						 * RED); try { Thread.sleep(dramaticPause); } catch (InterruptedException e) {
						 * // TODO Auto-generated catch block e.printStackTrace(); } try {
						 * Thread.sleep(dramaticPause); } catch (InterruptedException e) { // TODO
						 * Auto-generated catch block e.printStackTrace(); }
						 * System.out.println("Somethings in the dark starring at you......."); try {
						 * Thread.sleep(dramaticPause); } catch (InterruptedException e) { // TODO
						 * Auto-generated catch block e.printStackTrace(); }
						 * 
						 * try { Thread.sleep(dramaticPause); } catch (InterruptedException e) { // TODO
						 * Auto-generated catch block e.printStackTrace(); }
						 */
						GuiConsole.io.println("You encountered these monsters in the room:");
						for (Monster monster : currentroom.monsterlist) {
							if (!monster.dead)
								GuiConsole.io.println(monster.monstertype, Color.orange.darker());
						}
						GuiConsole.io.println();
					}

					// System.out.println("\nTREASURES");
					// for(Treasure treasure : currentroom.treasurelist) {
					// System.out.println(treasure.treasuretype);
					// }

					// for(Monster monster : currentroom.monsterlist) {
					// System.out.println(monster.lastinititativeroll);//this is the shit that gets
					// printed
					// }

					game.sortMonsters(currentroom.monsterlist);
					hero.initiativeRoll();
					boolean fighting = true;
					int monstercount = 0;
					fighting = true;
					deadmonstercount = 0;

					while (fighting && !currentroom.monsterlist.isEmpty()) {
						monstercount = 0;
						deadmonstercount = 0;
						hero.turntaken = false;
						for (Monster monster : currentroom.monsterlist) {
							if (hero.dead) {
								GuiConsole.io.println("YOU DIED", Color.RED);
								fighting = false;
								break;

							}
							monstercount++;
							if (hero.lastinitiativeroll > monster.lastinititativeroll && !hero.turntaken
									&& !monster.dead && !hero.dead) {
								if (game.playerCombatAction(scanner, hero, currentroom.monsterlist, map, ai)
										.equals("break")) {
									fighting = false;
									break;
								}
							}
							if (!monster.dead) {
								game.monsterAttack(hero, monster);
							} else {
								deadmonstercount++;
							}
						}
						if (monstercount == currentroom.monsterlist.size() && !hero.turntaken
								&& deadmonstercount != currentroom.monsterlist.size() && !hero.dead) {
							if (game.playerCombatAction(scanner, hero, currentroom.monsterlist, map, ai)
									.equals("break")) {
								fighting = false;
								break;
							}
						}
						if (deadmonstercount == currentroom.monsterlist.size()) {
							GuiConsole.io.print("All");
							GuiConsole.io.print(" monsters ", Color.orange.darker());
							GuiConsole.io.print("in the room have been ");
							GuiConsole.io.println("slain\n", Color.white);
							currentroom.monsterlist.clear();
							fighting = false;
							break;
						}
						if (hero.dead) {
							GuiConsole.io.println("YOU DIED", Color.RED);
							dungeoning = false;
							fighting = false;
							break;
						}
					}
					fighting = true;
					// game.collectTreasures(currentroom.treasurelist, hero);
					game.collectTreasures(map, hero);
				}
				if (!hero.dead) {
					GuiConsole.io.print("What ");
					GuiConsole.io.print("direction", Color.white);
					GuiConsole.io.println("?");
					GuiConsole.io.print(">> ");
					while (true) {
						// count checks
						/*
						 * GuiConsole.io.println("ROOMS VISITED "+hero.visitedrooms,Color.RED);
						 * GuiConsole.io.println("GIANT SPIDERS KILLED "+hero.deadgiantspiders,Color.
						 * MAGENTA);
						 * GuiConsole.io.println("SKELETONS KILLED "+hero.deadskeletons,Color.YELLOW);
						 * GuiConsole.io.println("ORCS KILLED "+hero.deadorcs,Color.BLUE);
						 * GuiConsole.io.println("TROLLS KILLED "+hero.deadtrolls,Color.BLACK);
						 */
						// count checks
						String whereto = null;
						if (hero.ai) {
							whereto = ai.chooseDirection(map, hero);
						} else {
							whereto = GuiConsole.io.nextLine().toLowerCase();
						}
						hero.block = true;
						if (whereto.equals("north") || whereto.equals("south") || whereto.equals("west")
								|| whereto.equals("east") || whereto.equals("n") || whereto.equals("s")
								|| whereto.equals("w") || whereto.equals("e")) {
							if (whereto.equals("north") || whereto.equals("n")) {
								currentroom = map.goNorth();
								break;
							} else if (whereto.equals("south") || whereto.equals("s")) {
								currentroom = map.goSouth();
								break;
							} else if (whereto.equals("east") || whereto.equals("e")) {
								currentroom = map.goEast();
								break;
							} else if (whereto.equals("west") || whereto.equals("w")) {
								currentroom = map.goWest();
								break;
							}
						} else {
							GuiConsole.io.print("Please choose a direction, North, West, South or East\n>> ",
									Color.WHITE);
							continue;
						}
					}
				}
				// System.out.println("NEW CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
			}
			// cant test this, supposed to count amount of runs
			hero.adventures++;
			// counts players adventures but not ai adventures, ?????????????????
			// GuiConsole.io.println("DO WE GET HERE",Color.RED);
			// cant test this, supposed to count amount of runs
			String cont = gamemenu.endMenu(hero);
			if (cont.equals("menu"))
				continue;
		}
	}

	public void sortMonsters(ArrayList<Monster> monsterlist) {
		for (Monster monster : monsterlist) {
			monster.initiativeRoll();
		}
		for (int i = 0; i < monsterlist.size(); i++) {
			for (int e = 0; e < monsterlist.size(); e++) {
				if (monsterlist.get(i).lastinititativeroll > monsterlist.get(e).lastinititativeroll) {
					Monster helpvar = monsterlist.get(i);
					monsterlist.set(i, monsterlist.get(e));
					monsterlist.set(e, helpvar);
				}
			}
		}
	}

	public void collectTreasures(Map map, Hero hero) {
		if (!hero.dead) {
			ArrayList<Treasure> treasurelist = null;
			for (Room room : map.room) {
				if (room.x == map.currentroomx && room.y == map.currentroomy) {
					treasurelist = room.treasurelist;
				}
			}
			if (!treasurelist.isEmpty()) {
				GuiConsole.io.print("\nYou found these");
				GuiConsole.io.print(" treasures ", Color.orange);
				GuiConsole.io.println("in the room:");
				for (Treasure treasure : treasurelist) {
					GuiConsole.io.println(treasure.treasuretype, Color.orange);
				}
			}
			int treasureSum = 0;
			for (Treasure treasure : treasurelist) {
				treasureSum += treasure.value;
			}
			treasurelist.clear();
			if (treasureSum > 0) {
				hero.treasure += treasureSum;
				GuiConsole.io.print("\nCollected");
				GuiConsole.io.print(" treasures ", Color.orange);
				GuiConsole.io.print("worth ");
				GuiConsole.io.print(treasureSum, Color.white);
				GuiConsole.io.print(" coins.\nYou now have ");
				GuiConsole.io.print(hero.treasure, Color.white);
				GuiConsole.io.println(" coins.\n");
			}
		}
	}

	public String playerCombatAction(Scanner scanner, Hero hero, ArrayList<Monster> monsterlist, Map map, AI ai) {
		GuiConsole.io.print("Do you want to");
		GuiConsole.io.print(" [");
		GuiConsole.io.print("F", Color.white);
		GuiConsole.io.print("]");
		GuiConsole.io.print("lee", Color.white);
		// GuiConsole.io.print(" [F]lee ", Color.white);
		GuiConsole.io.print(" or ");
		GuiConsole.io.print("[");
		GuiConsole.io.print("A", Color.red);
		GuiConsole.io.print("]");
		GuiConsole.io.print("ttack", Color.red);
		// GuiConsole.io.print("[A]ttack", Color.red);
		GuiConsole.io.println("?");
		boolean wronginput = true;
		while (wronginput) {
			String fleeorattack = null;
			GuiConsole.io.print(">> ");
			if (hero.ai) {
				fleeorattack = ai.fightOrFlight(monsterlist, hero);
			} else {
				fleeorattack = GuiConsole.io.nextLine().toLowerCase();
			}
			// allseeingeye
			/*
			 * hero.turntaken = true; if(fleeorattack.equals("seeing")) {
			 * GuiConsole.io.println(
			 * "â•”â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•—\n"
			 * ,Color.RED); GuiConsole.io.println(hero.name + "see a gliming " +
			 * treasure.treasuretype + "behind the " +monster.monstertype,Color.RED);
			 * GuiConsole.io.println(
			 * "â•šâ•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�\n"
			 * ,Color.RED); return "break"; }
			 */
			hero.turntaken = true;
			if (fleeorattack.equals("f")) {
				wronginput = false;
				if (hero.flee()) {

					for (Monster monster : monsterlist) {
						monster.resetMonsterHealth();
					}
					map.goLast();
					GuiConsole.io.print("You");
					GuiConsole.io.print(" fled ", Color.white);
					GuiConsole.io.print("back to the previous room ");
					GuiConsole.io.println("successfully", Color.green);
					AI.deadSteps++;
					// System.out.println("NEW CURRENTROOM "+map.currentroomx+" "+map.currentroomy);
					return "break";
				} else {
					GuiConsole.io.print("Your attempt to");
					GuiConsole.io.print(" flee ", Color.white);
					GuiConsole.io.print("back to the previous room");
					GuiConsole.io.println(" failed", Color.red);
				}
			} else if (fleeorattack.equals("a")) {
				wronginput = false;
				GuiConsole.io.print("Which");
				GuiConsole.io.print(" monster ", Color.orange.darker());
				GuiConsole.io.print("do you want to");
				GuiConsole.io.print(" attack", Color.red);
				GuiConsole.io.println("?");
				for (Monster monster : monsterlist) {
					if (!monster.dead) {
						// GuiConsole.io.println(monster.monstertype,Color.orange.darker());
						// Ã„ndra tillbaka till ovanstÃ¥ende om ni tycker det blir fult! /J
						GuiConsole.io.print("[");
						GuiConsole.io.print(monster.monstertype.substring(0, 1), Color.orange.darker());
						GuiConsole.io.print("]");
						GuiConsole.io.print(monster.monstertype.substring(1), Color.orange.darker());
						GuiConsole.io.println();
					}
				}
				GuiConsole.io.println();
				boolean exists = false;
				boolean attacking = true;
				while (attacking) {
					exists = false;
					System.out.print(">> ");
					String attacktarget = null;
					if (hero.ai) {
						attacktarget = ai.chooseMonster(monsterlist).toLowerCase();
					} else {
						String input = GuiConsole.io.nextLine().toLowerCase();
						switch (input) {
						case "g":
							attacktarget = "giant spider";
							break;
						case "s":
							attacktarget = "skeleton";
							break;
						case "t":
							attacktarget = "troll";
							break;
						case "o":
							attacktarget = "orc";
							break;
						default:
							attacktarget = input.toLowerCase();
						}
					}
					String formattedattacktarget = attacktarget.substring(0, 1).toUpperCase()
							+ attacktarget.substring(1);
					for (Monster monster : monsterlist) {
						if (monster.monstertype.equals(formattedattacktarget) && !monster.dead) {
							exists = true;
						}
					}
					if (exists) {
						for (int i = 0; i < monsterlist.size(); i++) {
							if (monsterlist.get(i).monstertype.equals(formattedattacktarget)) {
								if (hero.attackRoll() > monsterlist.get(i).defendRoll()) {
									int herodmg = hero.dealDamage();
									int monsterhealth = monsterlist.get(i).health;
									monsterlist.get(i).takeDamage(herodmg);
									GuiConsole.io.print(hero.name, Color.cyan);
									GuiConsole.io.print(" hit ");
									GuiConsole.io.print(monsterlist.get(i).monstertype, Color.ORANGE.darker());
									GuiConsole.io.print(" for ");
									GuiConsole.io.println(herodmg, Color.white);
									if (monsterhealth != monsterlist.get(i).health && monsterlist.get(i).health > 0) {
										GuiConsole.io.print(monsterlist.get(i).monstertype + "'s",
												Color.orange.darker());
										GuiConsole.io.print(" health ");
										GuiConsole.io.print("is ");
										GuiConsole.io.println(monsterlist.get(i).health, Color.white);
									}
									if (monsterlist.get(i).dead) {
										// counting dead monsters
										if (monsterlist.get(i).monstertype.equals("Giant spider"))
											hero.deadgiantspiders++;
										else if (monsterlist.get(i).monstertype.equals("Skeleton"))
											hero.deadskeletons++;
										else if (monsterlist.get(i).monstertype.equals("Orc"))
											hero.deadorcs++;
										else if (monsterlist.get(i).monstertype.equals("Troll"))
											hero.deadtrolls++;
										// counting dead monsters
										GuiConsole.io.print(monsterlist.get(i).monstertype, Color.orange.darker());
										GuiConsole.io.print(" has been ");
										GuiConsole.io.println("slain", Color.white);
										ai.monsterSlain();
									}
									break;
								} else {
									GuiConsole.io.print("Player's attack");
									GuiConsole.io.println(" missed", Color.white);
									break;
								}
							}
						}
						attacking = false;
						break;
					} else {
						GuiConsole.io.println("Please enter a valid target");
						continue;
					}
				}
			} else {
				GuiConsole.io.println("Please enter a valid option");
				continue;
			}
		}
		return "";
	}

	public void monsterAttack(Hero hero, Monster monster) {
		int monsterattackroll = monster.attackRoll();
		int herodefendroll = hero.defendRoll();
		// System.out.println("ROLLS MONSTERATTACKROLL "+monsterattackroll+"\nHERO
		// DEFENDROLL "+herodefendroll);
		if (hero.herotype.equals("Knight") && hero.block) {
			hero.takeDamage(monster.dealDamage());
			GuiConsole.io.print(hero.name, Color.cyan);
			GuiConsole.io.print(" blocked the ");
			GuiConsole.io.print(monster.monstertype + "'s ", Color.orange.darker());
			GuiConsole.io.println("attack!");
		} else if (monsterattackroll > herodefendroll) {
			int monsterdmg = monster.dealDamage();
			int herohealth = hero.health;
			hero.takeDamage(monsterdmg);
			GuiConsole.io.print(monster.monstertype, Color.orange.darker());
			GuiConsole.io.print(" hit ");
			GuiConsole.io.print(hero.name, Color.cyan);
			GuiConsole.io.print(" for ");
			GuiConsole.io.println(monsterdmg, Color.white);
			if (hero.health != herohealth) {
				GuiConsole.io.print(hero.name, Color.cyan);
				GuiConsole.io.print(" health is ");
				GuiConsole.io.println(hero.health, Color.white);
			}
		} else {
			GuiConsole.io.print("the ");
			GuiConsole.io.print(monster.monstertype, Color.orange.darker());
			GuiConsole.io.print(" strikes at you, but you");
			GuiConsole.io.print(" dodge ", Color.white);
			GuiConsole.io.println("just in time");

		}
	}

	public void run() {

		// TODO Auto-generated method stub

	}
}

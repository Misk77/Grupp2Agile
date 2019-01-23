
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Map implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static boolean clearScreenWhenEnteringRoom = false;
	ArrayList<Room> room = new ArrayList<Room>();
	ArrayList<Room> visitedrooms = new ArrayList<Room>();
	int lastroomvisitedx; // coordinates for last room
	int lastroomvisitedy;

	int currentroomx;
	int currentroomy;

	int sizex;
	int sizey;

	Random rand;
	PlayMusic playmusic = new PlayMusic();
	
	public static String theme = "blue";
	
	// Added som new colors & SimpleAttributeSets.
	Color dark_blue = new Color(0, 0, 153);
	Color darker_blue = new Color(0, 0, 80);
	Color very_dark_blue = new Color(0, 0, 35);
	Color dark_red = new Color(153, 0, 0);
	Color darker_red = new Color(90, 0, 0);
	Color very_dark_red = new Color(60, 0, 0);
	Color orangered = new Color(225, 60, 0);
	Color dark_green = new Color(0, 102, 0);
	Color darker_green = new Color(0, 75, 0);
	Color very_dark_green = new Color(0, 50, 0);
	
	char buildBlock = '█'; // The map is drawn with this character. Can be anything.
	char heroBlock = '×'; // This character shows where the hero is.
	char healthBlock = '¤';

	SimpleAttributeSet unExplored = new SimpleAttributeSet();
	SimpleAttributeSet emptySpaceCurrentRoom = new SimpleAttributeSet();
	SimpleAttributeSet emptySpaceOtherRoom = new SimpleAttributeSet();
	SimpleAttributeSet mapFrame = new SimpleAttributeSet();
	SimpleAttributeSet heroFrameText = new SimpleAttributeSet();
	SimpleAttributeSet bgColor = new SimpleAttributeSet();
	SimpleAttributeSet monsterFrameText = new SimpleAttributeSet();
	SimpleAttributeSet exitCurrentRoom = new SimpleAttributeSet();
	SimpleAttributeSet exitOtherRoom = new SimpleAttributeSet();
	SimpleAttributeSet monsterCurrentRoom = new SimpleAttributeSet();
	SimpleAttributeSet monsterOtherRoom = new SimpleAttributeSet();
	SimpleAttributeSet theHero = new SimpleAttributeSet();
	




	public Map() {
		rand = new Random();
		//String creepydungeon = "/ExternalItems/creepydungeon";

		//playmusic.playBackGround(creepydungeon);
	}

	public void generateMap(int x, int y) {
		for (int h = 0; h < x; h++) {
			// "x" coordinates
			for (int v = 0; v < y; v++) {
				Room oneroom = new Room();
				oneroom.x = h;
				oneroom.y = v;
				room.add(oneroom);
			}
		}
		this.sizex = x - 1;
		this.sizey = y - 1;
	}

	public Room goNorth(Hero hero) {
		for (Room room : this.room) {
			if (room.x == currentroomx - 1 && room.y == currentroomy) {
				this.lastroomvisitedx = currentroomx;
				this.lastroomvisitedy = currentroomy;
				this.currentroomx = room.x;
				room.visited = true;
				drawMap(false, hero);
				return room;
			}
		}
		return null;
	}

	public Room goSouth(Hero hero) {
		for (Room room : this.room) {
			if (room.x == currentroomx + 1 && room.y == currentroomy) {
				this.lastroomvisitedx = currentroomx;
				this.lastroomvisitedy = currentroomy;
				this.currentroomx = room.x;
				room.visited = true;
				drawMap(false, hero);
				return room;
			}
		}
		return null;
	}

	public Room goEast(Hero hero) {
		for (Room room : this.room) {
			if (room.x == currentroomx && room.y == currentroomy + 1) {
				this.lastroomvisitedx = currentroomx;
				this.lastroomvisitedy = currentroomy;
				this.currentroomy = room.y;
				room.visited = true;
				drawMap(false, hero);
				return room;
			}
		}
		return null;

	}

	public Room goWest(Hero hero) {
		for (Room room : this.room) {
			if (room.x == currentroomx && room.y == currentroomy - 1) {
				this.lastroomvisitedx = currentroomx;
				this.lastroomvisitedy = currentroomy;
				this.currentroomy = room.y;
				room.visited = true;
				drawMap(false, hero);
				return room;
			}
		}
		return null;
	}

	public Room goLast(Hero hero) {
		for (Room room : this.room) {
			if (room.x == lastroomvisitedx && room.y == lastroomvisitedy) {
				this.currentroomx = lastroomvisitedx;
				this.currentroomy = lastroomvisitedy;
				room.visited = true;
				drawMap(false, hero);
				return room;
			}
		}
		return null;
	}

	public Room startingPoint(String corner, Hero hero) {

		if (corner.equals("NW")) {
			this.currentroomx = 0;
			this.currentroomy = 0;
		} else if (corner.equals("NE")) {
			this.currentroomx = 0;
			this.currentroomy = this.sizey;
		} else if (corner.equals("SW")) {
			this.currentroomx = this.sizex;
			this.currentroomy = 0;
		} else if (corner.equals("SE")) {
			this.currentroomx = this.sizex;
			this.currentroomy = this.sizey;
		}
		for (Room room : this.room) {
			if (room.x == currentroomx && room.y == currentroomy) {
				room.visited = true;
				drawMap(true, hero);
				return room;
			}
		}

		return null;
	}

	public void generateExit() {
		while (true) {
			int randroom = rand.nextInt(room.size() - 1);
			if (this.room.get(randroom).x != this.currentroomx && this.room.get(randroom).y != this.currentroomy) {
				this.room.get(randroom).exit = true;
				this.room.get(randroom).monsterlist.clear();
				this.room.get(randroom).treasurelist.clear();
				break;
			}
		}
	}

	public void clearCurrentRoom() {
		for (Room room : this.room) {

			if (room.x == currentroomx && room.y == currentroomy) {
				room.monsterlist.clear();
				room.treasurelist.clear();
			}

		}
	}

	public void drawMap(boolean atStart, Hero hero) {
	
		//if (hero.ai) {clearScreenWhenEnteringRoom = true;}
		boolean showMap = true;

		// Theme is set with Map.theme (public static String)
		// ATM you get a red map by choosing NE or SE corners and blue for the others.
		if (theme.equals("blue")) {
			StyleConstants.setForeground(unExplored, very_dark_blue);
			StyleConstants.setForeground(emptySpaceCurrentRoom, Color.blue);
			StyleConstants.setForeground(emptySpaceOtherRoom, dark_blue);
			StyleConstants.setForeground(mapFrame, darker_blue);
			StyleConstants.setForeground(heroFrameText, Color.orange);
			StyleConstants.setBackground(heroFrameText, darker_blue);
			StyleConstants.setForeground(monsterFrameText, dark_red);
			StyleConstants.setBackground(monsterFrameText, darker_blue);
			StyleConstants.setForeground(bgColor, Color.black);
			StyleConstants.setForeground(exitCurrentRoom, Color.green);
			StyleConstants.setBackground(exitCurrentRoom, Color.blue);
			StyleConstants.setForeground(exitOtherRoom, dark_green);
			StyleConstants.setBackground(exitOtherRoom, dark_blue);
			StyleConstants.setForeground(monsterCurrentRoom, dark_red);
			StyleConstants.setBackground(monsterCurrentRoom, Color.blue);
			StyleConstants.setForeground(monsterOtherRoom, very_dark_red);
			StyleConstants.setBackground(monsterOtherRoom, dark_blue);
			StyleConstants.setForeground(theHero, Color.orange);
			StyleConstants.setBackground(theHero, Color.blue);
		}
		if (theme.equals("red")) {
			StyleConstants.setForeground(unExplored, very_dark_red);
			StyleConstants.setForeground(emptySpaceCurrentRoom, orangered);
			StyleConstants.setForeground(emptySpaceOtherRoom, dark_red);
			StyleConstants.setForeground(mapFrame, darker_red);
			StyleConstants.setForeground(exitCurrentRoom, Color.yellow);
			StyleConstants.setBackground(exitCurrentRoom, orangered);
			StyleConstants.setForeground(exitOtherRoom, dark_green);
			StyleConstants.setBackground(exitOtherRoom, dark_red);
			StyleConstants.setForeground(monsterCurrentRoom, Color.green);
			StyleConstants.setBackground(monsterCurrentRoom, orangered);
			StyleConstants.setForeground(monsterOtherRoom, very_dark_green);
			StyleConstants.setBackground(monsterOtherRoom, dark_red);
			StyleConstants.setForeground(theHero, Color.yellow);
			StyleConstants.setBackground(theHero, orangered);
		}
		
		int heroBoxWidth = 30;
		String[] part = new String[2];
		part[0] = "";
		part[1] = "";
		
		ArrayList<Monster> monsterlist = new ArrayList<Monster>();
		for (Room room : this.room) {
			if (room.x == this.currentroomx && room.y == this.currentroomy) {
				for (Monster monster : room.monsterlist) {
					if (!monster.dead) {
						if (!atStart) {
							monsterlist.add(monster);
						}
					}
				}
			}
		}


		if (clearScreenWhenEnteringRoom) {
			GuiConsole.io.clear();
		}
		if (showMap) {
			// Upper map frame begin
			GuiConsole.io.print("\n" + buildBlock, mapFrame);
			for (int i = sizex; i >= 0; i--) {
				for (int x = 5; x > 0; x--) {
					GuiConsole.io.print(buildBlock, mapFrame);
				}
			}
			GuiConsole.io.print(buildBlock, mapFrame);
			// Upper map frame end
			
			GuiConsole.io.print(buildBlock, bgColor);
			
			// Upper herobox frame begin
			for (int x = ((heroBoxWidth/2)-2); x > 0; x--) {
				GuiConsole.io.print(buildBlock, mapFrame);
			}
			
			GuiConsole.io.print("HERO", heroFrameText);
			
			for (int x = ((heroBoxWidth/2)-2); x > 0; x--) {
				GuiConsole.io.print(buildBlock , mapFrame);
			}
			// Upper herobox frame end
			
			GuiConsole.io.print(buildBlock, bgColor);
			
			// Upper monsterbox frame begin
			for (int x = ((heroBoxWidth/2)-4); x > 0; x--) {
				GuiConsole.io.print(buildBlock, mapFrame);
			}

			GuiConsole.io.print("MONSTERS", monsterFrameText);

			for (int x = ((heroBoxWidth/2)-4); x > 0; x--) {
				GuiConsole.io.print(buildBlock, mapFrame);
			}
			// Upper monsterbox frame end
			
			
			GuiConsole.io.println();

			for (int x = 0; x <= sizex; x++) {
				
				for (int y = 0; y <= sizex; y++) {
					for (Room room : this.room) {
						if (room.x == x && room.y == y) {
							if (room.visited) {

								if (room.x == currentroomx && room.y == currentroomy) {
									part[0] += "--"+ heroBlock + "--";
								} else {
									part[0] += "*****";
								}

								if (room.monsterlist.isEmpty() || atStart) {
									if (room.exit) {
										part[1] += "EXIT*";
									} else {
										part[1] += "*****";
									}
								} else {
									int space = 5;
									for (Monster monster : room.monsterlist) {
										if (!monster.dead) {
											part[1] += monster.monstertype.substring(0, 1);
											space -= 1;
										}
									}
									for (int i = space; i > 0; i--) {
										part[1] += "*";
									}
								}
							} else {
								part[0] += "#####";
								part[1] += "#####";
							}
						}
					}
				} // Y
				GuiConsole.io.print(buildBlock, mapFrame);


				// Print "part[1]", the upper line:
				for (int i = 0, n = part[1].length(); i < n; i++) {
					char c1 = part[1].charAt(i);
					char c0 = part[0].charAt(i);
					boolean thisRoom = false;
					if (c0 == heroBlock || c0 == '-') {
						thisRoom = true;
					}
					if (c1 == '*') {
						if (thisRoom) {
							GuiConsole.io.print(buildBlock, emptySpaceCurrentRoom);
						} else {
							GuiConsole.io.print(buildBlock, emptySpaceOtherRoom);
						}
					} else if (c1 == '#') {
						GuiConsole.io.print(buildBlock, unExplored);
					} else if (c1 == 'E') {
						if (thisRoom) {
							GuiConsole.io.print("E", exitCurrentRoom);
						} else {
							GuiConsole.io.print("E", exitOtherRoom);
						}
					} else if (c1 == 'X') {
						if (thisRoom) {
							GuiConsole.io.print("X", exitCurrentRoom);
						} else {
							GuiConsole.io.print("X", exitOtherRoom);
						}
					} else if (c1 == 'I') {
						if (thisRoom) {
							GuiConsole.io.print("I", exitCurrentRoom);
						} else {
							GuiConsole.io.print("I", exitOtherRoom);
						}
					} else if (c1 == 'T') { // If the T is not part of 'EXIT', it's a Troll.
						if (i > 0 && part[1].charAt(i - 1) == 'I') {
							if (thisRoom) {
								GuiConsole.io.print("T", exitCurrentRoom);
							} else {
								GuiConsole.io.print("T", exitOtherRoom);
							}
							// Monsters:
						} else {
							if (thisRoom) {
								GuiConsole.io.print("T", monsterCurrentRoom);
							} else {
								GuiConsole.io.print("T", monsterOtherRoom);
							}
						}
					} else if (c1 == 'G') {
						if (thisRoom) {
							GuiConsole.io.print("G", monsterCurrentRoom);
						} else {
							GuiConsole.io.print("G", monsterOtherRoom);
						}
					} else if (c1 == 'S') {
						if (thisRoom) {
							GuiConsole.io.print("S", monsterCurrentRoom);
						} else {
							GuiConsole.io.print("S", monsterOtherRoom);
						}
					} else if (c1 == 'O') {
						if (thisRoom) {
							GuiConsole.io.print("O", monsterCurrentRoom);
						} else {
							GuiConsole.io.print("O", monsterOtherRoom);
						}
					}
				}
				
				//Tomma rader i boxarna:
				GuiConsole.io.print(buildBlock, mapFrame); // högervägg map
				GuiConsole.io.print(buildBlock, bgColor); // mellan map och herobox
				GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg herobox
				for (int i=28; i>0; i--) {GuiConsole.io.print(buildBlock, bgColor);} // Tomt space i herobox
				GuiConsole.io.print(buildBlock, mapFrame); // högervägg herobox
				GuiConsole.io.print(buildBlock, bgColor); // mellan herobox och monsterbox
				GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg monsterbox
				for (int i=28; i>0; i--) {GuiConsole.io.print(buildBlock, bgColor);} // Tomt space i monsterbox
				GuiConsole.io.print(buildBlock, mapFrame); // högervägg monsterbox
				
				GuiConsole.io.println();
				GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg map

				// Print "part[0]", the lower line:
				for (int i = 0, n = part[0].length(); i < n; i++) {
					char c0 = part[0].charAt(i);
					if (c0 == '#') {
						GuiConsole.io.print(buildBlock, unExplored);
					} else if (c0 == '-') {
						GuiConsole.io.print(buildBlock, emptySpaceCurrentRoom);
					} else if (c0 == heroBlock) {
						GuiConsole.io.print(heroBlock, theHero);
					} else if (c0 == '*') {
						GuiConsole.io.print(buildBlock, emptySpaceOtherRoom);
					}
				}
				GuiConsole.io.print(buildBlock, mapFrame);

				if (x == 0) {
					GuiConsole.io.print(buildBlock, bgColor); // Mellan map och herobox
					//Hero box
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg herobox
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					GuiConsole.io.print("Name:");
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					GuiConsole.io.print(hero.name, Color.orange); // Hero name
					for (int z = (21-hero.name.length()); z>0; z--) {
						GuiConsole.io.print(buildBlock, bgColor); // Mellanrum efter hero name
					}
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg herobox
					GuiConsole.io.print(buildBlock, bgColor); // Mellan herobox och monsterbox
					//Monster box
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg monsterbox
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					if (monsterlist.size() > 0) {
						Monster monster = monsterlist.get(0);
						GuiConsole.io.print(monster.monstertype, Color.orange.darker());
						GuiConsole.io.print(buildBlock, bgColor);
						for (int z = (monster.health); z>0; z--) {
							GuiConsole.io.print(healthBlock, Color.red);
						}
						for (int z = (monster.maxHealth-monster.health); z>0; z--) {
							GuiConsole.io.print(healthBlock, Color.DARK_GRAY);
						}

						for (int z = (26-(monster.monstertype.length() + monster.maxHealth)); z>0; z--) { 
							GuiConsole.io.print(buildBlock, bgColor); // Mellanrum efter monster name
						}
					} else {
						for (int i=27; i>0; i--) {GuiConsole.io.print(buildBlock, bgColor);} // Tomt space i monsterbox
					}
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg monsterbox
					
				} 
				
				else if (x == 1) {
					GuiConsole.io.print(buildBlock, bgColor); // Mellan map och herobox
					//Hero box
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg herobox
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					GuiConsole.io.print("Class:");
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					GuiConsole.io.print(hero.herotype, Color.cyan);
					for (int z = (20-hero.herotype.length()); z>0; z--) {
						GuiConsole.io.print(buildBlock, bgColor); // Mellanrum efter hero type
					}
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg herobox
					
					GuiConsole.io.print(buildBlock, bgColor); // Mellan herobox och monsterbox
					//Monster box
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg monsterbox
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					if (monsterlist.size() > 1) {
						Monster monster = monsterlist.get(1);
						GuiConsole.io.print(monster.monstertype, Color.orange.darker());
						GuiConsole.io.print(buildBlock, bgColor);
						for (int z = (monster.health); z>0; z--) {
							GuiConsole.io.print(healthBlock, Color.red);
						}
						for (int z = (monster.maxHealth-monster.health); z>0; z--) {
							GuiConsole.io.print(healthBlock, Color.DARK_GRAY);
						}

						for (int z = (26-(monster.monstertype.length() + monster.maxHealth)); z>0; z--) { 
							GuiConsole.io.print(buildBlock, bgColor); // Mellanrum efter monster name
						}
					} else {
						for (int i=27; i>0; i--) {GuiConsole.io.print(buildBlock, bgColor);} // Tomt space i monsterbox
					}
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg monsterbox
				} 
				
				else if (x == 2) {
					GuiConsole.io.print(buildBlock, bgColor); // Mellan map och herobox
					//Hero box
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg herobox
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					GuiConsole.io.print("Health:");
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					for (int z = (hero.health); z>0; z--) {
						GuiConsole.io.print(healthBlock, Color.green);
					}
					for (int z = (hero.maxHealth-hero.health); z>0; z--) {
						GuiConsole.io.print(healthBlock, Color.DARK_GRAY);
					}
					
					for (int z = (19 - hero.maxHealth); z>0; z--) {
						GuiConsole.io.print(buildBlock, bgColor); // Mellanrum efter hero health
					}
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg herobox
					
					GuiConsole.io.print(buildBlock, bgColor); // Mellan herobox och monsterbox
					//Monster box
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg monsterbox
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					if (monsterlist.size() > 2) {
						Monster monster = monsterlist.get(2);
						GuiConsole.io.print(monster.monstertype, Color.orange.darker());
						GuiConsole.io.print(buildBlock, bgColor);
						for (int z = (monster.health); z>0; z--) {
							GuiConsole.io.print(healthBlock, Color.red);
						}
						for (int z = (monster.maxHealth-monster.health); z>0; z--) {
							GuiConsole.io.print(healthBlock, Color.DARK_GRAY);
						}
						for (int z = (26-(monster.monstertype.length() + monster.maxHealth)); z>0; z--) { 
							GuiConsole.io.print(buildBlock, bgColor); // Mellanrum efter monster name
						}
					} else {
						for (int i=27; i>0; i--) {GuiConsole.io.print(buildBlock, bgColor);} // Tomt space i monsterbox
					}
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg monsterbox
				} 
				else if (x == 3) {
					GuiConsole.io.print(buildBlock, bgColor); // Mellan map och herobox
					//Hero box
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg herobox
					for (int i=28; i>0; i--) {GuiConsole.io.print(buildBlock, bgColor);} // Tomt space i herobox
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg herobox
					GuiConsole.io.print(buildBlock, bgColor); // Mellan herobox och monsterbox
					//Monster box
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg monsterbox
					GuiConsole.io.print(buildBlock, bgColor); // mellanrum
					if (monsterlist.size() > 3) {
						Monster monster = monsterlist.get(3);
						GuiConsole.io.print(monster.monstertype, Color.orange.darker());
						GuiConsole.io.print(buildBlock, bgColor);
						for (int z = (monster.health); z>0; z--) {
							GuiConsole.io.print(healthBlock, Color.red);
						}
						for (int z = (monster.maxHealth-monster.health); z>0; z--) {
							GuiConsole.io.print(healthBlock, Color.DARK_GRAY);
						}
						for (int z = (26-(monster.monstertype.length() + monster.maxHealth)); z>0; z--) { 
							GuiConsole.io.print(buildBlock, bgColor); // Mellanrum efter monster name
						}
					} else {
						for (int i=27; i>0; i--) {GuiConsole.io.print(buildBlock, bgColor);} // Tomt space i monsterbox
					}
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg monsterbox
				} 
				else if (x > 3) {
					GuiConsole.io.print(buildBlock, bgColor); // Mellan map och herobox
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg herobox
					for (int i=28; i>0; i--) {GuiConsole.io.print(buildBlock, bgColor);} // Tomt space i herobox
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg herobox
					GuiConsole.io.print(buildBlock, bgColor); // Mellan herobox och monsterbox
					GuiConsole.io.print(buildBlock, mapFrame); // vänstervägg monsterbox
					for (int i=28; i>0; i--) {GuiConsole.io.print(buildBlock, bgColor);} // Tomt space i monsterbox
					GuiConsole.io.print(buildBlock, mapFrame); // högervägg monsterbox
				}
				GuiConsole.io.println();
				
				part[0] = "";
				part[1] = "";
			} // X
			
			// Bottom frame map:
			GuiConsole.io.print(buildBlock, mapFrame);
			for (int i = sizex; i >= 0; i--) {
				for (int x = 5; x > 0; x--) {
					GuiConsole.io.print(buildBlock, mapFrame);
				}
			}
			GuiConsole.io.print(buildBlock, mapFrame);
			GuiConsole.io.print(buildBlock, bgColor); // Space mellan map och herobox
			// Bottom frame herobox:
			for (int x = heroBoxWidth; x > 0; x--) {
				GuiConsole.io.print(buildBlock, mapFrame);
			}
			GuiConsole.io.print(buildBlock, bgColor); // Space mellan herobox och monsterbox
			// Bottom frame monsterbox:
			for (int x = heroBoxWidth; x > 0; x--) {
				GuiConsole.io.print(buildBlock, mapFrame);
			}
			
			GuiConsole.io.print("\n\n\n");
		}

		// System.out.println("deadSteps: " + AI.deadSteps);
	}
}


import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Map implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static boolean clearScreenWhenEnteringRoom = false;
	ArrayList<Room> room = new ArrayList<Room>();
	ArrayList<Room> visitedrooms = new ArrayList<Room>();
	int lastroomvisitedx; //coordinates for last room
	int lastroomvisitedy;

	int currentroomx;
	int currentroomy;

	int sizex;
	int sizey;

	Random rand;

	public Map() {
		rand = new Random();
	}

	public void generateMap(int x, int y) {
		for(int h = 0; h < x; h++) {
			//"x" coordinates
			for(int v = 0; v < y; v++) {
				Room oneroom = new Room();
				oneroom.x = h;
				oneroom.y = v;
				room.add(oneroom);
			}
		}
		this.sizex = x-1;
		this.sizey = y-1;
	}

	public Room goNorth() {
		for(Room room : this.room) {
			if(room.x == currentroomx-1 && room.y == currentroomy) {
				this.lastroomvisitedx = currentroomx;
				this.lastroomvisitedy = currentroomy;
				this.currentroomx = room.x;
				room.visited = true;
				drawMap(false);
				return room;
			}
		}
		return null;
	}

	public Room goSouth() {
		for(Room room : this.room) {
			if(room.x == currentroomx+1 && room.y == currentroomy) {
				this.lastroomvisitedx = currentroomx;
				this.lastroomvisitedy = currentroomy;
				this.currentroomx = room.x;
				room.visited = true;
				drawMap(false);
				return room;
			}
		}
		return null;
	}

	public Room goEast() {
		for(Room room : this.room) {
			if(room.x == currentroomx && room.y == currentroomy+1) {
				this.lastroomvisitedx = currentroomx;
				this.lastroomvisitedy = currentroomy;
				this.currentroomy = room.y;
				room.visited = true;
				drawMap(false);
				return room;
			}
		}
		return null;

	}

	public Room goWest() {
		for(Room room : this.room) {
			if(room.x == currentroomx && room.y == currentroomy-1) {
				this.lastroomvisitedx = currentroomx;
				this.lastroomvisitedy = currentroomy;
				this.currentroomy = room.y;
				room.visited = true;
				drawMap(false);
				return room;
			}
		}
		return null;
	}

	public Room goLast() {
		for(Room room : this.room) {
			if(room.x == lastroomvisitedx && room.y == lastroomvisitedy) {
				this.currentroomx = lastroomvisitedx;
				this.currentroomy = lastroomvisitedy;
				room.visited = true;
				drawMap(false);
				return room;
			}
		}
		return null;
	}

	public Room startingPoint(String corner) {
		if(corner.equals("NW")) {
			this.currentroomx = 0;
			this.currentroomy = 0;
		}
		else if(corner.equals("NE")) {
			this.currentroomx = 0;
			this.currentroomy = this.sizey;
		}
		else if(corner.equals("SW")) {
			this.currentroomx = this.sizex;
			this.currentroomy = 0;
		}
		else if(corner.equals("SE")) {
			this.currentroomx = this.sizex;
			this.currentroomy = this.sizey;
		}
		for(Room room : this.room) {
			if(room.x == currentroomx && room.y == currentroomy) {
				room.visited = true;
				drawMap(true);
				return room;
			}
		}
		return null;
	}

	public void generateExit() {
		while(true) {
			int randroom = rand.nextInt(room.size()-1);
			if(this.room.get(randroom).x != this.currentroomx && this.room.get(randroom).y != this.currentroomy) {
				this.room.get(randroom).exit = true;
				this.room.get(randroom).monsterlist.clear();
				this.room.get(randroom).treasurelist.clear();
				break;
			}
		}
	}
	public void clearCurrentRoom() {
		for(Room room : this.room) {
			if(room.x == currentroomx && room.y == currentroomy) {
				room.monsterlist.clear();
				room.treasurelist.clear();
			}
		}
	}

	public void drawMap(boolean atStart) {
		boolean showMap = true;
		
		// Added som new colors & SimpleAttributeSets. 
		Color dark_blue = new Color(0,0,153);
		Color very_dark_blue = new Color(0,0,35);
		Color darkest_blue = new Color(0,0,80);
		Color dark_red = new Color(153,0,0);
		Color very_dark_red = new Color(60,0,0);
		Color dark_green = new Color(0,102,0);
		char buildBlock = '█'; // The map is drawn with this character. Can be anything.
		char heroBlock = '×'; // This character shows where the hero is.
		
		SimpleAttributeSet unExplored = new SimpleAttributeSet();
		SimpleAttributeSet emptySpaceCurrentRoom = new SimpleAttributeSet();
		SimpleAttributeSet emptySpaceOtherRoom = new SimpleAttributeSet();
		SimpleAttributeSet mapFrame = new SimpleAttributeSet();
		SimpleAttributeSet exitCurrentRoom = new SimpleAttributeSet();
		SimpleAttributeSet exitOtherRoom = new SimpleAttributeSet();
		SimpleAttributeSet monsterCurrentRoom = new SimpleAttributeSet();
		SimpleAttributeSet monsterOtherRoom = new SimpleAttributeSet();
		SimpleAttributeSet hero = new SimpleAttributeSet();
		
		StyleConstants.setForeground(unExplored, very_dark_blue);
		StyleConstants.setForeground(emptySpaceCurrentRoom, Color.blue);
		StyleConstants.setForeground(emptySpaceOtherRoom, dark_blue);
		StyleConstants.setForeground(mapFrame, darkest_blue);
		StyleConstants.setForeground(exitCurrentRoom, Color.green);
		StyleConstants.setBackground(exitCurrentRoom, Color.blue);
		StyleConstants.setForeground(exitOtherRoom, dark_green);
		StyleConstants.setBackground(exitOtherRoom, dark_blue);
		StyleConstants.setForeground(monsterCurrentRoom, dark_red);
		StyleConstants.setBackground(monsterCurrentRoom, Color.blue);
		StyleConstants.setForeground(monsterOtherRoom, very_dark_red);
		StyleConstants.setBackground(monsterOtherRoom, dark_blue);
		StyleConstants.setForeground(hero, Color.orange);
		StyleConstants.setBackground(hero, Color.blue);
		
		String[] part = new String[2];
		part[0] = "";
		part[1] = "";
		
		if (clearScreenWhenEnteringRoom) {
			GuiConsole.io.clear();
		}
		if (showMap) {
			GuiConsole.io.print("\n"+ buildBlock, mapFrame);
			for (int i = sizex; i>=0; i--) {
				for (int x = 5; x>0; x--) {
					GuiConsole.io.print(buildBlock,mapFrame);
				}
			}
			GuiConsole.io.print(buildBlock + "\n", mapFrame);
			for(int x = 0; x<=sizex; x++) {
				for(int y = 0; y<=sizex; y++) {
					for(Room room : this.room) {
						if(room.x == x && room.y == y) {
							if (room.visited) {
								
								if(room.x == currentroomx && room.y == currentroomy) {
									part[0] += "--×--";
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
											part[1] += monster.monstertype.substring(0,1);
											space -= 1;
										}
									}
									for (int i = space; i>0; i--) {
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
				for(int i = 0, n = part[1].length() ; i < n ; i++) { 
				    char c1 = part[1].charAt(i);
				    char c0 = part[0].charAt(i);
				    boolean thisRoom = false;
					if (c0 == heroBlock || c0 == '-') {
						thisRoom = true;
					}
					if(c1 == '*') {
						if (thisRoom) {
							GuiConsole.io.print(buildBlock, emptySpaceCurrentRoom);
						} else {
							GuiConsole.io.print(buildBlock, emptySpaceOtherRoom);
						}
					}
					else if(c1 == '#') {
						GuiConsole.io.print(buildBlock, unExplored);
					}
					else if(c1 == 'E') {
						if (thisRoom) {
							GuiConsole.io.print("E", exitCurrentRoom);
						} else {
							GuiConsole.io.print("E", exitOtherRoom);
						}
					} else if(c1 == 'X') {	
						if (thisRoom) {
							GuiConsole.io.print("X", exitCurrentRoom);
						} else {
							GuiConsole.io.print("X", exitOtherRoom);
						}
					} else if(c1 == 'I') {	
						if (thisRoom) {
							GuiConsole.io.print("I", exitCurrentRoom);
						} else {
							GuiConsole.io.print("I", exitOtherRoom);
						}
					} else if(c1 == 'T') {	// If the T is not part of 'EXIT', it's a Troll.
						if (i > 0 && part[1].charAt(i-1) == 'I') {
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
					} else if(c1 == 'G') {
						if (thisRoom) {
							GuiConsole.io.print("G", monsterCurrentRoom);
						} else {
							GuiConsole.io.print("G", monsterOtherRoom);
						}
					} else if(c1 == 'S') {	
						if (thisRoom) {
							GuiConsole.io.print("S", monsterCurrentRoom);
						} else {
							GuiConsole.io.print("S", monsterOtherRoom);
						}
					} else if(c1 == 'O') {
						if (thisRoom) {
							GuiConsole.io.print("O", monsterCurrentRoom);
						} else {
							GuiConsole.io.print("O", monsterOtherRoom);
						}
					}
				}
				GuiConsole.io.print(buildBlock + "\n",mapFrame);
				GuiConsole.io.print(buildBlock,mapFrame);
	
				// Print "part[0]", the lower line:
				for(int i = 0, n = part[0].length() ; i < n ; i++) { 
					char c0 = part[0].charAt(i);
					if(c0 == '#') {
						GuiConsole.io.print(buildBlock,unExplored);
					}
					else if(c0 == '-') {
						GuiConsole.io.print(buildBlock,emptySpaceCurrentRoom);
					}
					else if(c0 == heroBlock) {
						GuiConsole.io.print(heroBlock, hero);
					}
					else if(c0 == '*') {
						GuiConsole.io.print(buildBlock,emptySpaceOtherRoom);
					}
				}
				GuiConsole.io.print(buildBlock + "\n",mapFrame);
				part[0] = "";
				part[1] = "";
			} // X
			GuiConsole.io.print(buildBlock,mapFrame);
			for (int i = sizex; i>=0; i--) {
				for (int x = 5; x>0; x--) {
					GuiConsole.io.print(buildBlock,mapFrame);
				}
			}
			GuiConsole.io.print(buildBlock + "\n\n",mapFrame);
		}
		
		//System.out.println("deadSteps: " + AI.deadSteps);
	}
}

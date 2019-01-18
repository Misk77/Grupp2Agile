import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Map {
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
		// atStart hindrar monster från att visas i första rummet, då det inte clearats ännu.
		boolean showMap = true;
		String[] part = new String[2];
		part[0] = "";
		part[1] = "";
		String heroHere;
		// The following line presents a new room much cleaner, at the cost of being able to scroll up.
		if (clearScreenWhenEnteringRoom) {
			GuiConsole.io.clear();
		}
		if (showMap) {
			GuiConsole.io.print("\n█",Color.ORANGE);
			for (int i = sizex; i>=0; i--) {
				GuiConsole.io.print("█████",Color.ORANGE);
			}
			GuiConsole.io.print("█\n",Color.ORANGE);
			for(int x = 0; x<=sizex; x++) {
				for(int y = 0; y<=sizex; y++) {
					for(Room room : this.room) {
						if(room.x == x && room.y == y) {
							if (room.visited) {
								heroHere = " ";
								if(room.x == currentroomx && room.y == currentroomy) {
									heroHere = "×";
								}
								part[0] += "  " + heroHere + "  ";
								if (room.monsterlist.isEmpty() || atStart) {
									if (room.exit) {
										part[1] += "EXIT ";
									} else {
										part[1] += "     ";
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
										part[1] += " ";
									}
								}
							} else {
								part[0] += "░░░░░";
								part[1] += "░░░░░";
							}
						}
					}
				} // Y
				GuiConsole.io.print("█",Color.ORANGE);
				GuiConsole.io.print(part[1],Color.ORANGE);
				GuiConsole.io.print("█\n",Color.ORANGE);
				GuiConsole.io.print("█",Color.ORANGE);
				GuiConsole.io.print(part[0],Color.ORANGE);
				GuiConsole.io.print("█\n",Color.ORANGE);
				part[0] = "";
				part[1] = "";
			} // X
			GuiConsole.io.print("█",Color.ORANGE);
			for (int i = sizex; i>=0; i--) {
				GuiConsole.io.print("█████",Color.ORANGE);
			}
			GuiConsole.io.print("█\n\n",Color.ORANGE);
		}
		
		//System.out.println("deadSteps: " + AI.deadSteps);
	}
}

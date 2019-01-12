import java.util.ArrayList;

public class Map {
	ArrayList<Room> room = new ArrayList<Room>();
	int lastroomvisitedx; //coordinates for last room
	int lastroomvisitedy;
	
	int currentroomx;
	int currentroomy;
	
	int sizex;
	int sizey;
	
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
				return room;
			}
		}
		return null;
	}
}

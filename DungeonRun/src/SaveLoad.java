import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SaveLoad implements  java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String heroName;
	public int adventures;
	public int visitedrooms;
	public int deadgiantspiders;
	public int deadskeletons;
	public int deadorcs;
	public int deadtrolls;
	public int treasure;
	
	

	

	
	public SaveLoad(String endMenu) {
		// TODO Auto-generated constructor stub
	}

	public static void save(Serializable data,String filename )throws Exception {
		try(ObjectOutputStream DungensSave = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))){
			DungensSave.writeObject(data);
		}
		}//ENDS SAVE
		
	public static Object load(String filename )throws Exception{
		try (ObjectInputStream DungensLoad = new ObjectInputStream(Files.newInputStream((Paths.get(filename))))){
		return DungensLoad.readObject();
	}
	
	}
	
	
	
	
	

	}

	
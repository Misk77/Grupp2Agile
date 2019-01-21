import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResouceMananger {

	public static void save(SaveLoad data,String filename )throws Exception {
		try(ObjectOutputStream DungensSave = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))){
			DungensSave.writeObject(data);
		}
		}//ENDS SAVE
		
	public static Object load(String filename )throws Exception{
		try (ObjectInputStream DungensLoad = new ObjectInputStream(Files.newInputStream((Paths.get(filename))))){
		return DungensLoad.readObject();
	}
	
	}
	
	
	
	
	}// ENDS resourceManager
	


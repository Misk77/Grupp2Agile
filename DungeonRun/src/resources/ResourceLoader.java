package resources;

import java.io.InputStream;

public class ResourceLoader {

	public static InputStream Load(String path) {
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		if (input==null) {
			input = ResourceLoader.class.getResourceAsStream(path);
		}
		return input;
	
	}
}

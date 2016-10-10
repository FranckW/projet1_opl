package spoon;

import java.io.IOException;

public class Pwd {
	
	private static String getPath() throws IOException{
		 String current = new java.io.File( "." ).getCanonicalPath();
		 return current;
	}
	
	public static String getRepositoryPath() throws IOException {
		return getChoosenPath("\\repository");
	}
	
	public static String getChoosenPath(String path) throws IOException{
		String choosedPath = Pwd.getPath() + "\\" + path;
		return choosedPath;
	}
	
}

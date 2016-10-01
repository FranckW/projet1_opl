package spoon;

import java.io.IOException;

public class Pwd {
	
	private static String getPath() throws IOException{
		 String current = new java.io.File( "." ).getCanonicalPath();
		 return current;
	}
	
	public static String getRepositoryPath() throws IOException {
		String repoPath = Pwd.getPath() + "\\repository";
		return repoPath;
	}

	public static String getOutputPath() throws IOException {
		String outputPath = Pwd.getPath() + "\\output";
		return outputPath;
	} 
	
}

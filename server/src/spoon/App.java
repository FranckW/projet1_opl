package spoon;

import java.io.IOException;

public class App {
	
    public static void main(String[] args) throws IOException {
    	
        final Launcher launcher = new Launcher();
        final String repositoryPath = Pwd.getRepositoryPath();
        final String outputDirectory = Pwd.getOutputPath();
        
        launcher.addInputResource(repositoryPath);        
        launcher.setSourceOutputDirectory(outputDirectory);
        
        //Here add the processors which analyse the source code
        launcher.addProcessor(new ClassProcessor());
        launcher.run();
    
        ClassRanking rank = ClassProcessor.getRank();
        System.out.println(rank.toString());
        
    }
    
}

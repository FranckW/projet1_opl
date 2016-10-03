package spoon;

import java.io.IOException;

public class App {
	
    public static void main(String[] args) throws IOException {
    	
        final Launcher launcher = new Launcher();
        final String repositoryPath = Pwd.getRepositoryPath();
        final String outputDirectory = Pwd.getOutputPath();
        
        launcher.addInputResource(repositoryPath);        
        launcher.setSourceOutputDirectory(outputDirectory);
        
        launcher.addProcessor(new ClassProcessor());
        launcher.run();
    
        
        System.out.println("Before analyse : ");
        ClassRanking rank = ClassProcessor.getRank();
        System.out.println(rank.toString());
        
        System.out.println("After analyse : ");
        ClassProcessor.extendsAnalyse();
        ClassProcessor.mainClassAnalyse();
        ClassProcessor.godClassAnalyse();
        System.out.println(rank.toString());
	
    }
    
}

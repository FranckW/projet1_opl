package spoon;

import java.io.IOException;

import servlet.RankingList;

public class App {
	
    public static void main(String[] args) throws IOException {
    	
        final Launcher launcher = new Launcher();
        final String repositoryPath = Pwd.getRepositoryPath();
        final String outputDirectory = Pwd.getOutputPath();
        
        launcher.addInputResource(repositoryPath);        
        launcher.setSourceOutputDirectory(outputDirectory);
        
        RankingProcessor rankProcessor = new RankingProcessor();
        
        launcher.addProcessor(rankProcessor);
        launcher.run();
        
        
        System.out.println("Before analyse : ");
        ClassRanking rank = rankProcessor.getRanking();
        RankingList.addClassRanking(rank);
        System.out.println(rank.toString());
        
        System.out.println("After analyse : ");
        rankProcessor.analyse();
        System.out.println(rank.toString());

	
    }
    
}

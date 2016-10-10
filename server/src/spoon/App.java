package spoon;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {


    	long debut = System.currentTimeMillis();
        final Launcher launcher = new Launcher();
        final String repositoryPath = Pwd.getRepositoryPath();

        final List<String> arguments = new LinkedList<String>();
        arguments.add("-i");
        arguments.add(repositoryPath);
        arguments.add("-p");
        arguments.add(ClassProcessor.class.getName());
        arguments.add("-x");

        launcher.run(arguments.toArray(new String[arguments.size()]));

//        launcher.addInputResource(repositoryPath);
//        launcher.setSourceOutputDirectory(outputDirectory);
//
//        launcher.addProcessor(new ClassProcessor());
//        launcher.run();


        System.out.println("Before analyse : ");
        ClassRanking rank = ClassProcessor.getRank();
        System.out.println(rank.toString());

        System.out.println("After analyse : ");
        ClassProcessor.analyse();
        System.out.println(rank.toString());

        System.out.println(System.currentTimeMillis() - debut + "ms");
    }

}

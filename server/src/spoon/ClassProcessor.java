package spoon;

import java.util.LinkedList;
import java.util.List;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class ClassProcessor extends AbstractProcessor<CtClass<?>> {
	
	private static ClassRanking rank = new ClassRanking();
	private static List<String> classList = new LinkedList();
	final static int EXTENDS_POINTS = 5;
	
	@Override
	public boolean isToBeProcessed(CtClass<?> candidate) {
		ClassRanking.addClass(candidate.getSimpleName());
		//return candidate.getSimpleName().startsWith("V");
		//We want all the classes
		return true;
	}

	public static ClassRanking getRank(){
		return rank;
	}
	
	//add all the java class of the repository in the list
	public void process(CtClass<?> arg0) {
		classList.add(arg0.toString());
	}
	
	public static void extendsAnalyse(){
		String parentClass;

		for(String classElement : classList){
			if(classElement.contains("extends")){
				parentClass = getParentClass(classElement);
				ClassRanking.addPoints(parentClass, EXTENDS_POINTS);
			}
		}
	}
	
	/**
	 * Get the name of the class after the first extends
	 * @param ctClass
	 * @return
	 */
	private static String getParentClass(String ctClass){
		String[] extendsSplitter;
		String extendsPart;
		String[] splitFinal;
		extendsSplitter = ctClass.split("extends ");
		extendsPart = extendsSplitter[1];
		splitFinal = extendsPart.split(" ");
		return splitFinal[0];
	}
	
}

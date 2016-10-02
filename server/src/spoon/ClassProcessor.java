package spoon;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class ClassProcessor extends AbstractProcessor<CtClass<?>> {
	
	private static ClassRanking rank = new ClassRanking();
	private static List<CtClass<?>> classList = new LinkedList<CtClass<?>>();
	final static int EXTENDS_POINTS = 5;
	final static int MAINCLASS_POINTS = 5;
	
	@Override
	public boolean isToBeProcessed(CtClass<?> candidate) {
		ClassRanking.addClass(candidate);
		//return candidate.getSimpleName().startsWith("V");
		//We want all the classes
		return true;
	}

	public static ClassRanking getRank(){
		return rank;
	}
	
	//add all the java class of the repository in the list
	public void process(CtClass<?> arg0) {
		classList.add(arg0);
		Set<CtMethod<?>> methods = arg0.getMethods();
		for(CtMethod<?> ctmethod : methods){
			System.out.println("test : " + ctmethod.getSimpleName());
		}
	}
	
	/**
	 * Get the name of the parent class of java class if it exist and give points to it
	 */
	public static void extendsAnalyse(){
		
		String parentClass;
		
		for(CtClass<?> classElement : classList){
			if(classElement.getSuperclass() != null){
				parentClass = classElement.getSuperclass().toString();
				ClassRanking.addPoints(parentClass, EXTENDS_POINTS);
			}
		}
	}
	
	public static void mainClassAnalyse(){
		Set<CtMethod<?>> methods;
		
		for(CtClass<?> classElement : classList){
			
				methods = classElement.getMethods();
				for(CtMethod<?> ctmethod : methods){
					if(ctmethod.getSimpleName().toString().equals("main")){
						System.out.println("found main class in : " + classElement.getSimpleName().toString());
						ClassRanking.addPoints(classElement.getSimpleName(), MAINCLASS_POINTS);

					}
				}
		}
	}
	
}

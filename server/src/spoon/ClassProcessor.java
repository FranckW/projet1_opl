package spoon;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class ClassProcessor extends AbstractProcessor<CtClass<?>> {
	
	private static ClassRanking rank = new ClassRanking();
	
	@Override
	public boolean isToBeProcessed(CtClass<?> candidate) {
		ClassRanking.addClass(candidate.getSimpleName());
		return candidate.getSimpleName().startsWith("V");
	}

	public static ClassRanking getRank(){
		return rank;
	}
	
	public void process(CtClass<?> arg0) {
		System.out.println(arg0);
	}

}

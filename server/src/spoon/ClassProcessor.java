package spoon;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class ClassProcessor extends AbstractProcessor<CtClass<?>> {
	
	@Override
	public boolean isToBeProcessed(CtClass<?> candidate) {
		return candidate.getSimpleName().endsWith(".java");
	}

	public void process(CtClass<?> arg0) {
		System.out.println(arg0);
	}

}

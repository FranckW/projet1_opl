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
	final static int GODCLASS_POINTS = 5;
	final static int IMPLEMENTS_POINTS = 2;

	@Override
	public boolean isToBeProcessed(CtClass<?> candidate) {
		ClassRanking.addClass(candidate);
		return true;
	}

	public static ClassRanking getRank() {
		return rank;
	}

	@Override
	public void process(CtClass<?> arg0) {
		classList.add(arg0);
	}

	public static void analyse() {
		for (CtClass<?> classElement : classList) {

			ClassProcessor.extendsAnalyse(classElement);
			ClassProcessor.mainClassAnalyse(classElement);
			ClassProcessor.godClassAnalyse(classElement);
			ClassProcessor.methodNumbersAnalyse(classElement);
			ClassProcessor.attributeNumbersAnalyse(classElement);
			ClassProcessor.implementsAnalyse(classElement);

		}
	}

	/**
	 * Get the name of the parent class if it exist and give points to it
	 */
	public static void extendsAnalyse(CtClass<?> classElement) {

		String parentClass;
		if (classElement.getSuperclass() != null) {
			parentClass = classElement.getSuperclass().getSimpleName();
			ClassRanking.addPoints(parentClass, EXTENDS_POINTS);

		}
	}

	public static void implementsAnalyse(CtClass<?> classElement) {
		ClassRanking.addPoints(classElement, classElement.getSuperInterfaces().size() * IMPLEMENTS_POINTS);

	}

	/**
	 * If a main remains, add points
	 */
	public static void mainClassAnalyse(CtClass<?> classElement) {
		Set<CtMethod<?>> methods;

		methods = classElement.getMethods();
		for (CtMethod<?> ctmethod : methods) {
			if (ctmethod.getSimpleName().toString().equals("main")) {
				ClassRanking.addPoints(classElement.getSimpleName(), MAINCLASS_POINTS);

			}

		}
	}

	/**
	 * If it's a big big class
	 */
	public static void godClassAnalyse(CtClass<?> classElement) {
		int classAverageSize = getAverageSize();
		if (classElement.toString().length() > classAverageSize * 2) {
			ClassRanking.addPoints(classElement.getSimpleName(), GODCLASS_POINTS);
		}

	}

	private static int getAverageSize() {
		int sizeClassTotal = 0;
		int currentClassElementSize;
		int classNumbers = classList.size();
		int classAverageSize;

		for (CtClass<?> classElement : classList) {
			currentClassElementSize = classElement.toString().length();
			sizeClassTotal = sizeClassTotal + currentClassElementSize;
		}
		classAverageSize = sizeClassTotal / classNumbers;
		return classAverageSize;
	}

	/**
	 * One method = some points
	 */
	public static void methodNumbersAnalyse(CtClass<?> classElement) {
		int methodNumbers;
		methodNumbers = classElement.getMethods().size();
		ClassRanking.addPoints(classElement.getSimpleName(), methodNumbers);

	}

	public static void methodStrengthAnalyse() {

	}

	/**
	 * One attribute = some points;
	 */
	public static void attributeNumbersAnalyse(CtClass<?> classElement) {
		ClassRanking.addPoints(classElement.getSimpleName(), classElement.getAllFields().size());
	}

}

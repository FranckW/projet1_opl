package spoon;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class RankingProcessor extends AbstractProcessor<CtClass<?>> {

	private ClassRanking ranking;
	private List<CtClass<?>> ctClassList;
	
	final static int EXTENDS_POINTS = 5;
	final static int MAINCLASS_POINTS = 5;
	final static int GODCLASS_POINTS = 5;
	final static int IMPLEMENTS_POINTS = 2;
	
	public RankingProcessor(){
		this.ranking = new ClassRanking();
		this.ctClassList = new LinkedList<CtClass<?>>();
	}
	
	public ClassRanking getRanking(){
		return ranking;
	}
	
	public boolean isToBeProcessed(CtClass<?> candidate) {
		this.ranking.addClass(candidate);
		return true;
	}
	
	@Override
	public void process(CtClass<?> arg0) {
		this.ctClassList.add(arg0);		
	}
	
	public void analyse(){
		for(CtClass<?> classElement : this.ctClassList){
			
			this.extendsAnalyse(classElement);
	        this.mainClassAnalyse(classElement);
	        this.godClassAnalyse(classElement);
	        this.methodNumbersAnalyse(classElement);
	        this.attributeNumbersAnalyse(classElement);
	        this.implementsAnalyse(classElement);
			
		}
	}
	
	/**
	 * Get the name of the parent class if it exist and give points to it
	 */
	public void extendsAnalyse(CtClass<?> classElement){
		
		String parentClass;
			if(classElement.getSuperclass() != null){
				parentClass = classElement.getSuperclass().getSimpleName();
				this.ranking.addPoints(parentClass, EXTENDS_POINTS);
			
		}
	}

	/**
	 * Class which implements interface get points
	 */
	public void implementsAnalyse(CtClass<?> classElement){
		this.ranking.addPoints(classElement, classElement.getSuperInterfaces().size() * IMPLEMENTS_POINTS);
	}
	
	/**
	 * If a main remains, add points
	 */
	public void mainClassAnalyse(CtClass<?> classElement){
		Set<CtMethod<?>> methods;
		methods = classElement.getMethods();
		for(CtMethod<?> ctmethod : methods){
			if(ctmethod.getSimpleName().toString().equals("main")){
				this.ranking.addPoints(classElement.getSimpleName(), MAINCLASS_POINTS);
			}				
		}
	}
	
	/**
	 * If it's a big big class get points
	 * Still naive (consider the imports...)
	 */
	public void godClassAnalyse(CtClass<?> classElement){
		int classAverageSize = getAverageSize();
			if(classElement.toString().length() > classAverageSize*3){
				this.ranking.addPoints(classElement.getSimpleName(), GODCLASS_POINTS);
			}
		
	}
	
	private int getAverageSize(){
		int sizeClassTotal = 0;
		int currentClassElementSize;
		int classNumbers = this.ctClassList.size();
		int classAverageSize;
		
		for(CtClass<?> classElement : this.ctClassList){
			currentClassElementSize = classElement.toString().length();
			sizeClassTotal = sizeClassTotal + currentClassElementSize;
			}
		classAverageSize = sizeClassTotal / classNumbers;
		return classAverageSize;
	}
		
	/**
	 * One method = some points
	 * */
	public void methodNumbersAnalyse(CtClass<?> classElement){
		int methodNumbers;
		methodNumbers = classElement.getMethods().size();
		this.ranking.addPoints(classElement.getSimpleName(), methodNumbers);
			
		
		
	}
	
	public static void methodStrengthAnalyse(){
		
	}
	
	/**
	 * One attribute = some points;
	 */
	public void attributeNumbersAnalyse(CtClass<?> classElement){
		this.ranking.addPoints(classElement.getSimpleName(),classElement.getAllFields().size());
	}
	
	
	
}

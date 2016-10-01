package spoon;

import java.util.HashMap;
import java.util.Map;

import spoon.reflect.declaration.CtClass;

/**
 * Will allow to rank the modified classes from the github PR in the order of importance
 */
public class ClassRanking {

	private static Map<CtClass<?> , Integer> classRankingMap;
	
	public ClassRanking(){
		classRankingMap = new HashMap<CtClass<?>, Integer>();
	}
	
	public static void addClass(CtClass<?> classCt){
		classRankingMap.put(classCt, 0);
	}
	
	public static void addPoints(String className, int points){
		for (CtClass<?> classCt: classRankingMap.keySet()){

			if(classCt.getSimpleName().toString().equals(className)){
    			classRankingMap.put(classCt, classRankingMap.get(classCt) + points);
            }            
		}
	}
	
	public static void addPoints(CtClass<?> classCt, int points){
		classRankingMap.put(classCt, classRankingMap.get(classCt) + points);
	}
	
	public String toString(){
		StringBuilder ret = new StringBuilder();
		
		for (CtClass<?> name: classRankingMap.keySet()){
			String key = name.toString();
            String value = classRankingMap.get(name).toString();  
            ret.append("classe : " + key + " score : " + value + "`\n");  
		} 
		
		return ret.toString();
	}
	
}

package spoon;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Will allow to rank the modified classes from the github PR in the order of importance
 */
public class ClassRanking {

	private static Map<String , Integer> classRankingMap;
	
	public ClassRanking(){
		classRankingMap = new HashMap<String, Integer>();
	}
	
	public static void addClass(String className){
		classRankingMap.put(className, 0);
	}
	
	public String toString(){
		StringBuilder ret = new StringBuilder();
		
		for (String name: classRankingMap.keySet()){
            String key = name.toString();
            String value = classRankingMap.get(name).toString();  
            ret.append("classe : " + key + " score : " + value + "`\n");  
		} 
		
		return ret.toString();
	}
	
}

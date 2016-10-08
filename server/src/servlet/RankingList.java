package servlet;

import java.util.LinkedList;
import java.util.List;

import spoon.ClassRanking;

public class RankingList {

	public static List<ClassRanking> classRankingList = new LinkedList<ClassRanking>();
	public static int index = 0;
	
	private static void incrementIndex(){
		index++;
	}
	
	public static ClassRanking getClassRanking(){
		return classRankingList.get(index);
	}
	
	//Implique que l'on fait une nouvelle analyse, on ajoute alors l'index de 1
	public static  void addClassRanking(ClassRanking classRanking){
		classRankingList.add(classRanking);
		incrementIndex();
	}
	
}

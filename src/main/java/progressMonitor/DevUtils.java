package progressMonitor;

import java.util.List;

public class DevUtils {
	public static <T>  void printList(String caption,List<T> tList){
		System.out.println("______________"+caption+"_____________");
		System.out.println("______________Total("+tList.size()+")_____________");
		for(T t : tList){
			System.out.println(t);
		}
		System.out.println("______________________________________");
	}
	
	public static void sqlLog(String sqlString){
		//System.out.println("SQL >> "+sqlString);
	}
}

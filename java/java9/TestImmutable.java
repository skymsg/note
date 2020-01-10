import java.util.List;
import java.lang.System;
import java.util.ArrayList;
import java.util.Collections;
public class TestImmutable {
	public static void  createAImmutableListTheJava8Way(){
		List<String> myList = new ArrayList<>();
		myList.add("create");
		myList.add("a");
		myList.add("immutable");
		myList.add("list");
		myList = Collections.unmodifiableList(myList);
		System.out.println(myList);
	}
	public static void createAImmutableListTheJava9Way(){
		List<String> myList = List.of("create","a","immutable","list");
		System.out.println(myList);
	}
	public static void main(String[] args){
		createAImmutableListTheJava8Way();
		createAImmutableListTheJava9Way();
	}
}

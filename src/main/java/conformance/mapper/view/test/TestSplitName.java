package conformance.mapper.view.test;

import java.util.ArrayList;

public class TestSplitName {

	public static void main(String[] args) {

		String name = "MediaController";
		String[] splited = name.split("_| ");
		ArrayList<String> parts = new ArrayList<String>();
		
		for(String a: splited){
			char[] cadena = a.toCharArray();
			
			int i=0;
			String firstLetter = "";
			if(!String.valueOf(cadena[i]).equals("[A-Z]")){
				firstLetter = String.valueOf(cadena[i]);
				i++;
			}
			
			if(a.split("[A-Z]").length>i+1 && !a.startsWith("[A-Z]")){
				String word = firstLetter;

				for(int e=i; e<cadena.length;e++){
					char c = cadena[e];
					while(!String.valueOf(c).matches("^[A-Z]") && e<cadena.length){
						word = word + c;
						if(e+1<cadena.length){
							c = cadena[e+1];
						}
						e++;
					}
					parts.add(word);
					word = String.valueOf(c);
				}
				
			}else{
				parts.add(a);
			}
		}
		
		for(String a: parts){
			System.out.println(a);
		}

	}

}

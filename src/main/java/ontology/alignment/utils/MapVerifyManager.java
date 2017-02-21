package ontology.alignment.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ontology.alignment.grouping.DataSetManager;
import ontology.alignment.pairing.PairOE;

public class MapVerifyManager {

 	private static HashMap<String,List<String>> map;
 	private static MapVerifyManager instance;
 	private static List<Solution> qualitySolution;
	
	public static MapVerifyManager getInstance() {
		if (instance==null) {
			instance = new MapVerifyManager();
		}
		return instance;
	}
	
	public static void initialize(String filename) {
	      File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;
	 
	      map = new HashMap<String,List<String>>();

	      qualitySolution = new ArrayList<Solution>();
	      
	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File (filename);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);
	 
	         // Lectura del fichero
	         String linea;
	         while((linea=br.readLine())!=null){
	           String[] mapping = linea.split("=");
	           List<String> list = new ArrayList<String>();
	           String[] mapping2 = mapping[1].split(",");
	           for(int i=0;i<mapping2.length;i++){
	        	   list.add(mapping2[i]);
	           }
	           if(map.get(mapping[0])==null){
	        	   map.put(mapping[0], list);
	           }else{
	        	   map.get(mapping[0]).addAll(list);
	           }
	         }
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
		
	}

	public static boolean isCorrectMapping(String n1, String n2) {
		List<String> list = map.get(n1);
		if(list!=null && list.contains(n2)){
			return true;
		}
		return false;
	}
	
	public static void printSolutions(List<PairOE> pairs){
		int mapCorrects = 0;
		int mapIncorrects = 0;
		for(PairOE pair:pairs){
			boolean correct = MapVerifyManager.getInstance().isCorrectMapping(pair.getElement2().getName(), pair.getElement1().getName());
			System.out.println(pair.getKey()+" -> "+DataSetManager.getInstance().getInstanceMap().get(pair).toString()+" "+correct);
			if(correct){
				mapCorrects++;
			}else{
				mapIncorrects++;
			}
		}	
		
		System.out.println("Mapeos Correctos: "+mapCorrects);
		System.out.println("Mapeos Incorrectos: "+mapIncorrects);
	}
	
	public static void printSolutions2(List<PairOE> pairs){
		int mapCorrects = 0;
		int mapIncorrects = 0;
		
		Set<String> set = map.keySet();
		for(String clazz:set){
			List<String> list = map.get(clazz);
			for(String component:list){
				for(PairOE pair:pairs){
					if(pair.getElement1().getName().equals(component)&&
							pair.getElement2().getName().equals(clazz)){
						mapCorrects++;
					}
					
				}
				mapIncorrects++;
			}
		}
		
		System.out.println("Mapeos Correctos: "+mapCorrects);
		System.out.println("Mapeos Incorrectos: "+(mapIncorrects-mapCorrects));

	}
	
	public static void printSolutions3(List<PairOE> pairs){

		Set<String> set = map.keySet();
		double relevantElements = 0;
		for(String key:set){
			List<String> relElems = map.get(key);
			relevantElements = relevantElements + relElems.size();
		}
		
		double truePositive = 0;
		double falsePositive = 0;
		
		for(PairOE pair:pairs){
			boolean correct = MapVerifyManager.getInstance().isCorrectMapping(pair.getElement2().getName(), pair.getElement1().getName());
			System.out.println(pair.getKey()+" -> "+DataSetManager.getInstance().getInstanceMap().get(pair).toString()+" "+correct);
			if(correct){
				truePositive++;
			}else{
				falsePositive++;
			}
		}	
		
		System.out.println("Mapeos Correctos: "+truePositive);
		System.out.println("Mapeos Incorrectos: "+falsePositive);
		
		double presicion = truePositive/(truePositive+falsePositive);
		double recall = (truePositive/relevantElements);
		System.out.println("Presicion: "+presicion);
		System.out.println("Recall: "+recall);
		
	}
	
	public static void printSolutions4(String etiqueta, List<PairOE> pairs){

		Set<String> set = map.keySet();
		double relevantElements = 0;
		for(String key:set){
			List<String> relElems = map.get(key);
			relevantElements = relevantElements + relElems.size();
		}
		
		double truePositive = 0;
		double falsePositive = 0;
		
		for(PairOE pair:pairs){
			boolean correct = MapVerifyManager.getInstance().isCorrectMapping(pair.getElement2().getName(), pair.getElement1().getName());
			//System.out.println(pair.getKey()+" -> "+DataSetManager.getInstance().getInstanceMap().get(pair).toString()+" "+correct);
			if(correct){
				truePositive++;
			}else{
				falsePositive++;
			}
		}	
		
		System.out.println("Mapeos Correctos: "+truePositive);
		System.out.println("Mapeos Incorrectos: "+falsePositive);
		
		double presicion = truePositive/(truePositive+falsePositive);
		double recall = (truePositive/relevantElements);
		System.out.println("Presicion: "+presicion);
		System.out.println("Recall: "+recall);
		
		qualitySolution.add(new Solution(etiqueta,truePositive, falsePositive, presicion, recall));
	
	}
	
	public static void bestSolution(){
		qualitySolution.sort(new Comparator<Solution>() {

			@Override
			public int compare(Solution arg0, Solution arg1) {
				if(arg0.getPresicion()>arg1.getPresicion()){
					return 1;
				}else if(arg0.getPresicion()<arg1.getPresicion()){
					return -1;
				}
				return 0;
			}

		});
		
		for(Solution sol:qualitySolution){
			System.out.println(sol.toString());
		}
	}

	public void printSolutionsCluster(
			ArrayList<ArrayList<PairOE>> alignment) {
		
		Set<String> set = map.keySet();
		double relevantElements = 0;
		for(String key:set){
			List<String> relElems = map.get(key);
			relevantElements = relevantElements + relElems.size();
		}
		
		double precision = 0.0;
		double recall = 0.0;
		double fmeasure = 0.0;
		double truePositive = 0;
		double falsePositive = 0;
				
		for(int i=0;i<alignment.size();i++){
			
			double truePositiveVar = 0;
			double falsePositiveVar = 0;
			
			ArrayList<PairOE> pairs = alignment.get(i);
			
			for(PairOE pair:pairs){
				boolean correct = MapVerifyManager.getInstance().isCorrectMapping(pair.getElement2().getName(), pair.getElement1().getName());
				//System.out.println(pair.getKey()+" -> "+DataSetManager.getInstance().getInstanceMap().get(pair).toString()+" "+correct);
				if(correct){
					truePositiveVar++;
				}else{
					falsePositiveVar++;
				}
			}
			
			if(precision<truePositiveVar/(truePositiveVar+falsePositiveVar)){
				precision = truePositiveVar/(truePositiveVar+falsePositiveVar);
				recall = (truePositiveVar/relevantElements);
				fmeasure = (2* precision * recall)/(precision + recall);
				truePositive = truePositiveVar;
				falsePositive = falsePositiveVar;
			}
			
		}
		
		System.out.println();
		//System.out.println("Cluster nro: " + i);
		System.out.println("Mapeos Correctos: "+truePositive);
		System.out.println("Mapeos Incorrectos: "+falsePositive);
		
		System.out.println("Precision: "+precision);
		System.out.println("Recall: "+recall);
		System.out.println("F-Measure: "+fmeasure);
	}
	
	public void printSolutionsCluster2(
			ArrayList<ArrayList<PairOE>> alignment) {
		
		Set<String> set = map.keySet();
		double relevantElements = 0;
		for(String key:set){
			List<String> relElems = map.get(key);
			relevantElements = relevantElements + relElems.size();
		}
		
		double precision = 0.0;
		double recall = 0.0;
		double fmeasure = 0.0;
		double truePositive = 0;
		double falsePositive = 0;
				
		for(int i=0;i<alignment.size();i++){
			
			double truePositiveVar = 0;
			double falsePositiveVar = 0;
			
			ArrayList<PairOE> pairs = alignment.get(i);
			
			for(PairOE pair:pairs){
				boolean correct = MapVerifyManager.getInstance().isCorrectMapping(pair.getElement2().getName(), pair.getElement1().getName());
				//System.out.println(pair.getKey()+" -> "+DataSetManager.getInstance().getInstanceMap().get(pair).toString()+" "+correct);
				if(correct){
					truePositiveVar++;
				}else{
					falsePositiveVar++;
				}
			}
			
			if(precision<truePositiveVar/(truePositiveVar+falsePositiveVar)){
				precision = truePositiveVar/(truePositiveVar+falsePositiveVar);
				recall = (truePositiveVar/relevantElements);
				fmeasure = (2* precision * recall)/(precision + recall);
				truePositive = truePositiveVar;
				falsePositive = falsePositiveVar;
			}
			
		}
		
		System.out.println();
		//System.out.println("Cluster nro: " + i);
//		System.out.println("Mapeos Correctos: "+truePositive);
//		System.out.println("Mapeos Incorrectos: "+falsePositive);
		
//		System.out.println("Precision: "+precision);
//		System.out.println("Recall: "+recall);
//		System.out.println("F-Measure: "+fmeasure);
		System.out.println(precision+" "+recall+" "+fmeasure);
	}
	
	public String getSolutionsCluster2(
			ArrayList<ArrayList<PairOE>> alignment) {
		
		Set<String> set = map.keySet();
		double relevantElements = 0;
		for(String key:set){
			List<String> relElems = map.get(key);
			relevantElements = relevantElements + relElems.size();
		}
		
		double precision = 0.0;
		double recall = 0.0;
		double fmeasure = 0.0;
		double truePositive = 0;
		double falsePositive = 0;
				
		for(int i=0;i<alignment.size();i++){
			
			double truePositiveVar = 0;
			double falsePositiveVar = 0;
			
			ArrayList<PairOE> pairs = alignment.get(i);
			
			for(PairOE pair:pairs){
				boolean correct = MapVerifyManager.getInstance().isCorrectMapping(pair.getElement2().getName(), pair.getElement1().getName());
				//System.out.println(pair.getKey()+" -> "+DataSetManager.getInstance().getInstanceMap().get(pair).toString()+" "+correct);
				if(correct){
					truePositiveVar++;
				}else{
					falsePositiveVar++;
				}
			}
			
			if(precision<truePositiveVar/(truePositiveVar+falsePositiveVar)){
				precision = truePositiveVar/(truePositiveVar+falsePositiveVar);
				recall = (truePositiveVar/relevantElements);
				fmeasure = (2* precision * recall)/(precision + recall);
				truePositive = truePositiveVar;
				falsePositive = falsePositiveVar;
			}
			
		}
		
		//System.out.println("Cluster nro: " + i);
//		System.out.println("Mapeos Correctos: "+truePositive);
//		System.out.println("Mapeos Incorrectos: "+falsePositive);
		
//		System.out.println("Precision: "+precision);
//		System.out.println("Recall: "+recall);
//		System.out.println("F-Measure: "+fmeasure);
		return (precision+" "+recall+" "+fmeasure);
	}
	

}
package conformance.mapper.view.test;


import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.core.AlignmentImp;
import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.parser.RDFtoOntologyGraph;
import conformance.mapper.utils.MapVerifyManager;

public class TestOntologyAlignmentHW {
	
	public static void main(String[] args) {
		
		System.out.println("Start Test");
		
		//executeSingleTest();
		executeAllTheTests();
			
		System.out.println("Finish Test");
		
	}

	private static void executeAllTheTests() {
		   
			Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontoarchdiagramHW3.rdf");
			Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontojavacodeHW2.rdf");
			
			AlignmentImp alignament = new AlignmentImp();
			
			MapVerifyManager.getInstance().initialize("OntologyFiles/HWmap.txt");
			
			ConfigurationManager.setProperty("FL_FILE_PATH", "FeatureLocation/HW_FL.txt");
			
			System.out.println("Processing... ");
			
			boolean L[] = {true,false,false,false};
			boolean FL[] = {false,true,false,false};
			boolean L_FL[] = {true,true,false,false};
			boolean FL_S[] = {false,true,true,false};
			boolean L_S[] = {true,false,true,false};
			boolean L_S_H[] = {true,false,true,true};
			boolean L_FL_S_H[] = {true,true,true,true};
			
			printResultsInFile("HW_L",o1,o2,alignament,L);
			printResultsInFile("HW_FL",o1,o2,alignament,FL);
			printResultsInFile("HW_L_FL",o1,o2,alignament,L_FL);
			printResultsInFile("HW_FL_S",o1,o2,alignament,FL_S);
			printResultsInFile("HW_L_S",o1,o2,alignament,L_S);
			printResultsInFile("HW_L_S_H",o1,o2,alignament,L_S_H);
			printResultsInFile("HW_L_FL_S_H",o1,o2,alignament,L_FL_S_H);
		
	}

	private static void printResultsInFile(String fileName, Ontology o1, Ontology o2, AlignmentImp alignament, boolean[] simF) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		   {
	       fichero = new FileWriter("Results/HW/"+fileName);
           pw = new PrintWriter(fichero);
				
           ConfigurationManager.setProperty("LinguisticSimilarityFunctionEnabled", String.valueOf(simF[0]));
           ConfigurationManager.setProperty("FeatureLocationAnalysis", String.valueOf(simF[1]));
           ConfigurationManager.setProperty("StructuralSimilarityFunctionEnabled", String.valueOf(simF[2]));
           ConfigurationManager.setProperty("HierarchicalAnalysis", String.valueOf(simF[3]));        
           
           List<PairOE> result = alignament.executeAlignment(o1, o2,  new ArrayList<PairOE>()).get(0);
                    
           for(PairOE pair:result){
        	   int correct =  MapVerifyManager.getInstance().isCorrectMappingInt(pair.getElement2().getShortName(), pair.getElement1().getName());
        	   if(correct!=-1){
        		   pw.println(pair.getElement1().getName()+" "+pair.getElement2().getName()+" "+pair.getConfidence()+" "+ 
        			   correct);
        	   }else{
        		   //pw.println(pair.getElement1().getName()+" "+pair.getElement2().getName()+" "+pair.getConfidence()+" "+  1);
        	   }
           }
           
           
           
		}
		 catch (Exception e) {
           e.printStackTrace();
        } finally {
          try {
             if (null != fichero)
               fichero.close();
             } 
          catch (Exception e2) {
             e2.printStackTrace();
          }
       }
		
	}

	private static void executeSingleTest() {
		
		System.out.println("Start Test");
		
		Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontoarchdiagramHW3.rdf");
		Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontojavacodeHW2.rdf");
		
		AlignmentImp alignament = new AlignmentImp();
		
		MapVerifyManager.getInstance().initialize("OntologyFiles/HWmap.txt");
		
		System.out.println("Processing... ");
		
		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		   {
	       fichero = new FileWriter("OntologyFiles/HWtest2.txt");
           pw = new PrintWriter(fichero);

       
			/*for(int numCluster = 1; numCluster<21; numCluster++){
				
				System.out.print(" "+numCluster*2);
				
				ConfigurationManager.setProperty("numCluster", String.valueOf(numCluster*2));
				
				ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("CorleyMihalceaComparer"));
				String s1 = MapVerifyManager.getInstance().getSolutionsCluster2(alignament.executeAlignment(o1, o2,  new ArrayList<PairOE>()));
				
				ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("GreedyComparer"));
				String s2 = MapVerifyManager.getInstance().getSolutionsCluster2(alignament.executeAlignment(o1, o2,  new ArrayList<PairOE>()));
				
				ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("OptimumComparer"));
				String s3 = MapVerifyManager.getInstance().getSolutionsCluster2(alignament.executeAlignment(o1, o2,  new ArrayList<PairOE>()));
		
				pw.println(s1+" "+s2+" "+s3);
			}*/
           
           
           
           List<PairOE> result = alignament.executeAlignment(o1, o2,  new ArrayList<PairOE>()).get(0);
           
           
           
           for(PairOE pair:result){
        	   int correct =  MapVerifyManager.getInstance().isCorrectMappingInt(pair.getElement2().getShortName(), pair.getElement1().getName());
        	   if(correct!=-1){
        	   pw.println(pair.getElement1().getName()+" "+pair.getElement2().getName()+" "+pair.getConfidence()+" "+ 
        			   correct);
        	   }
           }
           
           
           
		}
		 catch (Exception e) {
           e.printStackTrace();
        } finally {
          try {
             if (null != fichero)
               fichero.close();
             } 
          catch (Exception e2) {
             e2.printStackTrace();
          }
       }
			
		System.out.println("Finish Test");
		
	}
}

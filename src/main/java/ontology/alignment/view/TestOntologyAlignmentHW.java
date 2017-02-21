package ontology.alignment.view;

import java.io.FileWriter;
import java.io.PrintWriter;

import ontology.alignment.configuration.ConfigurationManager;
import ontology.alignment.core.AlignmentImp;
import ontology.alignment.definition.Ontology;
import ontology.alignment.parser.RDFtoOntologyGraph;
import ontology.alignment.utils.MapVerifyManager;

public class TestOntologyAlignmentHW {

	public static void main(String[] args) {
		
		System.out.println("Start Test");
		
		Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontoarchdiagramHW.rdf");
		Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontojavacodeHW.rdf");
		
		AlignmentImp alignament = new AlignmentImp();
		
		MapVerifyManager.getInstance().initialize("OntologyFiles/HWmap2.txt");
		
		System.out.println("Processing... ");
		
		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		   {
	       fichero = new FileWriter("OntologyFiles/HWtest2.txt");
           pw = new PrintWriter(fichero);

       
			for(int numCluster = 1; numCluster<21; numCluster++){
				
				System.out.print(" "+numCluster*2);
				
				ConfigurationManager.setProperty("numCluster", String.valueOf(numCluster*2));
				
				ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("CorleyMihalceaComparer"));
				String s1 = MapVerifyManager.getInstance().getSolutionsCluster2(alignament.executeAlignment(o1, o2));
				
				ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("GreedyComparer"));
				String s2 = MapVerifyManager.getInstance().getSolutionsCluster2(alignament.executeAlignment(o1, o2));
				
				ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("OptimumComparer"));
				String s3 = MapVerifyManager.getInstance().getSolutionsCluster2(alignament.executeAlignment(o1, o2));
		
				pw.println(s1+" "+s2+" "+s3);
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

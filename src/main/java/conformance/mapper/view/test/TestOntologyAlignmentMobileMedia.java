package conformance.mapper.view.test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.core.AlignmentImp;
import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.parser.RDFtoOntologyGraph;
import conformance.mapper.utils.MapVerifyManager;

public class TestOntologyAlignmentMobileMedia {

	public static void main(String[] args) {
		
		System.out.println("Start Test");
		
		Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontoarchdiagramMM.rdf");
		Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontojavacodeMM.rdf");
		
		AlignmentImp alignament = new AlignmentImp();
		
		MapVerifyManager.getInstance().initialize("OntologyFiles/MMmap.txt");
		
		System.out.println("Processing... ");
		
		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		   {
	       fichero = new FileWriter("OntologyFiles/MMtest2.txt");
           pw = new PrintWriter(fichero);

       
/*			for(*/int numCluster = 10;/* numCluster<21; numCluster++){*/
				
				System.out.print(" "+numCluster*2);
				
				ConfigurationManager.setProperty("numCluster", String.valueOf(numCluster*2));
				
				ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("CorleyMihalceaComparer"));
				String s1 = MapVerifyManager.getInstance().getSolutionsCluster2(alignament.executeAlignment(o1, o2,  new ArrayList<PairOE>()));
				
				ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("GreedyComparer"));
				String s2 = MapVerifyManager.getInstance().getSolutionsCluster2(alignament.executeAlignment(o1, o2,  new ArrayList<PairOE>()));
				
				ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("OptimumComparer"));
				String s3 = MapVerifyManager.getInstance().getSolutionsCluster2(alignament.executeAlignment(o1, o2,  new ArrayList<PairOE>()));
		
				pw.println(s1+" "+s2+" "+s3);
//			}
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

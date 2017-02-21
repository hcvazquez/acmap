package ontology.alignment.view;

import ontology.alignment.configuration.ConfigurationManager;
import ontology.alignment.core.AlignmentImp;
import ontology.alignment.definition.Ontology;
import ontology.alignment.parser.RDFtoOntologyGraph;
import ontology.alignment.utils.MapVerifyManager;

public class TestOntologyAlignmentSDB {

	public static void main(String[] args) {
		
		Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontoarchdiagramSDB.rdf");
		Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontojavacodeSDB.rdf");
		
		//SimpleClassifierAlignment alignament = new SimpleClassifierAlignment();
		AlignmentImp alignament = new AlignmentImp();
		
		MapVerifyManager.getInstance().initialize("OntologyFiles/SDBmap.txt");

		//for(int k=1; k<10; k++){
		//	ConfigurationManager.setProperty("K", String.valueOf(k*5));
/*			double trainingPercentage=0.01;
			for(int e=1; e<10; e++){
				//String etiqueta = "-------------------"+"K="+k*5+" "+"trainingPercentage="+trainingPercentage+"-------------------";
				String etiqueta = "-------------------"+"trainingPercentage="+trainingPercentage+"-------------------";
				System.out.println(etiqueta);
				ConfigurationManager.setProperty("trainingPercentage", String.valueOf(trainingPercentage));
				MapVerifyManager.getInstance().printSolutions4(etiqueta,alignament.executeAlignment(o1, o2));
				trainingPercentage = trainingPercentage + 0.05;
			}*/
//		}
		
		for(int numCluster = 2; numCluster<20; numCluster++){
			System.out.println("-------------------------CorleyMihalceaComparer-----------------------");
			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("CorleyMihalceaComparer"));
			MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));
			
			System.out.println("-------------------------GreedyComparer-----------------------");
			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("GreedyComparer"));
			MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));
			
			System.out.println("-------------------------OptimumComparer-----------------------");
			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("OptimumComparer"));
			MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));
			
	/*		System.out.println("-------------------------DependencyComparer-----------------------");
			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("DependencyComparer"));
			MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));
			
			System.out.println("-------------------------BLEUComparer-----------------------");
			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("BLEUComparer"));
			MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));
			
			System.out.println("-------------------------LSAComparer-----------------------");
			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("LSAComparer"));
			MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));*/
		}
		
	}
}

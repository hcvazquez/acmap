package ontology.alignment.view;

import ontology.alignment.configuration.ConfigurationManager;
import ontology.alignment.core.AlignmentImp;
import ontology.alignment.definition.Ontology;
import ontology.alignment.parser.RDFtoOntologyGraph;
import ontology.alignment.utils.MapVerifyManager;

public class TestOntologyAlignmentMM {

//	public static void main(String[] args) {
//		
//		OntologyGraph o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontoarchdiagramMM.rdf");
//		OntologyGraph o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontojavacodeMM.rdf");
//		
////		SimpleClassifierAlignment alignament = new SimpleClassifierAlignment();
//		SimpleClusterAlignment alignament = new SimpleClusterAlignment();
//		
//		MapVerifyManager.getInstance().initialize("OntologyFiles/MMmap.txt");
////		MapVerifyManager.getInstance().printSolutions3(alignament.executeAlignment(o1, o2));
//		
//		//for(int k=1; k<10; k++){
//		//	ConfigurationManager.setProperty("K", String.valueOf(k*5));
//	/*	double trainingPercentage=0.050;
//		for(int e=1; e<10; e++){
//			//String etiqueta = "-------------------"+"K="+k*5+" "+"trainingPercentage="+trainingPercentage+"-------------------";
//			String etiqueta = "-------------------"+"trainingPercentage="+trainingPercentage+"-------------------";
//			System.out.println(etiqueta);
//			ConfigurationManager.setProperty("trainingPercentage", String.valueOf(trainingPercentage));*/
//			//MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));
//		/*	trainingPercentage = trainingPercentage + 0.005;
//		}*/
//		System.out.println();
//		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
//				+ " Mobile Media xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//		System.out.println();
//		
//		for(int numCluster = 1; numCluster<21; numCluster++){
//			System.out.println("N------------------------ "+numCluster+" ------------------------------N");
//			ConfigurationManager.setProperty("numCluster", String.valueOf(numCluster*2));
//			
//			System.out.println("-------------------------CorleyMihalceaComparer-----------------------");
//			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("CorleyMihalceaComparer"));
//			MapVerifyManager.getInstance().printSolutionsCluster2(alignament.executeAlignment(o1, o2));
//			
//			System.out.println("-------------------------GreedyComparer-----------------------");
//			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("GreedyComparer"));
//			MapVerifyManager.getInstance().printSolutionsCluster2(alignament.executeAlignment(o1, o2));
//			
//			System.out.println("-------------------------OptimumComparer-----------------------");
//			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("OptimumComparer"));
//			MapVerifyManager.getInstance().printSolutionsCluster2(alignament.executeAlignment(o1, o2));
//			
//	/*		System.out.println("-------------------------DependencyComparer-----------------------");
//			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("DependencyComparer"));
//			MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));
//			
//			System.out.println("-------------------------BLEUComparer-----------------------");
//			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("BLEUComparer"));
//			MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));
//			
//			System.out.println("-------------------------LSAComparer-----------------------");
//			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("LSAComparer"));
//			MapVerifyManager.getInstance().printSolutionsCluster(alignament.executeAlignment(o1, o2));*/
//		}
//	}
	
	public static void main(String[] args) {
		
		Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontoarchdiagramMM.rdf");
		Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontojavacodeMM.rdf");
		
//		SimpleClassifierAlignment alignament = new SimpleClassifierAlignment();
		AlignmentImp alignament = new AlignmentImp();
		
		MapVerifyManager.getInstance().initialize("OntologyFiles/MMmap.txt");
		
		for(int numCluster = 1; numCluster<21; numCluster++){
			System.out.println("N------------------------ "+numCluster+" ------------------------------N");
			ConfigurationManager.setProperty("numCluster", String.valueOf(numCluster*2));
			
			System.out.println("-------------------------CorleyMihalceaComparer-----------------------");
			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("CorleyMihalceaComparer"));
			MapVerifyManager.getInstance().printSolutionsCluster2(alignament.executeAlignment(o1, o2));
			
			System.out.println("-------------------------GreedyComparer-----------------------");
			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("GreedyComparer"));
			MapVerifyManager.getInstance().printSolutionsCluster2(alignament.executeAlignment(o1, o2));
			
			System.out.println("-------------------------OptimumComparer-----------------------");
			ConfigurationManager.setProperty("LinguisticSimilarityComparer", String.valueOf("OptimumComparer"));
			MapVerifyManager.getInstance().printSolutionsCluster2(alignament.executeAlignment(o1, o2));
			
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

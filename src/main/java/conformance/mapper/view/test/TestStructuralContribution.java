package conformance.mapper.view.test;

import java.util.HashMap;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.grouping.ranking.Ranking;
import conformance.mapper.services.RecommendationManager;


public class TestStructuralContribution {

	public static void main(String[] args) {
		
		RecommendationManager recommender = new RecommendationManager();
		
		HashMap<String,Double> configuration = new HashMap<String,Double>();
		
		Double structuralValue = 0.05;
//		Double otherValues = 0.25;
		double[] spearmanValues = new double[20];
		for(int i=0;i<20;i++){
			structuralValue = 0.5 + (0.20*i);
//			otherValues = otherValues - (0.05*i);
			
			configuration.put(ConfigurationManager.LINGUISTIC_ANALYSIS, 1.0);
//			configuration.put(ConfigurationManager.FEATURE_LOCATION_ANALYSIS, 1.0);
			configuration.put(ConfigurationManager.STRUCTURAL_ANALYSIS, structuralValue);
//			configuration.put(ConfigurationManager.HIERARCHICAL_ANALYSIS, otherValues);
			
			//Ranking recommendation = recommender.getRecomendationRankingWithEvaluation("file:/C:/benchmarks/ontoarchdiagramHW3.rdf", "file:/C:/benchmarks/ontojavacodeHW2.rdf", configuration,"OntologyFiles/HWmap.txt");
			
			Ranking recommendation = recommender.getRecomendationRankingWithEvaluation("file:/C:/benchmarks/ontoarchdiagramMM.rdf", "file:/C:/benchmarks/ontojavacodeMM.rdf", configuration,"OntologyFiles/MMmap.txt");
//			System.out.println(i+": Max Precision -> "+recommendation.getMaxPrecision());
//			System.out.println("    Max Recall    -> "+recommendation.getMaxRecall(122));
//			System.out.println("    Max F-Measure -> "+recommendation.getMaxFMeasure(122));
//			System.out.println("    Pearsons      -> "+recommendation.calculatePearsonsCorrelation());
//			System.out.println("    Spearman      -> "+recommendation.calculateSpearmanCorrelation());
//			System.out.println("    Kendalls      -> "+recommendation.calculateKendallsCorrelation());
//			System.out.println();
			
			//recommendation.saveRankingInFile("Results/Recommendations/StructuralAnalysis/"+i);
			
			spearmanValues[i] = recommendation.calculateSpearmanCorrelation();
		}
		
		System.out.println("SPEARMAN VALUES");
		for(double i:spearmanValues){
			System.out.println(i);
		}
		
		
		
	}
}

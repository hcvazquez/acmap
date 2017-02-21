package conformance.mapper.view.test;

import java.util.ArrayList;
import java.util.HashMap;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.grouping.ranking.Ranking;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.services.RecommendationManager;
import conformance.mapper.utils.MapVerifyManager;


public class TestRodrigo {

	public static void main(String[] args) {
		
		RecommendationManager recommender = new RecommendationManager();
		
		HashMap<String,Double> configuration = new HashMap<String,Double>();
			
		configuration.put(ConfigurationManager.LINGUISTIC_ANALYSIS, 1.0);
//		configuration.put(ConfigurationManager.FEATURE_LOCATION_ANALYSIS, 1.0);
		configuration.put(ConfigurationManager.STRUCTURAL_ANALYSIS, 1.0);
//		configuration.put(ConfigurationManager.HIERARCHICAL_ANALYSIS, otherValues);
						
			Ranking recommendation = recommender.getRecomendationRanking("file:/C:/benchmarks/ontoarchdiagramMM.rdf", "file:/C:/benchmarks/ontojavacodeMM.rdf", configuration);

		
		System.out.println("**************** ************* RESULTS ************** ****************");
		/*for(double i:spearmanValues){
			System.out.println(i);
		}*/
        for(PairOE pair:recommendation.getRankingList()){
     	   System.out.println(pair.getElement1().getName()+" -> "+pair.getElement2().getName()+"  RankingValue="+pair.getConfidence() );
        }
		
		
	}
}

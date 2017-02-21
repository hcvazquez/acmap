package conformance.mapper.core;

import java.util.ArrayList;
import java.util.List;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.definition.Ontology;
import conformance.mapper.grouping.Grouper;
import conformance.mapper.grouping.clustering.ClusterGrouper;
import conformance.mapper.grouping.ranking.RankGrouper;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.pairing.PairingManager;
import conformance.mapper.similarity.SimilarityDataManager;
import conformance.mapper.similarity.SimilarityMatrix;
import conformance.mapper.similarity.function.experimental.MaximumMatchingFunction;
import conformance.mapper.similarity.function.experimental.PropertyFunction;
import conformance.mapper.similarity.function.experimental.SNAFunction;
import conformance.mapper.similarity.function.featurelocation.FeatureLocationFunction;
import conformance.mapper.similarity.function.hierarchical.HierarchicalFunction;
import conformance.mapper.similarity.function.linguistic.LinguisticFunction;
import conformance.mapper.similarity.function.structural.StructuralFunction;

public class AlignmentImp implements Alignment{
	
	LinguisticFunction linguisticFunction;
	PropertyFunction propertyFunction;
	StructuralFunction structuralFunction;
	SNAFunction snaFunction;
	HierarchicalFunction hierarchicalFunction;
	FeatureLocationFunction featureLocationFunction;
	MaximumMatchingFunction maximumMatchingFunction; 
	
	
	public AlignmentImp(){
		this.linguisticFunction = new LinguisticFunction();
		this.propertyFunction = new PropertyFunction();
		this.structuralFunction = new StructuralFunction();
		this.snaFunction = new SNAFunction();
		this.hierarchicalFunction = new HierarchicalFunction();
		this.featureLocationFunction = new FeatureLocationFunction();
		this.maximumMatchingFunction = new MaximumMatchingFunction();
	}

	@Override
	public ArrayList<ArrayList<PairOE>> executeAlignment(Ontology o1, Ontology o2, List<PairOE> initialMap){
		
		SimilarityDataManager.getInstance().clear();
		SimilarityDataManager.getInstance().setInitialMap(initialMap);
		
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.LINGUISTIC_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(linguisticFunction.execute(o1,o2));
		}
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.PROPERTY_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(propertyFunction.execute(o1,o2));
		}
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.FEATURE_LOCATION_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(featureLocationFunction.execute(o1,o2));
		}
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.STRUCTURAL_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(structuralFunction.execute(o1,o2));
		}
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.MAXIMUM_MATCHING_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(maximumMatchingFunction.execute(o1,o2));
		}
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.SOCIALNETWORK_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(snaFunction.execute(o1,o2));
		}
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.HIERARCHICAL_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(hierarchicalFunction.execute(o1,o2));
		}
		
		Grouper grouper = null;
		
		if("clustering".equalsIgnoreCase(ConfigurationManager.getProperty(ConfigurationManager.GROUPING))){
			grouper = new ClusterGrouper();
		}
		
		if("ranking".equalsIgnoreCase(ConfigurationManager.getProperty(ConfigurationManager.GROUPING))){
			grouper = new RankGrouper();
		}
		
		/*
		 * DEBUG
		 */
		/*List<PairOE> pairs = PairingManager.getInstance().buildNodePairs(o1, o2);
		for(PairOE pair:pairs){
			System.out.println(SimilarityDataManager.getInstance().getMapProbability(pair));
		}*/
		
		return grouper.groupSolutions(PairingManager.getInstance().buildNodePairs(o1, o2), SimilarityDataManager.getInstance().getSimilarityData());	
	}
	
	@Override
	public ArrayList<PairOE> getSolution(ArrayList<ArrayList<PairOE>> clusters){
		
		ArrayList<PairOE> solution = clusters.get(0);
		Double solutionProm = promOfSimilarity(solution);
		
		for(int i = 1 ; i< clusters.size(); i++){
			ArrayList<PairOE> cluster = clusters.get(i);
			Double clusterProm = promOfSimilarity(cluster);
			if(clusterProm>solutionProm){
				solution = cluster;
				solutionProm = clusterProm;
			}
		}
		
		return solution;
	}
	
	public Double promOfSimilarity(ArrayList<PairOE> cluster){
		Double sum = 0.00;
		for(PairOE pair:cluster){
			double sumPair = 0;
			List<SimilarityMatrix> similarityData = SimilarityDataManager.getInstance().getSimilarityData();
			for (int i = 0; i < similarityData.size(); i++) {
				sumPair = sumPair + similarityData.get(i).getPairValue(pair);
			}
			sum = sum + (sumPair/similarityData.size());
		}
		return sum/cluster.size();
	}
	
}

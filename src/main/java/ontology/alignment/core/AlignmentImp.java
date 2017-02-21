package ontology.alignment.core;

import java.util.ArrayList;
import java.util.List;

import ontology.alignment.configuration.ConfigurationManager;
import ontology.alignment.definition.Ontology;
import ontology.alignment.grouping.ClusterGrouper;
import ontology.alignment.grouping.ClusterManager;
import ontology.alignment.grouping.DataSetManager;
import ontology.alignment.grouping.Grouper;
import ontology.alignment.pairing.PairOE;
import ontology.alignment.pairing.PairingManager;
import ontology.alignment.similarity.LinguisticFunction;
import ontology.alignment.similarity.PropertyFunction;
import ontology.alignment.similarity.SNAFunction;
import ontology.alignment.similarity.SimilarityDataManager;
import ontology.alignment.similarity.SimilarityMatrix;
import ontology.alignment.similarity.StructuralFunction;
import ontology.alignment.utils.MapVerifyManager;
import weka.clusterers.Clusterer;
import weka.core.Instances;

public class AlignmentImp{
	
	LinguisticFunction linguisticFunction;
	PropertyFunction propertyFunction;
	StructuralFunction structuralFunction;
	SNAFunction snaFunction;
	
	
	public AlignmentImp(){
		this.linguisticFunction = new LinguisticFunction();
		this.propertyFunction = new PropertyFunction();
		this.structuralFunction = new StructuralFunction();
		this.snaFunction = new SNAFunction();
	}

	public ArrayList<ArrayList<PairOE>> executeAlignment(Ontology o1, Ontology o2){
		
		SimilarityDataManager.getInstance().clear();
		
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.LINGUISTIC_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(linguisticFunction.execute(o1,o2));
		}
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.PROPERTY_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(propertyFunction.execute(o1,o2));
		}
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.STRUCTURAL_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(structuralFunction.execute(o1,o2));
		}
		if(Boolean.valueOf(ConfigurationManager.getProperty(ConfigurationManager.SOCIALNETWORK_ANALYSIS))){
			SimilarityDataManager.getInstance().addSimilarityData(snaFunction.execute(o1,o2));
		}
		
		Grouper grouper = null;
		
		if("clustering".equalsIgnoreCase(ConfigurationManager.getProperty(ConfigurationManager.GROUPING))){
			grouper = new ClusterGrouper();
		}
		
		return grouper.groupSolutions(PairingManager.getInstance().buildNodePairs(o1, o2), SimilarityDataManager.getInstance().getSimilarityData());	
	}
	
	
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

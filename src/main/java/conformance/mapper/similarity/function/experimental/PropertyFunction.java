package conformance.mapper.similarity.function.experimental;

import java.util.List;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.definition.Node;
import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityFunction;
import conformance.mapper.similarity.SimilarityMatrix;
import conformance.mapper.similarity.SimilarityMatrixImp;

public class PropertyFunction implements SimilarityFunction{

	@Override
	public SimilarityMatrix execute(Ontology o1, Ontology o2) {
		
		SimilarityMatrixImp simMatrix = new SimilarityMatrixImp("Property Similarity",this);
	    
	    for(int i=0; i<o1.getNodes().size();i++){
	    	for(int e=0; e<o2.getNodes().size();e++){
	    		PairOE pairOE = new PairOE(o1.getNodes().get(i),o2.getNodes().get(e));
				simMatrix.setPairValue(pairOE, getSimilarityValue(o1.getNodes().get(i),o2.getNodes().get(e)));		
	    	}
	    }
		
		return simMatrix;
	}

	private Double getSimilarityValue(Node node1,
			Node node2) {
		
		double matchesN1 = 0;
		double matchesN2 = 0;
		
		List propN1 = node1.getProperties();	
		List propN2 = node2.getProperties();
		
		if(propN1.size() + propN2.size() >0){
		
			for(Object prop:propN1){
				if(propN2.contains(prop)){
					matchesN1++;
				}
			}
			
			for(Object prop:propN2){
				if(propN1.contains(prop)){
					matchesN2++;
				}
			}
			
			return (matchesN1+matchesN2)/(propN1.size() + propN2.size());
		
		}
		
		return 0.0;
	}

	@Override
	public Double getWeight() {
		return Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.P_WEIGHT));
	}

}

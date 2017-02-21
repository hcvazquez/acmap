package conformance.mapper.similarity.function.hierarchical;

import java.util.List;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.definition.Element;
import conformance.mapper.definition.Node;
import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityDataManager;
import conformance.mapper.similarity.SimilarityFunction;
import conformance.mapper.similarity.SimilarityMatrix;
import conformance.mapper.similarity.SimilarityMatrixImp;

public class HierarchicalFunction implements SimilarityFunction {

	@Deprecated
	public SimilarityMatrix execute_deprecated(Ontology o1, Ontology o2) {
		
		SimilarityMatrixImp simMatrix = new SimilarityMatrixImp("Hierarchical Similarity", this);
		List<SimilarityMatrix> simMatrixList= SimilarityDataManager.getInstance().getSimilarityData();
			
		//Cargo todo con cero
	/*    for(int i=0; i<o1.getNodes().size();i++){
	    	for(int e=0; e<o2.getNodes().size();e++){
	    		if(!map.getElement2().equals(o2.getNodes().get(e)) && map.getElement2().isInTheSamePath(o2.getNodes().get(e))){
		    		PairOE pair = new PairOE(o1.getNodes().get(i),o2.getNodes().get(e));
					double confidenceAcum = 0.0;
					for(SimilarityMatrix matrix:simMatrixList){
						confidenceAcum += matrix.getPairValue(pair);
					}					
					simMatrix.setPairValue(pair, confidenceAcum/simMatrixList.size());		
	    		}
	    	}
	    }*/

		List<PairOE> listPairs = SimilarityDataManager.getInstance().getSeedMacher();
	    
		//Por cada clase que mapea a un componente mapeo al mismo componente las clases del mismo paquete
		for(PairOE map:listPairs){
			for(int e=0; e<o2.getNodes().size();e++){
				if(!map.getElement2().equals(o2.getNodes().get(e)) && map.getElement2().isInTheSamePath(o2.getNodes().get(e))){
					PairOE pairOE = new PairOE(map.getElement1(),o2.getNodes().get(e));
					simMatrix.setPairValue(pairOE, getSimilarityValue(map.getElement1(),o2.getNodes().get(e)));
				}
			}
		}
		
		return simMatrix;
	}
	
	@Override
	public SimilarityMatrix execute(Ontology o1, Ontology o2) {
		
		SimilarityMatrixImp simMatrix = new SimilarityMatrixImp("Hierarchical Similarity",this);
		
		//Cargo todo con cero
	    for(int i=0; i<o1.getNodes().size();i++){
	    	for(int e=0; e<o2.getNodes().size();e++){
	    		PairOE pairOE = new PairOE(o1.getNodes().get(i),o2.getNodes().get(e));
				simMatrix.setPairValue(pairOE, 0.0);		
	    	}
	    }

		List<PairOE> listPairs = SimilarityDataManager.getInstance().getSeedMacher();
	    
		//Por cada clase que mapea a un componente mapeo al mismo componente las clases del mismo paquete
		for(PairOE map:listPairs){
			for(int e=0; e<o2.getNodes().size();e++){
				if(!map.getElement2().equals(o2.getNodes().get(e)) && map.getElement2().isInTheSamePath(o2.getNodes().get(e))){
					PairOE pairOE = new PairOE(map.getElement1(),o2.getNodes().get(e));
					simMatrix.setPairValue(pairOE, getSimilarityValue(map.getElement1(),o2.getNodes().get(e)));
				}
			}
		}
		
		return simMatrix;
	}
	
	private Double getSimilarityValue(Element component, Node clazz) {		
		return 1.0;
	}
	
	@Override
	public Double getWeight() {
		return Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.H_WEIGHT));
	}
}

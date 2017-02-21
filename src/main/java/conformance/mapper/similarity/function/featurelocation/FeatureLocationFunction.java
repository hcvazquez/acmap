package conformance.mapper.similarity.function.featurelocation;

import java.io.FileInputStream;
import java.util.Properties;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityFunction;
import conformance.mapper.similarity.SimilarityMatrix;
import conformance.mapper.similarity.SimilarityMatrixImp;

public class FeatureLocationFunction implements SimilarityFunction {

	@Override
	public SimilarityMatrix execute(Ontology o1, Ontology o2) {
		
		SimilarityMatrixImp simMatrix = new SimilarityMatrixImp("Feature Location Similarity", this);
		
		Properties property = new Properties();
		try {
			property.load(new FileInputStream(ConfigurationManager.getProperty(ConfigurationManager.FL_FILE_PATH)));
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	    for(int i=0; i<o1.getNodes().size();i++){
	    	for(int e=0; e<o2.getNodes().size();e++){
	    		PairOE pairOE = new PairOE(o1.getNodes().get(i),o2.getNodes().get(e));
	    		String propertyKey = o2.getNodes().get(e).getShortName()+">X<"+o1.getNodes().get(i).getName();
    			simMatrix.setPairValue(pairOE, getSimilarityValue(property, propertyKey));
	    	}
	    }
		
		return simMatrix;
	}

	private Double getSimilarityValue(Properties property, String propertyKey) {		
		String value = property.getProperty(propertyKey);
		if(value!=null){
			return Double.valueOf(value.replaceAll(",", "."));
		}else{
			return 0.0;
		}
	}

	@Override
	public Double getWeight() {
		return Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.FL_WEIGHT));
	}
}

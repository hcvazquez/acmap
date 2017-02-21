package conformance.mapper.similarity;

import conformance.mapper.definition.Ontology;

public interface SimilarityFunction {
	
	public SimilarityMatrix execute(Ontology o1, Ontology o2);

	public Double getWeight();
	
}

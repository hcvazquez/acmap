package ontology.alignment.similarity;

import java.util.List;

import ontology.alignment.definition.Ontology;
import ontology.alignment.pairing.PairOE;

public interface SimilarityFunction {
	
	public SimilarityMatrix execute(Ontology o1, Ontology o2);
	
}

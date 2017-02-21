package ontology.alignment.similarity;

import java.util.Set;

import ontology.alignment.pairing.PairOE;

public interface SimilarityMatrix {
	
	public Double getPairValue(PairOE pair);
	
	public void setPairValue(PairOE pair, Double value);
	
	public String getName();
	
	public Set<PairOE> getPairs();
}

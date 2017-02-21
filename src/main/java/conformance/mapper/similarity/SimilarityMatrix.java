package conformance.mapper.similarity;

import java.util.Set;

import conformance.mapper.pairing.PairOE;

public interface SimilarityMatrix {
	
	public Double getPairValue(PairOE pair);
	
	public void setPairValue(PairOE pair, Double value);
	
	public String getName();
	
	public Set<PairOE> getPairs();
	
	public SimilarityFunction getSimilarityFunction();
	
}

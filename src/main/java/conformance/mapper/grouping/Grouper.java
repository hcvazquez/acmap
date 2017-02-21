package conformance.mapper.grouping;

import java.util.ArrayList;
import java.util.List;

import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityMatrix;

public interface Grouper {
	
	public ArrayList<ArrayList<PairOE>> groupSolutions(List<PairOE> pairs, List<SimilarityMatrix> simMatrix); 

}

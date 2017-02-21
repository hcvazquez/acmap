package ontology.alignment.grouping;

import java.util.ArrayList;
import java.util.List;

import ontology.alignment.pairing.PairOE;
import ontology.alignment.similarity.SimilarityMatrix;

public interface Grouper {
	
	public ArrayList<ArrayList<PairOE>> groupSolutions(List<PairOE> pairs, List<SimilarityMatrix> simMatrix); 

}

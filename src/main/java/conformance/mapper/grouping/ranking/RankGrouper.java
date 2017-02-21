package conformance.mapper.grouping.ranking;

import java.util.ArrayList;
import java.util.List;

import conformance.mapper.grouping.Grouper;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityMatrix;

public class RankGrouper implements Grouper {

	public RankGrouper() {
		super();
	}

	@Override
	public ArrayList<ArrayList<PairOE>> groupSolutions(List<PairOE> pairs,
			List<SimilarityMatrix> simMatrix) {
		
		ArrayList<ArrayList<PairOE>> result = new ArrayList<ArrayList<PairOE>>();
		ArrayList<PairOE> ranking= new ArrayList<PairOE>();
		
		for(PairOE pair:pairs){
			double confidenceAcum = 0.0;
//			System.out.print(pair.getElement1().getShortName()+" "+pair.getElement2().getShortName()+" ");
			for(SimilarityMatrix matrix:simMatrix){
				Double weight = matrix.getSimilarityFunction().getWeight();
				confidenceAcum += weight.doubleValue() * matrix.getPairValue(pair).doubleValue();
			}
//			System.out.println();
			double confidence = confidenceAcum/(double)simMatrix.size(); 
			pair.setConfidence(confidence>1?1:confidence);
			ranking.add(pair);
		}

		result.add(ranking);
		
		return result;
	}

}

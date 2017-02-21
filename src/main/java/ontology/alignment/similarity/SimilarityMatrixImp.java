package ontology.alignment.similarity;

import java.util.HashMap;
import java.util.Set;

import ontology.alignment.pairing.PairOE;

public class SimilarityMatrixImp implements SimilarityMatrix {

	HashMap<PairOE,Double> matrix;
	String name;

	public SimilarityMatrixImp(String name) {
		super();
		this.matrix = new HashMap<PairOE, Double>();
		this.name = name;
	}
		
	public HashMap<PairOE, Double> getMatrix() {
		return matrix;
	}

	public void setMatrix(HashMap<PairOE, Double> matrix) {
		this.matrix = matrix;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Double getPairValue(PairOE pair) {
		return matrix.get(pair);
	}

	@Override
	public void setPairValue(PairOE pair, Double value) {
		matrix.put(pair, value);
	}

	@Override
	public String getName() {
		return name;
	}
	
	public Set<PairOE> getPairs(){
		return matrix.keySet();
	}
	
}

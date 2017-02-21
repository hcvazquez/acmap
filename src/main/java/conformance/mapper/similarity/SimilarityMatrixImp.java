package conformance.mapper.similarity;

import java.util.HashMap;
import java.util.Set;

import conformance.mapper.pairing.PairOE;

public class SimilarityMatrixImp implements SimilarityMatrix {

	HashMap<PairOE,Double> matrix;
	String name;
	SimilarityFunction function;

	public SimilarityMatrixImp(String name, SimilarityFunction function) {
		super();
		this.matrix = new HashMap<PairOE, Double>();
		this.name = name;
		this.function = function;
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
		if(matrix.get(pair)!=null){
			return matrix.get(pair);
		}
		return 0.0;
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

	@Override
	public SimilarityFunction getSimilarityFunction() {
		return function;
	}
	
}

package conformance.mapper.similarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import conformance.mapper.pairing.PairOE;

public class SimilarityDataManager {

	protected static SimilarityDataManager instance;
	protected List<SimilarityMatrix> similarityData;
	List<PairOE> initialMap;
	
	public static SimilarityDataManager getInstance(){
		if(instance==null){
			instance = new SimilarityDataManager();
		}
		return instance;
	}
	
	private SimilarityDataManager(){
		 similarityData = new ArrayList<SimilarityMatrix>();
		 initialMap = new ArrayList<PairOE>();
	}
	
	public void addSimilarityData(SimilarityMatrix matrix){
		similarityData.add(matrix);
	}

	public List<SimilarityMatrix> getSimilarityData() {
		return similarityData;
	}
	
	public List<PairOE> getSeedMacher(){		
		if(initialMap.isEmpty()){
			for(SimilarityMatrix sm:similarityData){
				Set<PairOE> pairs = sm.getPairs();
				for(PairOE pair:pairs){
					if(sm.getPairValue(pair)==1 && !initialMap.contains(pair)){
						initialMap.add(pair);
					}
				}
			}		
		}
		return initialMap;
	}
 	
	public void clear(){
		similarityData.clear();
		initialMap.clear();
	}

	public void setInitialMap(List<PairOE> initialMap) {
		this.initialMap = initialMap;
	}
	
	public void printSimilarityData(){
		for(SimilarityMatrix sm:similarityData){
			System.out.println("Similarity "+sm.getName());
			Set<PairOE> pairs = sm.getPairs();
			for(PairOE pair:pairs){
				System.out.println(pair.getKey()+" -> "+sm.getPairValue(pair));
			}
		}	
	}

	public double getMapProbability(PairOE pair) {
		
		double sum = 0;
		
		for (int i = 0; i < similarityData.size(); i++) {
			if(similarityData.get(i).getPairValue(pair)==null){
				similarityData.get(i).setPairValue(pair, 0.0);
			}
			sum = sum + similarityData.get(i).getPairValue(pair);
		}
		return sum;
	}
	
	
	
}

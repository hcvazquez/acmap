package ontology.alignment.similarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ontology.alignment.pairing.PairOE;

public class SimilarityDataManager {

	protected static SimilarityDataManager instance;
	protected List<SimilarityMatrix> similarityData;
	
	public static SimilarityDataManager getInstance(){
		if(instance==null){
			instance = new SimilarityDataManager();
		}
		return instance;
	}
	
	private SimilarityDataManager(){
		 similarityData = new ArrayList<SimilarityMatrix>();
	}
	
	public void addSimilarityData(SimilarityMatrix matrix){
		similarityData.add(matrix);
	}

	public List<SimilarityMatrix> getSimilarityData() {
		return similarityData;
	}
	
	public List<PairOE> getSeedMacher(){
		
		List<PairOE> list = new ArrayList<PairOE>();
		
		for(SimilarityMatrix sm:similarityData){
			Set<PairOE> pairs = sm.getPairs();
			for(PairOE pair:pairs){
				if(sm.getPairValue(pair)==1 && !list.contains(pair)){
					//System.out.println(pair.toString());
					list.add(pair);
				}
			}
		}
		
		return list;
	}
 	
	public void clear(){
		similarityData.clear();
	}
	
	
	
}

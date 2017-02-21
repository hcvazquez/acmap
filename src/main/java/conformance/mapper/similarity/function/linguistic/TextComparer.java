package conformance.mapper.similarity.function.linguistic;

import java.util.HashMap;

import semilar.data.Sentence;
import semilar.sentencemetrics.AbstractComparer;

public class TextComparer {
	
	protected HashMap<String,Float> cache;
	private static TextComparer instance;
	
	private TextComparer(){
		this.cache = new HashMap<String,Float>();
	}
	
	public static TextComparer getInstance(){
		if(instance == null){
			instance = new TextComparer();
		}
		return instance;
	}
	
	public float computeSimilarity(Sentence s1, Sentence s2){
		Float value = cache.get(s1.getRawForm()+s2.getRawForm());
		if(value == null){
			AbstractComparer comparer = LinguisticManager.getInstance().getLinguisticComparer();
			value = comparer.computeSimilarity(s1,s2);
			cache.put(s1.getRawForm()+s2.getRawForm(), Float.valueOf(value));
		}
		return value;
		
	}

}

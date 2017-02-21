package conformance.mapper.similarity.function.linguistic;

import semilar.data.Sentence;

public class Comparison {
	
	Sentence s1;
	Sentence s2;
	
	public Comparison(Sentence s1, Sentence s2) {
		super();
		this.s1 = s1;
		this.s2 = s2;
	}
	
	public boolean equals(Object other){
		if(other!=null){
			if(other instanceof Comparison){
				if(  (s1.getRawForm().equals(((Comparison) other).getS1().getRawForm()) && s2.getRawForm().equals(((Comparison) other).getS2()))
				  || (s2.getRawForm().equals(((Comparison) other).getS1().getRawForm()) && s1.getRawForm().equals(((Comparison) other).getS2()))
					){
					return true;
				}
			}
		}
		return false;
	}

	public Sentence getS1() {
		return s1;
	}

	public Sentence getS2() {
		return s2;
	}
	
	

}

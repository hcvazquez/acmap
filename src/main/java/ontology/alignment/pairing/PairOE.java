package ontology.alignment.pairing;

import ontology.alignment.definition.Element;

public class PairOE {
	
	Element element1;
	Element element2;
	
	public PairOE(Element element1, Element element2) {
		super();
		this.element1 = element1;
		this.element2 = element2;
	}

	public Element getElement1() {
		return element1;
	}

	public Element getElement2() {
		return element2;
	}

	public void setElement1(Element element1) {
		this.element1 = element1;
	}

	public void setElement2(Element element2) {
		this.element2 = element2;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof PairOE){
			if(this.getKey().equals(((PairOE)obj).getKey())){
				return true;
			}
		}
		return false;
		
	}
	
    @Override
    public int hashCode() {
        return getKey().length();
    }
	
	public String getKey(){
		return element1.getName() + " >X< " + element2.getName();
	}
	
	public String toString(){
		return getKey();
	}
	

}

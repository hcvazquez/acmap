package ontology.alignment.pairing;

import java.util.ArrayList;
import java.util.List;

import ontology.alignment.definition.Ontology;


public class PairingManager {
	
	protected static PairingManager instance;
	
	public static PairingManager getInstance(){
		if(instance==null){
			instance = new PairingManager();
		}
		return instance;
	}
	
	public List<PairOE> buildEntirePairs(Ontology o1, Ontology o2){
		
		List<PairOE> pairs = new ArrayList<PairOE>();
		
		pairs.addAll(buildNodePairs(o1, o2));
		
		pairs.addAll(buildLinksPairs(o1, o2));
		
		return pairs;

	}
	
	public List<PairOE> buildNodePairs(Ontology o1, Ontology o2){
		
		List<PairOE> pairs = new ArrayList<PairOE>();
		
		for(int i=0; i<o1.getNodes().size();i++){
			for(int e=0; e<o2.getNodes().size();e++){
				pairs.add(new PairOE(o1.getNodes().get(i), o2.getNodes().get(e)));
			}
		}
		
		return pairs;

	}
	
	public List<PairOE> buildLinksPairs(Ontology o1, Ontology o2){
		
		List<PairOE> pairs = new ArrayList<PairOE>();
		
		for(int i=0; i<o1.getLinks().size();i++){
			for(int e=0; e<o2.getLinks().size();e++){
				pairs.add(new PairOE(o1.getLinks().get(i), o2.getLinks().get(e)));
			}
		}
		
		return pairs;

	}
	
	
}

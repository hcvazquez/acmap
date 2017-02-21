package conformance.mapper.core;

import java.util.ArrayList;
import java.util.List;

import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;

public interface Alignment {

	public ArrayList<ArrayList<PairOE>> executeAlignment(Ontology o1, Ontology o2, List<PairOE> initialMap);
	public ArrayList<PairOE> getSolution(ArrayList<ArrayList<PairOE>> clusters);
}

package ontology.alignment.core;

import java.util.List;

import ontology.alignment.definition.Ontology;
import ontology.alignment.pairing.PairOE;

public interface Alignment {

	public List<PairOE> executeAlignment(Ontology o1, Ontology o2);
}

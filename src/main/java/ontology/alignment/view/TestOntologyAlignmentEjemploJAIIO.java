package ontology.alignment.view;

import java.util.ArrayList;

import ontology.alignment.configuration.ConfigurationManager;
import ontology.alignment.core.AlignmentImp;
import ontology.alignment.definition.Ontology;
import ontology.alignment.pairing.PairOE;
import ontology.alignment.parser.RDFtoOntologyGraph;
import ontology.alignment.utils.MapVerifyManager;

public class TestOntologyAlignmentEjemploJAIIO {

	public static void main(String[] args) {
		
		Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontoarchdiagramEJJAIIO.rdf");
		Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontojavacodeEJJAIOO.rdf");
		
		ConfigurationManager.setProperty("numCluster", String.valueOf(3));
			
		AlignmentImp alignament = new AlignmentImp();
		
		ArrayList<ArrayList<PairOE>> clusters = alignament.executeAlignment(o1, o2);
		
		ArrayList<PairOE> solution = alignament.getSolution(clusters);
		
		for(PairOE pair:solution){
			System.out.println(pair.getElement1().getName()+" = "+pair.getElement2().getName());
		}
		
	}
}

package conformance.mapper.view.test;

import java.util.ArrayList;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.core.AlignmentImp;
import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.parser.RDFtoOntologyGraph;
import conformance.mapper.similarity.SimilarityDataManager;
import conformance.mapper.utils.GephiExporter;

public class TestOntologyAlignmentEjemploJAIIO {

	public static void main(String[] args) {
		
		Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontoarchdiagramEJJAIIO.rdf");
		Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile("file:/C:/benchmarks/ontojavacodeEJJAIOO.rdf");
		
		ConfigurationManager.setProperty("numCluster", String.valueOf(3));
			
		AlignmentImp alignament = new AlignmentImp();
		
		ArrayList<ArrayList<PairOE>> clusters = alignament.executeAlignment(o1, o2, new ArrayList<PairOE>());
		
		ArrayList<PairOE> solution = alignament.getSolution(clusters);
		
		System.out.println();System.out.println();System.out.println("SOLUCION");
		for(PairOE pair:solution){
			System.out.println(pair.getElement1().getName()+" = "+pair.getElement2().getName());
			//System.out.print("->"+SimilarityDataManager.getInstance().getSimilarityData().get(0).getPairValue(pair));
			//System.out.print("-"+SimilarityDataManager.getInstance().getSimilarityData().get(1).getPairValue(pair));
			//System.out.println("-"+SimilarityDataManager.getInstance().getSimilarityData().get(2).getPairValue(pair));
		}
		System.out.println("FIN SOLUCION");System.out.println();System.out.println();
		
		System.out.println();System.out.println();System.out.println("CLUSTERS");
		int count=0;
		for(ArrayList<PairOE> cluster:clusters){
			System.out.println();System.out.println("cluster "+count++);
			for(PairOE pair:cluster){
				System.out.print(pair.getElement1().getName()+" = "+pair.getElement2().getName());
				double value = SimilarityDataManager.getInstance().getSimilarityData().get(0).getPairValue(pair)+
								SimilarityDataManager.getInstance().getSimilarityData().get(1).getPairValue(pair)+
									SimilarityDataManager.getInstance().getSimilarityData().get(2).getPairValue(pair);
				
				System.out.println(" -> "+value/3);
			}
			System.out.println();System.out.println("FIN cluster "+(count-1));
		}
		System.out.println("FIN CLUSTERS");System.out.println();System.out.println();
		
		SimilarityDataManager.getInstance().printSimilarityData();
		
		GephiExporter.getInstance().exportToGephiFile("C:\\Users\\Hernan\\Desktop\\solution", solution);
		
	}
}

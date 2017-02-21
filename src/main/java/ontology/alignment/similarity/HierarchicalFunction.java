package ontology.alignment.similarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ontology.alignment.definition.Ontology;
import ontology.alignment.definition.Node;
import ontology.alignment.pairing.PairOE;

import org.w3c.rdf.model.Model;
import org.w3c.rdf.model.ModelException;
import org.w3c.rdf.model.NodeFactory;
import org.w3c.rdf.model.Resource;
import org.w3c.rdf.util.RDFFactory;
import org.w3c.rdf.util.RDFFactoryImpl;

import com.interdataworking.mm.alg.MapPair;
import com.interdataworking.mm.alg.Match;

public class HierarchicalFunction implements SimilarityFunction {

	@Override
	public SimilarityMatrix execute(Ontology o1, Ontology o2) {
		
		SimilarityMatrixImp simMatrix = new SimilarityMatrixImp("Hierarchical Similarity");

		List<PairOE> listPairs = SimilarityDataManager.getInstance().getSeedMacher();
	    
	    for(int i=0; i<o1.getNodes().size();i++){
	    	for(int e=0; e<o2.getNodes().size();e++){
	    		PairOE pairOE = new PairOE(o1.getNodes().get(i),o2.getNodes().get(e));
				simMatrix.setPairValue(pairOE, getSimilarityValue(o1.getNodes().get(i),o2.getNodes().get(e), listPairs));		
	    	}
	    }
		
		return simMatrix;
	}

	private Double getSimilarityValue(Node node1, Node node2,
			List<PairOE> listPairs) {
		
		return null;
	}
}

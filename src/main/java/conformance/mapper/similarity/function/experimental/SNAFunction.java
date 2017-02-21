package conformance.mapper.similarity.function.experimental;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.definition.Link;
import conformance.mapper.definition.Node;
import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityFunction;
import conformance.mapper.similarity.SimilarityMatrix;
import conformance.mapper.similarity.SimilarityMatrixImp;
import edu.uci.ics.jung.algorithms.importance.MarkovCentrality;
import edu.uci.ics.jung.algorithms.scoring.BarycenterScorer;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.scoring.DistanceCentralityScorer;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;

public class SNAFunction implements SimilarityFunction{

	@Override
	public SimilarityMatrix execute(Ontology o1, Ontology o2) {
		
		SimilarityMatrixImp simMatrix = new SimilarityMatrixImp("SNA Similarity",this);
		
		//Compute SNA Metrics for the ontology 1
		
		Graph<Node, Link> g1 = new DirectedSparseMultigraph<Node, Link>();
		
		for(Link link:o1.getLinks()){
			g1.addEdge(link, link.getSource(), link.getDestiny());
		}
		
		//Metrics
		DistanceCentralityScorer<Node,Link> closeness1 = new ClosenessCentrality<Node, Link>(g1);
		BetweennessCentrality<Node,Link> betweenness1 = new BetweennessCentrality<Node, Link>(g1);
		
		//Compute SNA Metrics for the ontology 2
			
		Graph<Node, Link> g2 = new DirectedSparseMultigraph<Node, Link>();
		
		for(Link link:o2.getLinks()){
			g2.addEdge(link, link.getSource(), link.getDestiny());
		}
		
		//Metrics
		DistanceCentralityScorer<Node,Link> closeness2 = new ClosenessCentrality<Node, Link>(g2);
		BetweennessCentrality<Node,Link> betweenness2 = new BetweennessCentrality<Node, Link>(g2);
		
		
		//Build Similarity MAtrix
		HashMap<PairOE, Double> simBetweenness = new HashMap<PairOE, Double>();
		
	    for(int i=0; i<o1.getNodes().size();i++){
	    	for(int e=0; e<o2.getNodes().size();e++){
	    		PairOE pairOE = new PairOE(o1.getNodes().get(i),o2.getNodes().get(e));
	    		if(betweenness1.getVertexScore(o1.getNodes().get(i))!=null &&
	    		   betweenness2.getVertexScore(o2.getNodes().get(e))!=null &&
	    		   betweenness1.getVertexScore(o1.getNodes().get(i))!=0 &&
	    		   betweenness2.getVertexScore(o2.getNodes().get(e))!=0 &&
	    		   betweenness1.getVertexScore(o1.getNodes().get(i)).isNaN() &&
	    		   betweenness2.getVertexScore(o2.getNodes().get(e)).isNaN()){
	    			
	    		
		    		simBetweenness.put(pairOE, 
		    				Double.valueOf(
		    				betweenness1.getVertexScore(o1.getNodes().get(i))<
		    				betweenness2.getVertexScore(o2.getNodes().get(e))
		    				?
		    				betweenness1.getVertexScore(o1.getNodes().get(i))/
		    				betweenness2.getVertexScore(o2.getNodes().get(e))
		    				:
		    				betweenness2.getVertexScore(o2.getNodes().get(e))/
		    				betweenness1.getVertexScore(o1.getNodes().get(i)) 
		    				));	
	    		}else{
	    			simBetweenness.put(pairOE, 0.0);
	    		}
	    		System.out.println(pairOE + "->" + simBetweenness.get(pairOE));    		
	    	}
	    }
	    
	    HashMap<PairOE,Double> simCloseness = new HashMap<PairOE, Double>();
		
	    for(int i=0; i<o1.getNodes().size();i++){
	    	for(int e=0; e<o2.getNodes().size();e++){
	    		PairOE pairOE = new PairOE(o1.getNodes().get(i),o2.getNodes().get(e));
	    		if(closeness1.getVertexScore(o1.getNodes().get(i))!=null &&
	    		   closeness2.getVertexScore(o2.getNodes().get(e))!=null &&
	 	    	   closeness1.getVertexScore(o1.getNodes().get(i))!=0 &&
	 	    	   closeness2.getVertexScore(o2.getNodes().get(e))!=0 &&
	 	    	   !closeness1.getVertexScore(o1.getNodes().get(i)).isNaN() &&
	 	    	   !closeness2.getVertexScore(o2.getNodes().get(e)).isNaN() ){
	    			  		
		    		simCloseness.put(pairOE, 
		    				Double.valueOf(
		    				closeness1.getVertexScore(o1.getNodes().get(i))<
		    				closeness2.getVertexScore(o2.getNodes().get(e))
		    				?
		    				closeness1.getVertexScore(o1.getNodes().get(i))/
		    				closeness2.getVertexScore(o2.getNodes().get(e))
		    				:
		    				closeness2.getVertexScore(o2.getNodes().get(e))/
		    				closeness1.getVertexScore(o1.getNodes().get(i)) 
		    				));
		    		
	    		}else{
	    			simCloseness.put(pairOE, 0.0);
	    		}
	    		System.out.println(pairOE + "->" + simCloseness.get(pairOE));
	    	}
	    }
		
		
		
	    for(int i=0; i<o1.getNodes().size();i++){
	    	for(int e=0; e<o2.getNodes().size();e++){
	    		PairOE pairOE = new PairOE(o1.getNodes().get(i),o2.getNodes().get(e));
				simMatrix.setPairValue(pairOE, Double.valueOf(
							Double.valueOf(
							simBetweenness.get(pairOE)
							+
							simCloseness.get(pairOE)
							)
							/2
						));		
	    	}
	    }
			
		
		return simMatrix;
	}

	@Override
	public Double getWeight() {
		return Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.SNA_WEIGHT));
	}

}

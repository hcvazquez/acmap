package conformance.mapper.similarity.function.experimental;

import java.util.ArrayList;
import java.util.List;

import conformance.evaluator.ConformanceEvaluator;
import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.definition.Link;
import conformance.mapper.definition.Node;
import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityFunction;
import conformance.mapper.similarity.SimilarityMatrix;
import conformance.mapper.similarity.SimilarityMatrixImp;

public class MaximumMatchingFunction implements SimilarityFunction {

	@Override
	public SimilarityMatrix execute(Ontology o1, Ontology o2) {
		
		SimilarityMatrixImp simMatrix = new SimilarityMatrixImp("Maximum Matching Similarity",this);
		
		ArrayList<Node> classes = o2.getNodes();
		ArrayList<Node> components = o1.getNodes();
		
		ArrayList<Integer> assign = new ArrayList<Integer>();
		for(int i = 0; i < classes.size(); i++){
			assign.add(0);
		}
		
		List<PairOE> solution = null;
		int value = evaluateSolution(assign, classes, components, o2.getLinks(), o1.getLinks());
		
		while(hasNext(assign,components.size()+1)){
			 assign = next(assign,components.size()+1);
			 int solutionValue = evaluateSolution(assign, classes, components, o2.getLinks(), o1.getLinks());
			 if(solutionValue > value){
				 solution = generateSolutionForAssign(assign, classes, components);
			 }
		}
		
		for(PairOE pair:solution){
			simMatrix.setPairValue(pair, 1.0);
		}
		
		return simMatrix;
	}

	private ArrayList<Integer> next(ArrayList<Integer> assign, int max) {
		int i=0;
		while(assign.get(i).intValue()==max){
			assign.set(i, 0);
			i++;
		}
		assign.set(i, assign.get(i).intValue()+1);
		return assign;
	}

	private boolean hasNext(ArrayList<Integer> assign, int max) {
		for(Integer i:assign){
			if(max!=i.intValue()){
				return true;
			}
		}
		return false;
	}

	private int evaluateSolution(ArrayList<Integer> assign,
			ArrayList<Node> classes, ArrayList<Node> components, ArrayList<Link> classesLinks, ArrayList<Link> componentsLinks) {
		return ConformanceEvaluator.getInstance().getConformanceValue(classes,components,assign,classesLinks,componentsLinks);
	}

	private List<PairOE> generateSolutionForAssign(ArrayList<Integer> assign,
			ArrayList<Node> classes, ArrayList<Node> components) {
		ArrayList<PairOE> solution = new ArrayList<PairOE>();
		for(int i=0; i< classes.size(); i++){
			int compNumber = assign.get(i).intValue();
			if(compNumber!=0){
				PairOE pair = new PairOE(components.get(compNumber-1), classes.get(i));
				solution.add(pair);
			}
		}
		return solution;
	}


	@Override
	public Double getWeight() {
		return Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.M_WEIGHT));
	}
	
	
}

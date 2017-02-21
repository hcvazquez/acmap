package conformance.evaluator;

import java.util.ArrayList;

import conformance.mapper.definition.Link;
import conformance.mapper.definition.Node;


public class ConformanceEvaluator {
	
	protected static ConformanceEvaluator instance;
	
	public static ConformanceEvaluator getInstance(){
		if(instance==null){
			instance = new ConformanceEvaluator();
		}
		return instance;
	}

	public int getConformanceValue(ArrayList<Node> classes,
			ArrayList<Node> components, ArrayList<Integer> assign,
			ArrayList<Link> classesLinks, ArrayList<Link> componentsLinks) {
		
		int value = 0;
		for(int i=0;i<classes.size();i++){
			ArrayList<Link> clazzDependencies = getClazzDependencies(classes.get(i), classesLinks);
			int compClass1 = assign.get(i);
			for(Link dependencie:clazzDependencies){
				int compClass2 = getCompAssigned(dependencie.getDestiny(), assign, classes);
				if(compClass1!=0 && compClass2!=0){
					if(verifyConvergence(components.get(compClass1-1),components.get(compClass2-1),componentsLinks)){//CONVERGENCIA
						value++;
					}else{//DIVERGENCIA
						value--;
					}
				}else{//AUSENCIA
					value--;
				}
			}
		}		
		
		return value;
	}

	private boolean verifyConvergence(Node node, Node node2,
			ArrayList<Link> componentsLinks) {
		for(Link link:componentsLinks){
			if(link.getSource().getName().equals(node.getName())&&
			   link.getSource().getName().equals(node2.getName())){
			   return true;
			}
		}
		return false;
	}

	private int getCompAssigned(Node destiny, ArrayList<Integer> assign, ArrayList<Node> classes) {
		for(int i=0; i<classes.size();i++){
			if(destiny.getName().equals(classes.get(i).getName())){
				return assign.get(i);
			}
		}
		return 0;
	}

	private ArrayList<Link> getClazzDependencies(Node clazz,
			ArrayList<Link> classesLinks) {
		ArrayList<Link> dependencies = new ArrayList<Link>();
		for(Link link:classesLinks){
			if(link.getSource().getName().equals(clazz.getName())){
				dependencies.add(link);
			}
		}
		return dependencies;
	}
	
	

}

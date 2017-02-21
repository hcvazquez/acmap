package conformance.mapper.definition;

import java.util.ArrayList;
import java.util.HashMap;

public class Ontology {

	HashMap<String,Node> nodes;
	HashMap<String,Link> links;
	
	public Ontology(HashMap<String,Node> nodes, HashMap<String,Link> links) {
		super();
		this.nodes = nodes;
		this.links = links;
	}
	
	public ArrayList<Node> getNodes(){
		return new ArrayList<Node>(nodes.values());
	}
	
	public ArrayList<Link> getLinks(){
		return new ArrayList<Link>(links.values());
	}
	
	public Node getNode(String name){
		return nodes.get(name);
	}
	
	public Link getLink(String name){
		return links.get(name);
	}
	
}

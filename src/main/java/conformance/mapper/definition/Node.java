package conformance.mapper.definition;

import java.util.ArrayList;
import java.util.List;

public class Node extends Element{
	
	List<String> properties;
	
	public Node(int id, String name) {
		super(id,name);
		properties = new ArrayList<String>();
	}
	
	public String toString() { // Always a good idea for debuging
		return name + " id:" + id; // JUNG2 makes good use of these.
	}

	public void addProperty(String property) {
		properties.add(property);
	}
	
	public List<String> getProperties() {
		return properties;
	}
	
}
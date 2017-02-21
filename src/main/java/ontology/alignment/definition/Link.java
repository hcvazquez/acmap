package ontology.alignment.definition;

public class Link extends Element{
	
	protected Node source; //domain
	protected Node destiny; //range
	
	
	public Link(int id, String name, Node source,
			Node destiny) {
		super(id, name);
		this.source = source;
		this.destiny = destiny;
	}
	
	public Link(int id, String name) {
		super(id, name);
	}
	
	public void setSource(Node source) {
		this.source = source;
	}

	public void setDestiny(Node destiny) {
		this.destiny = destiny;
	}

	public Node getSource() {
		return source;
	}

	public Node getDestiny() {
		return destiny;
	}

	public String toString() { // Always a good idea for debuging
		return name + " id:" + id; // JUNG2 makes good use of these.
	}

}

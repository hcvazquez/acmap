package conformance.mapper.definition;

public class Element {
	
	protected int id;
	protected String name;
	
	public Element(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getShortName() {
		if(name.contains(".")){
			String[] pkg = name.split("\\.");
			return pkg[pkg.length-1];
		}
		return name;
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInTheSamePath(Node node) {
		String[] path1 = name.split("\\.");
		String[] path2 = node.getName().split("\\.");
		if(path1.length!=path2.length){
			return false;
		}
		for(int i=0; i<path1.length-1; i++){
			if(!path1[i].equals(path2[i])){
				return false;
			}
		}
		return true;
	}

}

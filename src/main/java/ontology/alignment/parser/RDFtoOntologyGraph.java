package ontology.alignment.parser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import ontology.alignment.definition.Ontology;
import ontology.alignment.definition.Link;
import ontology.alignment.definition.Node;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class RDFtoOntologyGraph {
	
	public static Ontology loadOntologyGraphFromRDFOWLFile(String filename) {
		// create an empty model
		Model model = ModelFactory.createDefaultModel();

		// read the RDF/XML file
		//InputStream in;
		try {
			//in = new FileInputStream(filename);
			//model.read(in,null);
			model.read(filename);
			//in.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int countnodes = 0;
		int countlinks = 0;
		
		HashMap<String, Link> links = new HashMap<String, Link>();
		HashMap<String, Node> nodes = new HashMap<String, Node>();
		
		
		Iterator<Statement> it = model.listStatements();	
		while(it.hasNext()){
			Statement st = it.next();
			if(st.getObject().isResource()){
				String name = ((Resource)st.getObject()).getLocalName();
				if("Class".equals(name)){//es un nodo
					Node node = new Node(countnodes++, st.getSubject().getLocalName());
					nodes.put(st.getSubject().getLocalName(), node);
				}else if("ObjectProperty".equals(name)){//es un link
					Link link = new Link(countlinks++, st.getSubject().getLocalName());
					links.put(st.getSubject().getLocalName(), link);
				}
			}
		}
		
		it = model.listStatements();	
		while(it.hasNext()){	
			Statement st = it.next();
			if(st.getObject().isResource()){
				String name = st.getPredicate().getLocalName();
				if("domain".equals(name)){
					Node node = nodes.get(((Resource)st.getObject()).getLocalName());
					Link link = links.get(st.getSubject().getLocalName());
					link.setSource(node);
				}else if("range".equals(name)){//es un link
					Node node = nodes.get(((Resource)st.getObject()).getLocalName());
					Link link = links.get(st.getSubject().getLocalName());
					link.setDestiny(node);
				}
			}
		}
		
		it = model.listStatements();	
		while(it.hasNext()){	
			Statement st = it.next();
			if(st.getObject().isLiteral()){
				String name = st.getPredicate().getLocalName();
				if("label".equals(name)){
					Node node = nodes.get(st.getSubject().getLocalName());
					node.addProperty(st.getLiteral().getString());
				}
			}
		}
		
		return new Ontology(nodes, links);
		
	}

}

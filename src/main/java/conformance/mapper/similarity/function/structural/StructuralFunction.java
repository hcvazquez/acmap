package conformance.mapper.similarity.function.structural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.w3c.rdf.model.Model;
import org.w3c.rdf.model.ModelException;
import org.w3c.rdf.model.NodeFactory;
import org.w3c.rdf.model.Resource;
import org.w3c.rdf.util.RDFFactory;
import org.w3c.rdf.util.RDFFactoryImpl;

import com.interdataworking.mm.alg.MapPair;
import com.interdataworking.mm.alg.Match;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.definition.Node;
import conformance.mapper.definition.Ontology;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityDataManager;
import conformance.mapper.similarity.SimilarityFunction;
import conformance.mapper.similarity.SimilarityMatrix;
import conformance.mapper.similarity.SimilarityMatrixImp;

public class StructuralFunction implements SimilarityFunction {

	@Override
	public SimilarityMatrix execute(Ontology o1, Ontology o2) {
		// TODO Auto-generated method stub
		RDFFactory rf = new RDFFactoryImpl();
		NodeFactory nf = rf.getNodeFactory();
		
		
		SimilarityMatrixImp simMatrix = new SimilarityMatrixImp("Structural Similarity",this);

		try{
		
			// create graph/model A
			Model A = rf.createModel();
			
			HashMap<String, Resource> resources = new HashMap<String, Resource>();
			
			Resource uniqueLink = nf.createResource("l1");//los links son todos iguales creo, tengo que analizar bien esto
			
			ArrayList<String> control = new ArrayList<String>();
			
			ArrayList<Resource> nodosA = new ArrayList<Resource>();
			
			ArrayList<Resource> nodosB = new ArrayList<Resource>();
			
			for(int i=0; i<o1.getLinks().size();i++){
				Resource source;
				Resource destiny;
				
				if(!resources.containsKey(o1.getLinks().get(i).getSource().getName())){
					source = nf.createResource(o1.getLinks().get(i).getSource().getName());
					resources.put(o1.getLinks().get(i).getSource().getName(), source);
				}else{
					source = 
							resources.get(o1.getLinks().get(i).getSource().getName());
				}
				
				if(!resources.containsKey(o1.getLinks().get(i).getDestiny().getName())){
					destiny = nf.createResource(o1.getLinks().get(i).getDestiny().getName());
					resources.put(o1.getLinks().get(i).getDestiny().getName(), destiny);
				}else{
					destiny = 
							resources.get(o1.getLinks().get(i).getDestiny().getName());
				}

				if(!control.contains(o1.getLinks().get(i).getSource().getName()+o1.getLinks().get(i).getDestiny().getName())){
					A.add(nf.createStatement(source, uniqueLink, destiny));
					control.add(o1.getLinks().get(i).getSource().getName()+o1.getLinks().get(i).getDestiny().getName());
					if(!nodosA.contains(source)){
						nodosA.add(source);
					}
					if(!nodosA.contains(destiny)){
						nodosA.add(destiny);
					}
				}
			}
			
			control = new ArrayList<String>();
			
			// create graph/model B
			Model B = rf.createModel();
			
			for(int i=0; i<o2.getLinks().size();i++){
				Resource source;
				Resource destiny;
				
				if(o2.getLinks().get(i).getSource().getName()==null || o2.getLinks().get(i).getDestiny().getName()==null || o2.getLinks().get(i).getName()==null ){
					System.out.println("NULL!");
				}
				
				if(!resources.containsKey(o2.getLinks().get(i).getSource().getName())){
					source = nf.createResource(o2.getLinks().get(i).getSource().getName());
					resources.put(o2.getLinks().get(i).getSource().getName(), source);
				}else{
					source = 
							resources.get(o2.getLinks().get(i).getSource().getName());
				}
				
				if(!resources.containsKey(o2.getLinks().get(i).getDestiny().getName())){
					destiny = nf.createResource(o2.getLinks().get(i).getDestiny().getName());
					resources.put(o2.getLinks().get(i).getDestiny().getName(), destiny);
				}else{
					destiny = 
							resources.get(o2.getLinks().get(i).getDestiny().getName());
				}
					
				if(!control.contains(o2.getLinks().get(i).getSource().getName()+o2.getLinks().get(i).getDestiny().getName())){
					B.add(nf.createStatement(source, uniqueLink, destiny));
					control.add(o2.getLinks().get(i).getSource().getName()+o2.getLinks().get(i).getDestiny().getName());
					if(!nodosB.contains(source)){
						nodosB.add(source);
					}
					if(!nodosB.contains(destiny)){
						nodosB.add(destiny);
					}
				}	
			}
	
			// create an initial mapping which is just a cross-product with 1's as
			// weights
			int count = 0;
			List<PairOE> listPairs = SimilarityDataManager.getInstance().getSeedMacher();
			List<MapPair> initMap = new ArrayList();
			for(int i=0; i<nodosA.size();i++){
				for(int e=0; e<nodosB.size();e++){
					Node on1 = new Node(0, nodosA.get(i).getLocalName());
					Node on2 = new Node(0, nodosB.get(e).getLocalName());
					PairOE pair = new PairOE(on1, on2);
					if(listPairs.contains(pair)){
						initMap.add(new MapPair(nodosA.get(i), nodosB.get(e), 1.0));
					}else{
						initMap.add(new MapPair(nodosA.get(i), nodosB.get(e), 0.0));
					}
				}
			}
			
			// create an initial mapping which is just a cross-product with 1's as
			// weights
	/*		int count = 0;
			List initMap = new ArrayList();
			for(int i=0; i<nodosA.size();i++){
				for(int e=0; e<nodosB.size();e++){
					if(nodosA.get(i).getLocalName().equals(nodosB.get(e).getLocalName())){
						initMap.add(new MapPair(nodosA.get(i), nodosB.get(e), 1.0));
					}else{
						initMap.add(new MapPair(nodosA.get(i), nodosB.get(e), 0.0));
					}
				}
			}*/
			
		/*	initMap.add(new MapPair(resources.get("AlbumController"),resources.get("AlbumController"),1.0));
			initMap.add(new MapPair(resources.get("AlbumListScreen"),resources.get("AlbumListScreen"),1.0));
			initMap.add(new MapPair(resources.get("MediaListController"),resources.get("MediaListController"),1.0));
			initMap.add(new MapPair(resources.get("PhotoViewScreen"),resources.get("PhotoViewScreen"),1.0));
			initMap.add(new MapPair(resources.get("PlayVideoScreen"),resources.get("PlayVideoScreen"),1.0));
			initMap.add(new MapPair(resources.get("MediaController"),resources.get("MediaController"),1.0));
			initMap.add(new MapPair(resources.get("PhotoViewController"),resources.get("PhotoViewController"),1.0));
			initMap.add(new MapPair(resources.get("NewLabelScreen"),resources.get("NewLabelScreen"),1.0));
			initMap.add(new MapPair(resources.get("PlayVideoController"),resources.get("PlayVideoController"),1.0));
			initMap.add(new MapPair(resources.get("NetworkScreen"),resources.get("NetworkScreen"),1.0));
			initMap.add(new MapPair(resources.get("MediaListScreen"),resources.get("MediaListScreen"),1.0));*/
			
			Match sf = new Match();
	
			// Two lines below are used to get the same setting as in the example of
			// the ICDE'02 paper.
			// (In general, this formula won't converge! So better stick to the
			// default values instead)
			/* boolean[] FORMULA_FTF = {false, true, false};
			 sf.formula = FORMULA_FTF;
			 sf.FLOW_GRAPH_TYPE = 1;//FG_PRODUCT;*/
	
			MapPair[] result = sf.getMatch(A, B, initMap);

			for(MapPair pair: result){
				Resource r1 = (Resource) pair.getLeft();
				Resource r2 = (Resource) pair.getRight();
				PairOE pairOE = new PairOE(o1.getNode(r1.getLocalName()),o2.getNode(r2.getLocalName()));
				simMatrix.setPairValue(pairOE, pair.sim);			
			}
		
		}catch(ModelException e){
			System.out.println(e.getMessage());
		}
		
		return simMatrix;
	}

	public static void dump(Collection c) {
		for (Iterator it = c.iterator(); it.hasNext();)
			System.err.println(String.valueOf(it.next()));
	}

	public static void dump(Object[] arr) {

		dump(Arrays.asList(arr));
	}
	
	@Override
	public Double getWeight() {
		return Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.S_WEIGHT));
	}
}

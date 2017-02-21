package conformance.mapper.utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import conformance.mapper.pairing.PairOE;

public class GephiExporter {
	
	private static GephiExporter instance;
	
	private GephiExporter(){
		super();
	}
	
	public static GephiExporter getInstance(){
		if(instance==null){
			instance = new GephiExporter();
		}
		return instance;
	}
	
	public String getHeadXMLForGephi() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <gexf xmlns=\"http://www.gephi.org/gexf\" xmlns:viz=\"http://www.gephi.org/gexf/viz\"> <graph type=\"static\">"+Constants.BREAK;
	}

	public String toXMLForGephiFormat(ArrayList<PairOE> solution) {
		String nodes = "<nodes>"+Constants.BREAK;
		String edges = "<edges>"+Constants.BREAK;
		
		HashMap<String,Integer> componentIDs = new HashMap<String,Integer>();
		int idNodeCount = 0;
		for(PairOE pair:solution){
			int idNode;
			if(componentIDs.get(pair.getElement1().getName())!=null){
				idNode = componentIDs.get(pair.getElement1().getName());
			}else{
				idNode = idNodeCount++;
				componentIDs.put(pair.getElement1().getName(), idNode);
			}
			String node = Constants.TAB + "<node id=\""+idNode+"\" label=\""+ pair.getElement1().getName() + "\">" + Constants.BREAK +
					Constants.DTAB + "<viz:color b=\"35\" g=\"182\" r=\"250\"/>" + Constants.BREAK +
					Constants.DTAB + "<viz:size value=\""+2.0+"\"/>" + Constants.BREAK +
				  Constants.TAB + "</node>";
			nodes += node + Constants.BREAK;
		}
		
		HashMap<String,Integer> codeElements = new HashMap<String,Integer>();
		int idEdgeCount = 0;
		for(PairOE pair:solution){
			int idNode;
			if(codeElements.get(pair.getElement1().getName())!=null){
				idNode = codeElements.get(pair.getElement1().getName());
			}else{
				idNode = idNodeCount++;
				codeElements.put(pair.getElement2().getName(), idNode);
			}
			String node = Constants.TAB + "<node id=\""+idNode+"\" label=\""+ pair.getElement2().getName() + "\">" + Constants.BREAK +
							Constants.DTAB + "<viz:color b=\"35\" g=\"182\" r=\"250\"/>" + Constants.BREAK +
							Constants.DTAB + "<viz:size value=\""+2.0+"\"/>" + Constants.BREAK +
						  Constants.TAB + "</node>";
			nodes += node + Constants.BREAK;
			
			int idEdge = idEdgeCount++;
			String edge = Constants.TAB + "<edge id=\""+idEdge+"\" source=\""+codeElements.get(pair.getElement2().getName())+"\" target=\""+componentIDs.get(pair.getElement1().getName())+"\"/>";
			edges += edge + Constants.BREAK;
		}
		
		
		nodes += "</nodes>"+Constants.BREAK;
		edges += "</edges>"+Constants.BREAK;
		
		return nodes + edges;
	}

	public String getFootXMLForGephi() {
		return "</graph> </gexf>";
	}
	
	public String solutionToGephiString(ArrayList<PairOE> solution){
		return getHeadXMLForGephi()+toXMLForGephiFormat(solution)+getFootXMLForGephi();
	}
	
	public void exportToGephiFile(String path,ArrayList<PairOE> solution){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
        	if(path.indexOf(".gexf")!=-1){
        		fichero = new FileWriter(path);
        	}else{
        		fichero = new FileWriter(path+".gexf");
        	}
            pw = new PrintWriter(fichero);
	        pw.print(solutionToGephiString(solution));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
}

package ontology.alignment.grouping;

import ontology.alignment.configuration.ConfigurationManager;
import weka.clusterers.Clusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class ClusterManager {
	
	private static ClusterManager instance;
	private SimpleKMeans clusterer;
	
	public static ClusterManager getInstance(){
		if(instance==null){
			instance = new ClusterManager();
		}
		return instance;
	}
	
	private ClusterManager(){
		clusterer = new SimpleKMeans();
	}
	
	private void executeClusterer(Instances dataSet){
		
		String[] options = new String[2];
		options[0] = "-I";                 // max. iterations
		options[1] = "100";
		try {
			((SimpleKMeans) clusterer).setNumClusters(Integer.valueOf(ConfigurationManager.getProperty("numCluster")));

//		 clusterer.setPreserveInstancesOrder(true);
		((SimpleKMeans) clusterer).setOptions(options);
		clusterer.buildClusterer(dataSet);    // build the clusterer
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	public Clusterer getClustersForInstances(Instances dataSet){
		executeClusterer(dataSet);
		return clusterer;
	}

	
	
}

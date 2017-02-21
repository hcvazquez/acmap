package conformance.mapper.grouping.clustering;

import java.util.ArrayList;
import java.util.List;

import conformance.mapper.grouping.DataSetManager;
import conformance.mapper.grouping.Grouper;
import conformance.mapper.grouping.clustering.ClusterManager;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityMatrix;
import weka.clusterers.Clusterer;
import weka.core.Instances;

public class ClusterGrouper implements Grouper {

	public ClusterGrouper() {
		super();
	}

	@Override
	public ArrayList<ArrayList<PairOE>> groupSolutions(List<PairOE> pairs,
			List<SimilarityMatrix> simMatrix) {
		
		Instances dataSet = DataSetManager.getInstance().buildDataset4ClusterWithCityBlockDistance(pairs,simMatrix);
		
		Clusterer clusterer = ClusterManager.getInstance().getClustersForInstances(dataSet);
		
		ArrayList<ArrayList<PairOE>> result = new ArrayList<ArrayList<PairOE>>();

		try {
			
			for(int i=0;i<clusterer.numberOfClusters();i++){
				result.add(new ArrayList<PairOE>());
			}
			
			for(PairOE pair:pairs){
				int cluster;
	
					if(DataSetManager.getInstance().getInstanceMap().get(pair)!=null){
						cluster = clusterer.clusterInstance(DataSetManager.getInstance().getInstanceMap().get(pair));
						result.get(cluster).add(pair);
					}else{
						System.out.println(pair.getKey()+" -> NULL");
					}
			}
		
		} catch (Exception e) {
			System.out.println("ERROR");
		}
		return result;
	}

}

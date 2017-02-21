package conformance.mapper.grouping.classification;

import java.util.ArrayList;
import java.util.List;

import conformance.mapper.grouping.DataSetManager;
import conformance.mapper.grouping.Grouper;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.similarity.SimilarityMatrix;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * El primer intento fue utilizar clasificacion pero no dio buenos resultados
 * @author Hernan
 *
 */
@Deprecated
public class ClassificationGrouper implements Grouper {

	public ClassificationGrouper() {
		super();
	}

	@Override
	public ArrayList<ArrayList<PairOE>> groupSolutions(List<PairOE> pairs,
			List<SimilarityMatrix> simMatrix) {
		
		Instances trainingSet = DataSetManager.getInstance().buildDataSet4ClassifierWithPromAttributes(
				pairs,
				simMatrix);
		
		Classifier classifier = ClassifierManager.getInstance().getClassifierForInstances(trainingSet);
		
//		System.out.println("New Clasification");
		ArrayList<ArrayList<PairOE>> result = new ArrayList<ArrayList<PairOE>>();
		result.add(new ArrayList<PairOE>());
		
		for(PairOE pair:pairs){
			double clsLabel = -1;
			try {
				Instance instance = DataSetManager.getInstance().getInstanceMap().get(pair); 
				if(instance !=null){
					if(instance.classIsMissing()){
						clsLabel = classifier.classifyInstance(instance);
					}else{
						clsLabel = instance.classValue();
					}
					if(clsLabel==(trainingSet.attribute(trainingSet.classIndex()).indexOfValue(DataSetManager.STRONGLY_RELATED_ITEMS))){
						result.get(0).add(pair);
					}
				}else{
				}
			} catch (Exception e) {
				System.out.println("ERROR:"+e.getMessage());
			}
		}
		
		return result;
	}

}

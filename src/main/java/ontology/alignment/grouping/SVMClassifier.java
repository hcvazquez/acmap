package ontology.alignment.grouping;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class SVMClassifier extends Classifier {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	Instance limit;
	double minDistance;

	@Override
	public void buildClassifier(Instances instances) throws Exception {
		for(int i=0;i<instances.numInstances();i++){
			Instance instance = instances.instance(i);
			if(instance.classValue()==instances.attribute(instances.classIndex()).indexOfValue(DataSetManager.STRONGLY_RELATED_ITEMS)){
				double distance = distanceToCenterCoordinates(instance);
				if(limit==null){
					limit = instance;
					minDistance = distance;
				}else{
					if(minDistance > distance){
						limit = instance;
						minDistance = distance;
					}
				}
			}
		}
	}
	
	@Override
	public double classifyInstance(Instance instance) throws Exception{
		if(instance.classIsMissing()){
			double distance = distanceToCenterCoordinates(instance);
			if(minDistance > distance){
				instance.setClassValue(instance.classAttribute().indexOfValue(DataSetManager.WEAKLY_RELATED_ITEMS));
				return instance.classAttribute().indexOfValue(DataSetManager.WEAKLY_RELATED_ITEMS);
			}else{
				instance.setClassValue(instance.classAttribute().indexOfValue(DataSetManager.STRONGLY_RELATED_ITEMS));
				return instance.classAttribute().indexOfValue(DataSetManager.STRONGLY_RELATED_ITEMS);
			}
		}else if(instance.classValue()==instance.classAttribute().indexOfValue(DataSetManager.STRONGLY_RELATED_ITEMS)){
				if(limit==null){
					limit = instance;
				}else{
					double distance = distanceToCenterCoordinates(instance);
					if(minDistance > distance){
						limit = instance;
						minDistance = distance;
					}
				}
				return instance.classAttribute().indexOfValue(DataSetManager.STRONGLY_RELATED_ITEMS);
		}else return instance.classAttribute().indexOfValue(DataSetManager.WEAKLY_RELATED_ITEMS);
	}
	
	private double distanceToCenterCoordinates(Instance instance){
		double prom = 0.0;
		for(int i=0; i<instance.numAttributes()-1;i++){
			prom = prom + instance.value(i);
		}
		return (prom/((double)instance.numAttributes()-1));
	}
}

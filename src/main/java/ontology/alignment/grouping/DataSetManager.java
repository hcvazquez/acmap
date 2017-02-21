package ontology.alignment.grouping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import ontology.alignment.configuration.ConfigurationManager;
import ontology.alignment.pairing.PairOE;
import ontology.alignment.similarity.SimilarityMatrix;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class DataSetManager {
	
	public static final String STRONGLY_RELATED_ITEMS = "STRONGLY_RELATED_ITEMS";
	public static final String WEAKLY_RELATED_ITEMS = "WEAKLY_RELATED_ITEMS";
	public static final String ALIGNMENT_RELATIONSHIP = "ALIGNMENT_RELATIONSHIP";

	private static DataSetManager instance;

	protected HashMap<PairOE, Instance> instanceMap;
	
	public static DataSetManager getInstance(){
		if(instance==null){
			instance = new DataSetManager();
		}
		return instance;
	}
	
	private DataSetManager(){
		instanceMap = new HashMap<PairOE, Instance>();
	}

	public Instances buildDataset4Cluster(List<PairOE> pairs, List<SimilarityMatrix> similarityData) {

		FastVector fvWekaAttributes = new FastVector(similarityData.size());

		for (int i = 0; i < similarityData.size(); i++) {
			fvWekaAttributes.addElement(new Attribute(similarityData.get(i)
					.getName()));
		}

		Instances dataSet = new Instances("Training Set", fvWekaAttributes, 10);

		for (PairOE pair : pairs) {
			Instance instance = new Instance(similarityData.size());
			instance.setDataset(dataSet);
			
			for (int i = 0; i < similarityData.size(); i++) {
				if(similarityData.get(i).getPairValue(pair)==null){
					similarityData.get(i).setPairValue(pair, 0.0);
				}
				if (dataSet.attribute(similarityData.get(i).getName()) != null) {
					instance.setValue(
							dataSet.attribute(similarityData.get(i).getName()),
							similarityData.get(i).getPairValue(pair));
				}
			}
			
			instanceMap.put(pair, instance);
			dataSet.add(instance);
		}
		
		return dataSet;
	}
	
	
	public Instances buildDataSet4Classifier(List<PairOE> pairs, List<SimilarityMatrix> similarityData) {

		int numAttr = similarityData.size() +1;
		
		FastVector fvWekaAttributes = new FastVector(numAttr);
		
		for (int i = 0; i < similarityData.size(); i++) {
			fvWekaAttributes.addElement(new Attribute(similarityData.get(i)
					.getName()));
		}
		 
		// Declare the class attribute along with its values
		FastVector fvClassVal = new FastVector(2);
		fvClassVal.addElement(STRONGLY_RELATED_ITEMS);
		fvClassVal.addElement(WEAKLY_RELATED_ITEMS);
		
		Attribute classAttribute = new Attribute(ALIGNMENT_RELATIONSHIP, fvClassVal);
		
		fvWekaAttributes.addElement(classAttribute);

		Instances trainingSet = new Instances("Training Set", fvWekaAttributes, 10);

		trainingSet.setClass(classAttribute);
		
		List<PairOE> sortList = new ArrayList<PairOE>();
		final HashMap<PairOE, Double> promValues = new HashMap<PairOE, Double>();

		for (PairOE pair : pairs) {
			
				Instance instance = new Instance(numAttr);
				instance.setDataset(trainingSet);
				
				for (int i = 0; i < similarityData.size(); i++) {
					if(similarityData.get(i).getPairValue(pair)==null){
						similarityData.get(i).setPairValue(pair, 0.0);
					}
					if (trainingSet.attribute(similarityData.get(i).getName()) != null) {
						instance.setValue(
								trainingSet.attribute(similarityData.get(i).getName()),
								similarityData.get(i).getPairValue(pair));
					}
				}
				
				instanceMap.put(pair, instance);
				double promValue = 0;
				for(int i=0;i<numAttr-1;i++){
					promValue = promValue + instanceMap.get(pair).value(i);
				}
				promValue = promValue/numAttr;
				promValues.put(pair, promValue);
				sortList.add(pair);
		}
		
		sortList.sort(new Comparator<PairOE>() {

			@Override
			public int compare(PairOE arg0, PairOE arg1) {
				if(promValues.get(arg0)>promValues.get(arg1)){
					return -1;
				}else if(promValues.get(arg0)<promValues.get(arg1)){
					return 1;
				}
				return 0;
			}
		});
		
		//System.out.println(STRONGLY_RELATED_ITEMS);
		for(int i=0;
				i<sortList.size()*(Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.TRAINING_PERCENTAGE))/2);
				i++){
			Instance inst = instanceMap.get(sortList.get(i));
			inst.setValue(trainingSet.attribute(ALIGNMENT_RELATIONSHIP), fvClassVal.indexOf(STRONGLY_RELATED_ITEMS));
			trainingSet.add(inst);
			/*for(int e=0;e<100;e++){
				trainingSet.add((Instance) inst.copy());
			}*/
			//System.out.println(sortList.get(i).getKey()+" "+promValues.get(sortList.get(i)));
		}
		
		//System.out.println(WEAKLY_RELATED_ITEMS);
		for(int i=sortList.size()-1;
				i>sortList.size()-(sortList.size()*(Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.TRAINING_PERCENTAGE))/2));
				i--){
			Instance inst = instanceMap.get(sortList.get(i));
			inst.setValue(trainingSet.attribute(ALIGNMENT_RELATIONSHIP), fvClassVal.indexOf(WEAKLY_RELATED_ITEMS));
			trainingSet.add(inst);
			/*for(int e=0;e<100;e++){
				trainingSet.add((Instance) inst.copy());
			}*/
			//System.out.println(sortList.get(i).getKey()+" "+promValues.get(sortList.get(i)));
		}
		
		
		return trainingSet;
	}
	
	public Instances buildDataSet4ClassifierWithPromAttributes(List<PairOE> pairs, List<SimilarityMatrix> similarityData) {
		
		FastVector fvWekaAttributes = new FastVector(2);
		
		fvWekaAttributes.addElement(new Attribute("promAttr"));
		 
		// Declare the class attribute along with its values
		FastVector fvClassVal = new FastVector(2);
		fvClassVal.addElement(STRONGLY_RELATED_ITEMS);
		fvClassVal.addElement(WEAKLY_RELATED_ITEMS);
		
		Attribute classAttribute = new Attribute(ALIGNMENT_RELATIONSHIP, fvClassVal);
		
		fvWekaAttributes.addElement(classAttribute);

		Instances trainingSet = new Instances("Training Set", fvWekaAttributes, 10);
		trainingSet.setClass(classAttribute);
		
		List<PairOE> sortList = new ArrayList<PairOE>();
		final HashMap<PairOE, Double> promValues = new HashMap<PairOE, Double>();

		for (PairOE pair : pairs) {
			
				Instance instance = new Instance(2);
				instance.setDataset(trainingSet);
				
				double promValue = 0;
				
				for (int i = 0; i < similarityData.size(); i++) {
					if(similarityData.get(i).getPairValue(pair)==null){
						similarityData.get(i).setPairValue(pair, 0.0);
					}
					//if (trainingSet.attribute(similarityData.get(i).getName()) != null) {
					promValue = promValue + similarityData.get(i).getPairValue(pair);
					//}
				}
				
				instance.setValue(trainingSet.attribute("promAttr"),promValue/(double)similarityData.size());
				
				instanceMap.put(pair, instance);
				promValues.put(pair, promValue);
				sortList.add(pair);
		}
		
		sortList.sort(new Comparator<PairOE>() {

			@Override
			public int compare(PairOE arg0, PairOE arg1) {
				if(promValues.get(arg0)>promValues.get(arg1)){
					return -1;
				}else if(promValues.get(arg0)<promValues.get(arg1)){
					return 1;
				}
				return 0;
			}
		});
		
	//	System.out.println(STRONGLY_RELATED_ITEMS);
		for(int i=0;
				i<sortList.size()*(Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.TRAINING_PERCENTAGE))/2);
				i++){
			Instance inst = instanceMap.get(sortList.get(i));
			inst.setValue(trainingSet.attribute(ALIGNMENT_RELATIONSHIP), fvClassVal.indexOf(STRONGLY_RELATED_ITEMS));
			trainingSet.add(inst);
	//		System.out.println(sortList.get(i).getKey()+" "+promValues.get(sortList.get(i)));
		}
		
	//	System.out.println(WEAKLY_RELATED_ITEMS);
		for(int i=sortList.size()-1;
				i>sortList.size()-(sortList.size()*(Double.valueOf(ConfigurationManager.getProperty(ConfigurationManager.TRAINING_PERCENTAGE))/2));
				i--){
			Instance inst = instanceMap.get(sortList.get(i));
			inst.setValue(trainingSet.attribute(ALIGNMENT_RELATIONSHIP), fvClassVal.indexOf(WEAKLY_RELATED_ITEMS));
			trainingSet.add(inst);
	//		System.out.println(sortList.get(i).getKey()+" "+promValues.get(sortList.get(i)));
		}
		
		
		return trainingSet;
	}
	
	public Instances buildDataset4ClusterWithCityBlockDistance(List<PairOE> pairs, List<SimilarityMatrix> similarityData) {

		FastVector fvWekaAttributes = new FastVector(1);
		
		fvWekaAttributes.addElement(new Attribute("cityBlockDistance"));

		Instances dataSet = new Instances("Training Set", fvWekaAttributes, 10);

		for (PairOE pair : pairs) {
			Instance instance = new Instance(1);
			instance.setDataset(dataSet);
			
			double cityBlockDistance = 0;
			
			for (int i = 0; i < similarityData.size(); i++) {
				if(similarityData.get(i).getPairValue(pair)==null){
					similarityData.get(i).setPairValue(pair, 0.0);
				}
				//if (trainingSet.attribute(similarityData.get(i).getName()) != null) {
				cityBlockDistance = cityBlockDistance + similarityData.get(i).getPairValue(pair);
				//}
			}
			
			instance.setValue(dataSet.attribute("cityBlockDistance"),cityBlockDistance);
			
			instanceMap.put(pair, instance);
			dataSet.add(instance);
		}
		
		return dataSet;
	}
	

	public HashMap<PairOE, Instance> getInstanceMap() {
		return instanceMap;
	}

	public void setInstanceMap(HashMap<PairOE, Instance> instanceMap) {
		this.instanceMap = instanceMap;
	}
	
	
}

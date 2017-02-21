package conformance.mapper.grouping.classification;

import conformance.mapper.configuration.ConfigurationManager;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 * El primer intento fue utilizar clasificacion pero no dio buenos resultados
 * @author Hernan
 *
 */
@Deprecated
public class ClassifierManager {
	
	private static ClassifierManager instance;
	private Classifier classifier;
	
	public static ClassifierManager getInstance(){
		if(instance==null){
			instance = new ClassifierManager();
		}
		return instance;
	}
	
	private ClassifierManager(){
		if(ConfigurationManager.getProperty("classifier").equals("KNN")){
			classifier = new IBk(Integer.valueOf(ConfigurationManager.getProperty("K")));
		}else if(ConfigurationManager.getProperty("classifier").equals("C45")){
			classifier = new J48();
		}else if(ConfigurationManager.getProperty("classifier").equals("NaiveBayes")){
			classifier = new NaiveBayes();
		}else if(ConfigurationManager.getProperty("classifier").equals("SMO")){
			classifier = new SMO();
		}else if(ConfigurationManager.getProperty("classifier").equals("SVM")){
			classifier = new SVMClassifier();
		}
	}
	
	public Classifier getClassifierForInstances(Instances trainingSet){
		try {
			classifier.buildClassifier(trainingSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classifier;
	}

	
	
}

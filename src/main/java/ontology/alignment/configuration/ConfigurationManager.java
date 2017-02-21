package ontology.alignment.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
	
	public static final String TRAINING_PERCENTAGE = "trainingPercentage";
	public static final String LINGUISTIC_ANALYSIS = "LinguisticSimilarityFunctionEnabled";
	public static final String PROPERTY_ANALYSIS = "PropertySimilarityFunctionEnabled";
	public static final String STRUCTURAL_ANALYSIS = "StructuralSimilarityFunctionEnabled";
	public static final String SOCIALNETWORK_ANALYSIS = "SocialNetworkAnalysis";
	public static final String GROUPING = "Grouping";

 	private static Properties property = null;
	
	private static Properties getInstance() {
		if (property==null) {
			property = new Properties();
			try {
				property.load(new FileInputStream("config.properties"));
		 	 } catch (IOException ex) {}
		}
		return property;
	}
	
	public static String getProperty(String propName) {
		Properties prop = getInstance();
		return prop.getProperty(propName);
	}
	
	public static String setProperty(String propName, String propValue) {
		Properties prop = getInstance();
		prop.setProperty(propName, propValue);
		return prop.getProperty(propName);
	}	
}
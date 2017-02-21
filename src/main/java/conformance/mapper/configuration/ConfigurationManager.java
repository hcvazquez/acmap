package conformance.mapper.configuration;

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
	public static final String HIERARCHICAL_ANALYSIS = "HierarchicalAnalysis";
	public static final String FEATURE_LOCATION_ANALYSIS = "FeatureLocationAnalysis";
	public static final String MAXIMUM_MATCHING_ANALYSIS = "MaximumMatchingAnalysis";
	public static final String FL_FILE_PATH = "FL_FILE_PATH";
	
	public static final String L_WEIGHT = "LinguisticSimilarityFunctionWeigth";
	public static final String P_WEIGHT = "PropertySimilarityFunctionWeigth";
	public static final String FL_WEIGHT = "FeatureLocationWeigth";
	public static final String S_WEIGHT = "StructuralSimilarityFunctionWeigth";
	public static final String M_WEIGHT = "MaximumMatchingWeigth";
	public static final String SNA_WEIGHT = "SocialNetworkWeigth";
	public static final String H_WEIGHT = "HierarchicalWeigth";

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
package conformance.mapper.similarity.function.linguistic;

import conformance.mapper.configuration.ConfigurationManager;
import semilar.sentencemetrics.AbstractComparer;
import semilar.sentencemetrics.BLEUComparer;
import semilar.sentencemetrics.CorleyMihalceaComparer;
import semilar.sentencemetrics.DependencyComparer;
import semilar.sentencemetrics.GreedyComparer;
import semilar.sentencemetrics.LSAComparer;
import semilar.config.OptimumComparer;
import semilar.sentencemetrics.PairwiseComparer.NormalizeType;
import semilar.sentencemetrics.PairwiseComparer.WordWeightType;
import semilar.tools.semantic.WordNetSimilarity;
import semilar.wordmetrics.WNWordMetric;

public class LinguisticManager {

	private static LinguisticManager instance;
	private static final String SIMILARITY_NAME = "Linguistic Similarity";
	
	public static String getSimilarityName() {
		return SIMILARITY_NAME;
	}

	public static LinguisticManager getInstance(){
		if(instance==null){
			instance = new LinguisticManager();
		}
		return instance;
	}
	
	public AbstractComparer getLinguisticComparer(){
		 
		AbstractComparer comparer = null;
		
		/* Word to word similarity expanded to sentence to sentence .. so we need word metrics */
        boolean wnFirstSenseOnly = false; //applies for WN based methods only.
        WNWordMetric wnMetricLin;
        WNWordMetric wnMetricLeskTanim;
        //provide the LSA model name you want to use.
        //LSAWordMetric lsaMetricTasa = new LSAWordMetric("LSA-MODEL-TASA-LEMMATIZED-DIM300");
        //provide the LDA model name you want to use.
        //LDAWordMetric ldaMetricTasa = new LDAWordMetric("LDA-MODEL-TASA-LEMMATIZED-TOPIC300");

        if(ConfigurationManager.getProperty("LinguisticSimilarityComparer").equals("GreedyComparer")){
        	wnMetricLin = new WNWordMetric(WordNetSimilarity.WNSimMeasure.LIN, wnFirstSenseOnly);
        	comparer = new GreedyComparer(wnMetricLin, 0.3f, false);
        	//greedyComparerWNLeskTanim = new GreedyComparer(wnMetricLeskTanim, 0.3f, false);
        	//greedyComparerLSATasa = new GreedyComparer(lsaMetricTasa, 0.3f, false);
        	//greedyComparerLDATasa = new GreedyComparer(ldaMetricTasa, 0.3f, false);
        }	

        if(ConfigurationManager.getProperty("LinguisticSimilarityComparer").equals("OptimumComparer")){
        	wnMetricLin = new WNWordMetric(WordNetSimilarity.WNSimMeasure.LIN, wnFirstSenseOnly);
        	comparer = new OptimumComparer(wnMetricLin, 0.3f, false, WordWeightType.NONE, NormalizeType.AVERAGE);
        	//optimumComparerWNLeskTanim = new OptimumComparer(wnMetricLeskTanim, 0.3f, false, WordWeightType.NONE, NormalizeType.AVERAGE);
        	//comparer = new OptimumComparer(lsaMetricTasa, 0.3f, false, WordWeightType.NONE, NormalizeType.AVERAGE);
        	//optimumComparerLDATasa = new OptimumComparer(ldaMetricTasa, 0.3f, false, WordWeightType.NONE, NormalizeType.AVERAGE);
        }

        if(ConfigurationManager.getProperty("LinguisticSimilarityComparer").equals("DependencyComparer")){
        	//Use one of the many word metrics. The example below uses Wordnet Lesk Tanim. Similarly, try using other
        	//word similarity metrics.
        	wnMetricLeskTanim = new WNWordMetric(WordNetSimilarity.WNSimMeasure.LESK_TANIM, wnFirstSenseOnly);
        	comparer = new DependencyComparer(wnMetricLeskTanim, 0.3f, true, "NONE", "AVERAGE");
        }
        
        /* methods without using word metrics */
        if(ConfigurationManager.getProperty("LinguisticSimilarityComparer").equals("CorleyMihalceaComparer")){
        	comparer = new CorleyMihalceaComparer(0.3f, false, "NONE", "par");
        }
        
        //for METEOR, please provide the **Absolute** path to your project home folder (without / at the end), And the
        // semilar library jar file should be in your project home folder.
        //meteorComparer = new MeteorComparer("C:/Users/Rajendra/workspace/SemilarLib/");
        
        if(ConfigurationManager.getProperty("LinguisticSimilarityComparer").equals("BLEUComparer")){
        	comparer = new BLEUComparer();
        }

        if(ConfigurationManager.getProperty("LinguisticSimilarityComparer").equals("LSAComparer")){
        	//lsaComparer: This is different from lsaMetricTasa, as this method will
        	// directly calculate sentence level similarity whereas  lsaMetricTasa
        	// is a word 2 word similarity metric used with Optimum and Greedy methods.
        	comparer = new LSAComparer("LSA-MODEL-TASA-LEMMATIZED-DIM300");
        }
        
        //lexicalOverlapComparer = new LexicalOverlapComparer(false);  // use base form of words? - No/false. 
        //for LDA based method.. please see the different example file.
		
		return comparer;
	}
}

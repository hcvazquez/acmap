package ontology.alignment.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import semilar.config.ConfigManager;
import semilar.data.Sentence;
import semilar.sentencemetrics.BLEUComparer;
import semilar.sentencemetrics.CorleyMihalceaComparer;
import semilar.sentencemetrics.DependencyComparer;
import semilar.sentencemetrics.GreedyComparer;
import semilar.sentencemetrics.LSAComparer;
import semilar.sentencemetrics.LexicalOverlapComparer;
import semilar.sentencemetrics.MeteorComparer;
import semilar.sentencemetrics.OptimumComparer;
import semilar.sentencemetrics.PairwiseComparer.NormalizeType;
import semilar.sentencemetrics.PairwiseComparer.WordWeightType;
import semilar.tools.preprocessing.SentencePreprocessor;
import semilar.tools.semantic.WordNetSimilarity;
import semilar.wordmetrics.LSAWordMetric;
import semilar.wordmetrics.WNWordMetric;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

/**
 * Examples using various sentence to sentence similarity methods. Please note that due to huge models of similarity
 * measures (wordnet files, LSA model, LDA models, ESA, PMI etc) and preprocessors - Standford/OpenNLP models, you may
 * not be able to run all the methods in a single pass. Also, for LDA based methods, we have to estimate the topic
 * distributions for the candidate pairs before calculating similarity. So, The examples for LDA based sentence to
 * sentence similarity and document level similarity are provided in the separate file as they have special
 * requirements.
 *
 * Some methods calculate the similarity of sentences directly or some use word to word similarity expanding to the
 * sentence level similarity. See the examples + documentation for more details.
 *
 * @author Rajendra
 */
public class TestSimilaritySentence {

    /* NOTE:
     * The greedy matching and Optimal matching methods rely on word to word similarity method.
     *(please see http://aclweb.org/anthology//W/W12/W12-2018.pdf for more details). So, based on the unique word to
     * word similarity measure, they have varying output (literally, many sentence to sentence similarity methods from
     * the combinations).
     */
    //greedy matching (see the available word 2 word similarity in the separate example file). Here I use some of them
    // for the illustration.
    GreedyComparer greedyComparerWNLin; //greedy matching, use wordnet LIN method for Word 2 Word similarity
    GreedyComparer greedyComparerWNLeskTanim;//greedy matching, use wordnet LESK-Tanim method for Word 2 Word similarity
    GreedyComparer greedyComparerLSATasa; // use LSA based word 2 word similarity (using TASA corpus LSA model).
    GreedyComparer greedyComparerLDATasa; // use LDA based word 2 word similarity (using TASA corpus LDA model).
    //Overall optimum matching method.. you may try all possible word to word similarity measures. Here I show some.
    OptimumComparer optimumComparerWNLin;
    OptimumComparer optimumComparerWNLeskTanim;
    OptimumComparer optimumComparerLSATasa;
    OptimumComparer optimumComparerLDATasa;
    //dependency based method.. we need to provide a word to word similarity metric. Here is just one example
    // using Wordnet Lesk Tanim.
    DependencyComparer dependencyComparerWnLeskTanim;
    //Please see paper Corley, C. and Mihalcea, R. (2005). Measuring the semantic similarity of texts.
    CorleyMihalceaComparer cmComparer;
    //METEOR method (introduced for machine translation evaluation): http://www.cs.cmu.edu/~alavie/METEOR/
    MeteorComparer meteorComparer;
    //BLEU (introduced for machine translation evaluation):http://acl.ldc.upenn.edu/P/P02/P02-1040.pdf 
    BLEUComparer bleuComparer;
    LSAComparer lsaComparer;
    LexicalOverlapComparer lexicalOverlapComparer; // Just see the lexical overlap.
    //For LDA based method.. see the separate example file. Its something different.

    public TestSimilaritySentence() {

        /* Word to word similarity expanded to sentence to sentence .. so we need word metrics */
        boolean wnFirstSenseOnly = false; //applies for WN based methods only.
        WNWordMetric wnMetricLin = new WNWordMetric(WordNetSimilarity.WNSimMeasure.LIN, wnFirstSenseOnly);
        WNWordMetric wnMetricLeskTanim = new WNWordMetric(WordNetSimilarity.WNSimMeasure.LESK_TANIM, wnFirstSenseOnly);
        //provide the LSA model name you want to use.
        LSAWordMetric lsaMetricTasa = new LSAWordMetric("LSA-MODEL-TASA-LEMMATIZED-DIM300");
        //provide the LDA model name you want to use.
        //LDAWordMetric ldaMetricTasa = new LDAWordMetric("LDA-MODEL-TASA-LEMMATIZED-TOPIC300");

        greedyComparerWNLin = new GreedyComparer(wnMetricLin, 0.3f, false);
        //greedyComparerWNLeskTanim = new GreedyComparer(wnMetricLeskTanim, 0.3f, false);
        //greedyComparerLSATasa = new GreedyComparer(lsaMetricTasa, 0.3f, false);
        //greedyComparerLDATasa = new GreedyComparer(ldaMetricTasa, 0.3f, false);

        optimumComparerWNLin = new OptimumComparer(wnMetricLin, 0.3f, false, WordWeightType.NONE, NormalizeType.AVERAGE);
        //optimumComparerWNLeskTanim = new OptimumComparer(wnMetricLeskTanim, 0.3f, false, WordWeightType.NONE, NormalizeType.AVERAGE);
        optimumComparerLSATasa = new OptimumComparer(lsaMetricTasa, 0.3f, false, WordWeightType.NONE, NormalizeType.AVERAGE);
        //optimumComparerLDATasa = new OptimumComparer(ldaMetricTasa, 0.3f, false, WordWeightType.NONE, NormalizeType.AVERAGE);

        //Use one of the many word metrics. The example below uses Wordnet Lesk Tanim. Similarly, try using other
        //word similarity metrics.
        dependencyComparerWnLeskTanim = new DependencyComparer(wnMetricLeskTanim, 0.3f, true, "NONE", "AVERAGE");

        /* methods without using word metrics */
        cmComparer = new CorleyMihalceaComparer(0.3f, false, "NONE", "par");
        //for METEOR, please provide the **Absolute** path to your project home folder (without / at the end), And the
        // semilar library jar file should be in your project home folder.
        //meteorComparer = new MeteorComparer("C:/Users/Rajendra/workspace/SemilarLib/");
        bleuComparer = new BLEUComparer();

        //lsaComparer: This is different from lsaMetricTasa, as this method will
        // directly calculate sentence level similarity whereas  lsaMetricTasa
        // is a word 2 word similarity metric used with Optimum and Greedy methods.
        lsaComparer = new LSAComparer("LSA-MODEL-TASA-LEMMATIZED-DIM300");
        //lexicalOverlapComparer = new LexicalOverlapComparer(false);  // use base form of words? - No/false. 
        //for LDA based method.. please see the different example file.
    }

    public void printSimilarities(Sentence sentenceA, Sentence sentenceB) {
        System.out.println("Sentence 1:" + sentenceA.getRawForm());
        System.out.println("Sentence 2:" + sentenceB.getRawForm());
        System.out.println("------------------------------");
        System.out.println("greedyComparerWNLin : " + greedyComparerWNLin.computeSimilarity(sentenceA, sentenceB));
        //System.out.println("greedyComparerWNLeskTanim : " + greedyComparerWNLeskTanim.computeSimilarity(sentenceA, sentenceB));
        //System.out.println("greedyComparerLSATasa : " + greedyComparerLSATasa.computeSimilarity(sentenceA, sentenceB));
        //System.out.println("greedyComparerLDATasa : " + greedyComparerLDATasa.computeSimilarity(sentenceA, sentenceB));
        //System.out.println("optimumComparerWNLin : " + optimumComparerWNLin.computeSimilarity(sentenceA, sentenceB));
        //System.out.println("optimumComparerWNLeskTanim : " + optimumComparerWNLeskTanim.computeSimilarity(sentenceA, sentenceB));
        System.out.println("optimumComparerLSATasa : " + optimumComparerLSATasa.computeSimilarity(sentenceA, sentenceB));
        //System.out.println("optimumComparerLDATasa : " + optimumComparerLDATasa.computeSimilarity(sentenceA, sentenceB));
        System.out.println("dependencyComparerWnLeskTanim : " + dependencyComparerWnLeskTanim.computeSimilarity(sentenceA, sentenceB));
        System.out.println("cmComparer : " + cmComparer.computeSimilarity(sentenceA, sentenceB));
        //System.out.println("meteorComparer : " + meteorComparer.computeSimilarity(sentenceA, sentenceB));
        System.out.println("bleuComparer : " + bleuComparer.computeSimilarity(sentenceA, sentenceB));
        System.out.println("lsaComparer : " + lsaComparer.computeSimilarity(sentenceA, sentenceB));
        //System.out.println("lexicalOverlapComparer : " + lexicalOverlapComparer.computeSimilarity(sentenceA, sentenceB));
        System.out.println("                              ");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
    	/*try {
			testDictionary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        // first of all set the semilar data folder path (ending with /).
        ConfigManager.setSemilarDataRootFolder("C:/benchmarks/semilar/");
        ConfigManager.setSemilarHomeFolder("C:/benchmarks/semilar/");
        
        System.out.println(ConfigManager.getWordnetDictionaryPath());
        System.out.println(ConfigManager.getWordnetPath());
        System.out.println(ConfigManager.getSemilarHomeFolder());
        System.out.println(ConfigManager.getSemilarDataRootFolder());

        Sentence sentence1;
        Sentence sentence2;

        //String text1 = "\"Senator Clinton should be ashamed of herself for playing politics with the important issue of homeland security funding,\" he said.";
        //String text2 = "\"She should be ashamed of herself for playing politics with this important issue,\" said state budget division spokesman Andrew Rush.";

        String text1 = "Play Music Controller";
        String text2 = "Media Controller";
        		
        SentencePreprocessor preprocessor = new SentencePreprocessor(SentencePreprocessor.TokenizerType.STANFORD, SentencePreprocessor.TaggerType.STANFORD, SentencePreprocessor.StemmerType.PORTER, SentencePreprocessor.ParserType.STANFORD);
        sentence1 = preprocessor.preprocessSentence(text1);
        sentence2 = preprocessor.preprocessSentence(text2);

        TestSimilaritySentence s2sSimilarityMeasurer = new TestSimilaritySentence();
        s2sSimilarityMeasurer.printSimilarities(sentence1, sentence2);

        System.out.println("\nDone!");
    }
    
    public static void testDictionary() throws IOException {      
        // construct the URL to the Wordnet dictionary directory 
        String wnhome = System.getenv("WNHOME"); 
        String path = wnhome + File.separator + "dict"; 
        URL url = new URL("file", null, path); 
         
        // construct the dictionary object and open it 
        IDictionary dict = new Dictionary(url); 
        dict.open(); 
     
        // look up first sense of the word "dog" 
        IIndexWord idxWord = dict.getIndexWord("dog", POS.NOUN);
        IWordID wordID = idxWord.getWordIDs().get(0);
        IWord word = dict.getWord(wordID);
        System.out.println("Id = " + wordID);
        System.out.println("Lemma = " + word.getLemma());
        System.out.println("Gloss = " + word.getSynset().getGloss());
    	 
    } 
    	 
}

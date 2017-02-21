package ontology.alignment.similarity;

import java.util.ArrayList;

import ontology.alignment.definition.Ontology;
import ontology.alignment.definition.Node;
import ontology.alignment.pairing.PairOE;
import semilar.config.ConfigManager;
import semilar.data.Sentence;
import semilar.sentencemetrics.AbstractComparer;
import semilar.tools.preprocessing.SentencePreprocessor;

public class LinguisticFunction implements SimilarityFunction {

	@Override
	public SimilarityMatrix execute(Ontology o1, Ontology o2) {
		
		SimilarityMatrixImp simMatrix = new SimilarityMatrixImp("Linguistic Similarity");
		
        ConfigManager.setSemilarDataRootFolder("C:/benchmarks/semilar/");
        ConfigManager.setSemilarHomeFolder("C:/benchmarks/semilar/");
		
	    AbstractComparer comparer = LinguisticManager.getInstance().getLinguisticComparer();
	    
	    SentencePreprocessor preprocessor = new SentencePreprocessor(SentencePreprocessor.TokenizerType.STANFORD, 
	    		SentencePreprocessor.TaggerType.STANFORD, SentencePreprocessor.StemmerType.PORTER, 
	    			SentencePreprocessor.ParserType.STANFORD);
	    
	    for(int i=0; i<o1.getNodes().size();i++){
	    	for(int e=0; e<o2.getNodes().size();e++){
	    		/*System.out.println(o1.getNodes().get(i).getName()+":"+o2.getNodes().get(e).getName()
	    				+ " -> "+comparer.computeSimilarity(getSentenceFromName(o1.getNodes().get(i),preprocessor),
	    						getSentenceFromName(o2.getNodes().get(e),preprocessor)));*/
	    		PairOE pairOE = new PairOE(o1.getNodes().get(i),o2.getNodes().get(e));
				simMatrix.setPairValue(pairOE, Double.valueOf(comparer.computeSimilarity(getSentenceFromName(o1.getNodes().get(i),preprocessor),
						getSentenceFromName(o2.getNodes().get(e),preprocessor))));		
	    	}
	    }
		
		return simMatrix;
		
	}

	private Sentence getSentenceFromName(Node ontologyNode, SentencePreprocessor preprocessor) {

		String name = ontologyNode.getName();
		
		String[] splited = name.split("_| ");
		ArrayList<String> parts = new ArrayList<String>();
		
		for(String a: splited){
			char[] cadena = a.toCharArray();
			
			int i=0;
			String firstLetter = "";
			if(!String.valueOf(cadena[i]).equals("[A-Z]")){
				firstLetter = String.valueOf(cadena[i]);
				i++;
			}
			
			if(a.split("[A-Z]").length>i+1 && !a.startsWith("[A-Z]")){
				String word = firstLetter;

				for(int e=i; e<cadena.length;e++){
					char c = cadena[e];
					while(!String.valueOf(c).matches("^[A-Z]") && e<cadena.length){
						word = word + c;
						if(e+1<cadena.length){
							c = cadena[e+1];
						}
						e++;
					}
					parts.add(word);
					word = String.valueOf(c);
				}
				
			}else{
				parts.add(a);
			}
		}
		
		String text = "";
		for(int i = 0; i<parts.size();i++){
			text = text + parts.get(i);
			if(i+1<parts.size()) text=text+ " ";
		}
		return preprocessor.preprocessSentence(text);
		
	}

}

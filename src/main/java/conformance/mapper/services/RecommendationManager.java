package conformance.mapper.services;

import java.util.ArrayList;
import java.util.HashMap;

import conformance.mapper.configuration.ConfigurationManager;
import conformance.mapper.core.AlignmentImp;
import conformance.mapper.definition.Ontology;
import conformance.mapper.grouping.ranking.Ranking;
import conformance.mapper.pairing.PairOE;
import conformance.mapper.parser.RDFtoOntologyGraph;
import conformance.mapper.utils.MapVerifyManager;

public class RecommendationManager {
	
	public Ranking getRecomendationRanking(String componentRDF, String classesRDF, HashMap<String, Double> configuration){
		
		Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile(componentRDF);
		Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile(classesRDF);
		
		this.configureRecommender(configuration);
		
		AlignmentImp mapper = new AlignmentImp();
		
        ArrayList<PairOE> alignment = mapper.executeAlignment(o1, o2,  new ArrayList<PairOE>()).get(0);
       
        return new Ranking(alignment);	
	}
	
	public void configureRecommender(HashMap<String, Double> functions){
		
        ConfigurationManager.setProperty(ConfigurationManager.LINGUISTIC_ANALYSIS, functions.get(ConfigurationManager.LINGUISTIC_ANALYSIS)!=null?"true":"false");
        ConfigurationManager.setProperty(ConfigurationManager.FEATURE_LOCATION_ANALYSIS, functions.get(ConfigurationManager.FEATURE_LOCATION_ANALYSIS)!=null?"true":"false");
        ConfigurationManager.setProperty(ConfigurationManager.STRUCTURAL_ANALYSIS, functions.get(ConfigurationManager.STRUCTURAL_ANALYSIS)!=null?"true":"false");
        ConfigurationManager.setProperty(ConfigurationManager.HIERARCHICAL_ANALYSIS, functions.get(ConfigurationManager.HIERARCHICAL_ANALYSIS)!=null?"true":"false");        
        
        ConfigurationManager.setProperty(ConfigurationManager.L_WEIGHT, functions.get(ConfigurationManager.LINGUISTIC_ANALYSIS)!=null?
        		String.valueOf(functions.get(ConfigurationManager.LINGUISTIC_ANALYSIS)):"0.0");
        
        ConfigurationManager.setProperty(ConfigurationManager.FL_WEIGHT, functions.get(ConfigurationManager.FEATURE_LOCATION_ANALYSIS)!=null?
        		String.valueOf(functions.get(ConfigurationManager.FEATURE_LOCATION_ANALYSIS)):"0.0");
        
        ConfigurationManager.setProperty(ConfigurationManager.S_WEIGHT, functions.get(ConfigurationManager.STRUCTURAL_ANALYSIS)!=null?
        		String.valueOf(functions.get(ConfigurationManager.STRUCTURAL_ANALYSIS)):"0.0");
        
        ConfigurationManager.setProperty(ConfigurationManager.H_WEIGHT, functions.get(ConfigurationManager.HIERARCHICAL_ANALYSIS)!=null?
        		String.valueOf(functions.get(ConfigurationManager.HIERARCHICAL_ANALYSIS)):"0.0");
	}
	
	public Ranking getRecomendationRankingWithEvaluation(String componentRDF, String classesRDF, HashMap<String, Double> configuration, String golden){
		
		Ontology o1 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile(componentRDF);
		Ontology o2 = RDFtoOntologyGraph.loadOntologyGraphFromRDFOWLFile(classesRDF);
		
		this.configureRecommender(configuration);
		
		AlignmentImp mapper = new AlignmentImp();
		
        ArrayList<PairOE> alignment = mapper.executeAlignment(o1, o2,  new ArrayList<PairOE>()).get(0);
        
        MapVerifyManager.getInstance().initialize(golden);
        
        ArrayList<PairOE> result = new ArrayList<PairOE>();
        for(PairOE pair:alignment){
     	   int correct =  MapVerifyManager.getInstance().isCorrectMappingInt(pair.getElement2().getShortName(), pair.getElement1().getName());
     	   if(correct!=-1){
     		   pair.setVerified(correct);
     		   result.add(pair);
     	   }else{
     		   //no encontró la clase o el componente en el golden
     	   }
        }
       
        return new Ranking(result);	
	}
	
}

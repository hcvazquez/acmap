package conformance.mapper.view.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import conformance.mapper.definition.Element;
import conformance.mapper.grouping.ranking.Ranking;
import conformance.mapper.pairing.PairOE;


public class TestOntologyCompleto {

	public static void main(String[] args) {
		
		TestOntologyAlignmentHW.main(args);
		
		TestOntologyAlignmentMM.main(args);
		
		cargarRankingsHW();
		
		cargarRankingsMM();
		
		
	}

	private static void cargarRankingsMM() {
		
		Ranking MM_FL = cargarRanking("Results/MM/MM_FL");
		System.out.println("MM_FL: Max Precision -> "+MM_FL.getMaxPrecision());
		System.out.println("       Max Recall    -> "+MM_FL.getMaxRecall(122));
		System.out.println("       Max F-Measure -> "+MM_FL.getMaxFMeasure(122));
		System.out.println();
		MM_FL.saveRankingInFile("Results/Rankings/MM/MM_FL");
		
		Ranking MM_FL_S = cargarRanking("Results/MM/MM_FL_S");
		System.out.println("MM_FL_S: Max Precision -> "+MM_FL_S.getMaxPrecision());
		System.out.println("         Max Recall    -> "+MM_FL_S.getMaxRecall(122));
		System.out.println("         Max F-Measure -> "+MM_FL_S.getMaxFMeasure(122));
		System.out.println();
		MM_FL_S.saveRankingInFile("Results/Rankings/MM/MM_FL_S");
		
		Ranking MM_L = cargarRanking("Results/MM/MM_L");
		System.out.println("MM_L:  Max Precision -> "+MM_L.getMaxPrecision());
		System.out.println("       Max Recall    -> "+MM_L.getMaxRecall(122));
		System.out.println("       Max F-Measure -> "+MM_L.getMaxFMeasure(122));
		System.out.println();
		MM_L.saveRankingInFile("Results/Rankings/MM/MM_L");
		
		Ranking MM_L_FL = cargarRanking("Results/MM/MM_L_FL");
		System.out.println("MM_L_FL: Max Precision -> "+MM_L_FL.getMaxPrecision());
		System.out.println("         Max Recall    -> "+MM_L_FL.getMaxRecall(122));
		System.out.println("         Max F-Measure -> "+MM_L_FL.getMaxFMeasure(122));
		System.out.println();
		MM_L_FL.saveRankingInFile("Results/Rankings/MM/MM_L_FL");
		
		Ranking MM_L_FL_S_H = cargarRanking("Results/MM/MM_L_FL_S_H");
		System.out.println("MM_L_FL_S_H: Max Precision -> "+MM_L_FL_S_H.getMaxPrecision());
		System.out.println("       		 Max Recall    -> "+MM_L_FL_S_H.getMaxRecall(122));
		System.out.println("       		 Max F-Measure -> "+MM_L_FL_S_H.getMaxFMeasure(122));
		System.out.println();
		MM_L_FL_S_H.saveRankingInFile("Results/Rankings/MM/MM_L_FL_S_H");
		
		Ranking MM_L_S = cargarRanking("Results/MM/MM_L_S");
		System.out.println("MM_L_S: Max Precision -> "+MM_L_S.getMaxPrecision());
		System.out.println("        Max Recall    -> "+MM_L_S.getMaxRecall(122));
		System.out.println("        Max F-Measure -> "+MM_L_S.getMaxFMeasure(122));
		System.out.println();
		MM_L_S.saveRankingInFile("Results/Rankings/MM/MM_L_S");
		
		Ranking MM_L_S_H = cargarRanking("Results/MM/MM_L_S_H");
		System.out.println("MM_L_S_H: Max Precision -> "+MM_L_S_H.getMaxPrecision());
		System.out.println("       	  Max Recall    -> "+MM_L_S_H.getMaxRecall(122));
		System.out.println("          Max F-Measure -> "+MM_L_S_H.getMaxFMeasure(122));
		System.out.println();
		MM_L_S_H.saveRankingInFile("Results/Rankings/MM/MM_L_S_H");
		
	}

	private static void cargarRankingsHW() {
		
		Ranking HW_FL = cargarRanking("Results/HW/HW_FL");
		System.out.println("HW_FL: Max Precision -> "+HW_FL.getMaxPrecision());
		System.out.println("       Max Recall    -> "+HW_FL.getMaxRecall(122));
		System.out.println("       Max F-Measure -> "+HW_FL.getMaxFMeasure(122));
		System.out.println();
		HW_FL.saveRankingInFile("Results/Rankings/HW/HW_FL");
		
		Ranking HW_FL_S = cargarRanking("Results/HW/HW_FL_S");
		System.out.println("HW_FL_S: Max Precision -> "+HW_FL_S.getMaxPrecision());
		System.out.println("         Max Recall    -> "+HW_FL_S.getMaxRecall(122));
		System.out.println("         Max F-Measure -> "+HW_FL_S.getMaxFMeasure(122));
		System.out.println();
		HW_FL_S.saveRankingInFile("Results/Rankings/HW/HW_FL_S");
		
		Ranking HW_L = cargarRanking("Results/HW/HW_L");
		System.out.println("HW_L:  Max Precision -> "+HW_L.getMaxPrecision());
		System.out.println("       Max Recall    -> "+HW_L.getMaxRecall(122));
		System.out.println("       Max F-Measure -> "+HW_L.getMaxFMeasure(122));
		System.out.println();
		HW_L.saveRankingInFile("Results/Rankings/HW/HW_L");
		
		Ranking HW_L_FL = cargarRanking("Results/HW/HW_L_FL");
		System.out.println("HW_L_FL: Max Precision -> "+HW_L_FL.getMaxPrecision());
		System.out.println("         Max Recall    -> "+HW_L_FL.getMaxRecall(122));
		System.out.println("         Max F-Measure -> "+HW_L_FL.getMaxFMeasure(122));
		System.out.println();
		HW_L_FL.saveRankingInFile("Results/Rankings/HW/HW_L_FL");
		
		Ranking HW_L_FL_S_H = cargarRanking("Results/HW/HW_L_FL_S_H");
		System.out.println("HW_L_FL_S_H: Max Precision -> "+HW_L_FL_S_H.getMaxPrecision());
		System.out.println("       		 Max Recall    -> "+HW_L_FL_S_H.getMaxRecall(122));
		System.out.println("       		 Max F-Measure -> "+HW_L_FL_S_H.getMaxFMeasure(122));
		System.out.println();
		HW_L_FL_S_H.saveRankingInFile("Results/Rankings/HW/HW_L_FL_S_H");
		
		Ranking HW_L_S = cargarRanking("Results/HW/HW_L_S");
		System.out.println("HW_L_S: Max Precision -> "+HW_L_S.getMaxPrecision());
		System.out.println("        Max Recall    -> "+HW_L_S.getMaxRecall(122));
		System.out.println("        Max F-Measure -> "+HW_L_S.getMaxFMeasure(122));
		System.out.println();
		HW_L_S.saveRankingInFile("Results/Rankings/HW/HW_L_S");
		
		Ranking HW_L_S_H = cargarRanking("Results/HW/HW_L_S_H");
		System.out.println("HW_L_S_H: Max Precision -> "+HW_L_S_H.getMaxPrecision());
		System.out.println("       	  Max Recall    -> "+HW_L_S_H.getMaxRecall(122));
		System.out.println("          Max F-Measure -> "+HW_L_S_H.getMaxFMeasure(122));
		System.out.println();
		HW_L_S_H.saveRankingInFile("Results/Rankings/HW/HW_L_S_H");
	}

	private static Ranking cargarRanking(String filePath) {
		Ranking ranking = null;
		
		try {
		  ArrayList<PairOE> rankList = new ArrayList<PairOE>();
		  
	      FileReader fr = new FileReader(filePath);
	      BufferedReader br = new BufferedReader(fr);
	 
	      String linea;
	      while((linea = br.readLine()) != null){
	    	  
	        String[] rankEntry = linea.split(" ");
	        PairOE pair = new PairOE(new Element(0, rankEntry[0]), new Element(0, rankEntry[1]));
	        pair.setConfidence(Double.valueOf(rankEntry[2]));
	        pair.setVerified(Integer.valueOf(rankEntry[3]));
	        rankList.add(pair);
	        
	      }
	 
	      ranking = new Ranking(rankList);
	    		  
	      fr.close();
	    }
	    catch(Exception e) {
	      System.out.println("Excepcion leyendo ranking "+ filePath + ": " + e);
	    }
		
		return ranking;
	}
}

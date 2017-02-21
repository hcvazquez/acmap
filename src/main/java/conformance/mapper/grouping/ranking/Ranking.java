package conformance.mapper.grouping.ranking;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.ranking.NaturalRanking;
import org.apache.commons.math3.stat.ranking.RankingAlgorithm;
import org.apache.commons.math3.stat.ranking.TiesStrategy;

import conformance.mapper.pairing.PairOE;

public class Ranking {
	
	protected ArrayList<PairOE> rankingList;
	protected ArrayList<Double> precisionAcum;
	protected ArrayList<Double> recallAcum;
	protected ArrayList<Double> fMeasureAcum;
	

	public Ranking(ArrayList<PairOE> rankingList) {
		super();
		this.rankingList = rankingList;
		reorderRanking();
	}

	public ArrayList<PairOE> getRankingList() {
		return rankingList;
	}

	public void setRankingList(ArrayList<PairOE> rankingList) {
		this.rankingList = rankingList;
	}
	
	public void reorderRanking(){
		rankingList.sort( new Comparator<PairOE>() {
							@Override
							public int compare(PairOE p1, PairOE p2) {
								return (-1)*p1.getConfidence().compareTo(p2.getConfidence());
							}
						});
	}
	
	/**
	 * This method need an Order Ranking
	 * @return
	 */
	public ArrayList<Double> getPrecisionAcumList(){
		
		ArrayList<Double> precisionList = new ArrayList<Double>();
		
		int sumVerified = 0;
		for(int i=0;i<rankingList.size();i++){
			if(rankingList.get(i).getVerified().intValue()==1){
				sumVerified ++;
			}
			if(sumVerified!=0){
				precisionList.add((double)sumVerified/(double)(i+1));
			}else{
				precisionList.add(0.0);
			}
			
		}
		
		this.precisionAcum = precisionList;
		
		return precisionList;
	}
	
	public Double getMaxPrecision(){
		if(precisionAcum == null){
			precisionAcum = getPrecisionAcumList();
		}
		Double max = 0.0;
		for(Double d:precisionAcum){
			if(d>max){
				max = d;
			}
		}
		return max;
	}
	
	
	public ArrayList<Double> getRecallAcumList(int goldenSize){
		
		ArrayList<Double> recallList = new ArrayList<Double>();
		
		int sumVerified = 0;
		for(int i=0;i<rankingList.size();i++){
			if(rankingList.get(i).getVerified().intValue()==1){
				sumVerified ++;
			}
			recallList.add((double)sumVerified/(double)goldenSize);
			
		}
		
		this.recallAcum = recallList;
		
		return recallList;
	}
	
	public Double getMaxRecall(int goldenSize){
		if(recallAcum == null){
			recallAcum = getRecallAcumList(goldenSize);
		}
		Double max = 0.0;
		for(Double d:recallAcum){
			if(d>max){
				max = d;
			}
		}
		return max;
	}
	
	//=2*((J4*K4)/(J4+K4))
	public ArrayList<Double> getFMeasureAcumList(int goldenSize){
		
		if(precisionAcum == null){
			precisionAcum = getPrecisionAcumList();
		}
		
		if(recallAcum == null){
			recallAcum = getRecallAcumList(goldenSize);
		}
		
		ArrayList<Double> fMeasureList = new ArrayList<Double>();
		
		for(int i=0;i<rankingList.size();i++){
			if(precisionAcum.get(i).doubleValue() + recallAcum.get(i).doubleValue() != 0){
				fMeasureList.add((double)(
						(double) 2 * ( (precisionAcum.get(i).doubleValue()*recallAcum.get(i).doubleValue()) 
								/ (precisionAcum.get(i).doubleValue() +recallAcum.get(i).doubleValue()) ) 
						));
			}
			else{
				fMeasureList.add(0.0);
			}
			
		}
		
		this.fMeasureAcum = fMeasureList;
		
		return fMeasureList;
	}
	
	public Double getMaxFMeasure(int goldenSize){
		if(fMeasureAcum == null){
			fMeasureAcum = getFMeasureAcumList(goldenSize);
		}
		Double max = 0.0;
		for(Double d:fMeasureAcum){
			if(d>max){
				max = d;
			}
		}
		return max;
	}

	public void saveRankingInFile(String fileName) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		   {
	       fichero = new FileWriter(fileName);
           pw = new PrintWriter(fichero);      
           
           for(PairOE pair:rankingList){
       		   pw.println(pair.getElement1().getName()+" "+pair.getElement2().getName()+" "+pair.getConfidence());
           }             
		}
		 catch (Exception e) {
           e.printStackTrace();
        } finally {
          try {
             if (null != fichero)
               fichero.close();
             } 
          catch (Exception e2) {
             e2.printStackTrace();
          }
       }
		
	}
	
	public Double calculateSpearmanCorrelation(){
		double[] rank = new double[rankingList.size()];
		double[] golden = new double[rankingList.size()];
		this.fixRank(golden, rank);
		for(int i=0;i<rankingList.size();i++){
			PairOE pair = rankingList.get(i);
			rank[i] = pair.getConfidence();
			golden[i] = pair.getVerified();
		}
		double spearman = new SpearmansCorrelation().correlation(golden,rank);
		
		return spearman;
	}
	
	public Double calculatePearsonsCorrelation(){
		double[] rank = new double[rankingList.size()];
		double[] golden = new double[rankingList.size()];
		this.fixRank(golden, rank);
		
		for(int i=0;i<rankingList.size();i++){
			PairOE pair = rankingList.get(i);
			rank[i] = pair.getConfidence();
			golden[i] = pair.getVerified();
		}

		PearsonsCorrelation pearsons = new PearsonsCorrelation();

		return pearsons.correlation(golden,rank);
	}
	
	public Double calculateKendallsCorrelation(){
		double[] rank = new double[rankingList.size()];
		double[] golden = new double[rankingList.size()];
		this.fixRank(golden, rank);
		
		for(int i=0;i<rankingList.size();i++){
			PairOE pair = rankingList.get(i);
			rank[i] = pair.getConfidence();
			golden[i] = pair.getVerified();
		}
		double kendalls = new KendallsCorrelation().correlation(golden, rank);

		return kendalls;
	}
	
	private int countVerified(double[] golden){
		int sum = 0;
		for(double i:golden){
			if(i==1.0){
				sum++;
			}
		}
		return sum;
	}
	
	private void fixRank(double[] golden,double[] rank){
		int countVerified = countVerified(golden);
		for(int i=0;i<rank.length;i++){
			if(i<countVerified){
				rank[i]=1.0;
			}else{
				rank[i]=0.0;
			}
		}
	}

}

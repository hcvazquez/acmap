package semilar.config;

import java.util.ArrayList;

import semilar.data.Sentence;
import semilar.data.Word;
import semilar.sentencemetrics.PairwiseComparer;
import semilar.utilities.HungarianAlgorithm;
import semilar.utilities.SMUtils;
import semilar.wordmetrics.AbstractWordMetric;

public class OptimumComparer
  extends PairwiseComparer
{
  static final String ComparerID = "OPTLEX";
  private boolean useContentWordsOnly = true;
  
  public OptimumComparer(AbstractWordMetric _wordMetric, float _w2wSimThreshold, boolean _useBaseForm, PairwiseComparer.WordWeightType _wordWeighting, PairwiseComparer.NormalizeType _normalize)
  {
    this.wordMetric = _wordMetric;
    this.useBaseForm = Boolean.valueOf(_useBaseForm);
    this.w2wSimThreshold = _w2wSimThreshold;
    this.wordWeighting = _wordWeighting;
    this.normalizeType = _normalize;
  }
  
  public boolean isUseContentWordsOnly()
  {
    return this.useContentWordsOnly;
  }
  
  public void setUseContentWordsOnly(boolean useContentWordsOnly)
  {
    this.useContentWordsOnly = useContentWordsOnly;
  }
  
  public float computeSimilarity(Sentence sentenceA, Sentence sentenceB)
  {
    InitializeComparerOutput();
    
    LogComparerOutput("Optimum lexical matching:");
    



    int maxDim = sentenceA.getWords().size();
    if (sentenceB.getWords().size() > maxDim) {
      maxDim = sentenceB.getWords().size();
    }
    double[][] simMatrix = new double[maxDim][maxDim];
    for (int i = 0; i < maxDim; i++) {
      for (int j = 0; j < maxDim; j++) {
        if ((i < sentenceA.getWords().size()) && (j < sentenceB.getWords().size()))
        {
          if ((!this.useContentWordsOnly) || (
          
            (!((Word)sentenceA.getWords().get(i)).isIsStopWord()) && (!((Word)sentenceB.getWords().get(j)).isIsStopWord())))
          {
            if ((SMUtils.Check4Puntuation(((Word)sentenceA.getWords().get(i)).getRawForm(), ((Word)sentenceA.getWords().get(i)).getPos())) || (SMUtils.Check4Puntuation(((Word)sentenceB.getWords().get(j)).getRawForm(), ((Word)sentenceB.getWords().get(j)).getPos())))
            {
              simMatrix[i][j] = 0.0D;
            }
            else if (((Word)sentenceA.getWords().get(i)).getBaseForm().equalsIgnoreCase(((Word)sentenceB.getWords().get(j)).getBaseForm()))
            {
              simMatrix[i][j] = 1.0D;
            }
            else
            {
              simMatrix[i][j] = 0.0D;
              if ((this.wordMetric != null) && (((Word)sentenceA.getWords().get(i)).getPos().equals(((Word)sentenceB.getWords().get(j)).getPos()))) {
                simMatrix[i][j] = this.wordMetric.computeWordSimilarity(((Word)sentenceA.getWords().get(i)).getBaseForm(), ((Word)sentenceB.getWords().get(j)).getBaseForm(), ((Word)sentenceA.getWords().get(i)).getPos());
              }
            }
            if (simMatrix[i][j] < this.w2wSimThreshold) {
              simMatrix[i][j] = 0.0D;
            }
          }
        }
        else {
          simMatrix[i][j] = 0.0D;
        }
      }
    }
    int[][] assignment = new int[maxDim][2];
    assignment = HungarianAlgorithm.hgAlgorithm(simMatrix, "max");
    
    this.outputAssignment = new ArrayList();
    
    double sum = 0.0D;
    for (int i = 0; i < assignment.length; i++)
    {
      String tokenA = "NA";
      if ((assignment[i][0] < sentenceA.getWords().size()) && (assignment[i][1] < sentenceB.getWords().size()) && (simMatrix[assignment[i][0]][assignment[i][1]] > 0.0D))
      {
        sum += simMatrix[assignment[i][0]][assignment[i][1]];
        
//        System.out.println("sim: " + simMatrix[assignment[i][0]][assignment[i][1]]);
        Integer[] pair = new Integer[2];
        pair[0] = Integer.valueOf(assignment[i][0]);
        pair[1] = Integer.valueOf(assignment[i][1]);
        this.outputAssignment.add(pair);
      }
    }
    LogComparerOutput("");
    LogComparerOutput("Total Sum of Matched Tokens = " + SMUtils.shortFloatDisplay(sum));
    double score = 0.0D;
    
    float totalSumA = 0.0F;
    float totalSumB = 0.0F;
    for (int j = 0; j < sentenceA.getWords().size(); j++) {
      if ((!this.useContentWordsOnly) || (!((Word)sentenceA.getWords().get(j)).isIsStopWord())) {
        if (!SMUtils.Check4Puntuation(((Word)sentenceA.getWords().get(j)).getRawForm(), ((Word)sentenceA.getWords().get(j)).getPos())) {
          totalSumA += 1.0F;
        }
      }
    }
    for (int j = 0; j < sentenceB.getWords().size(); j++) {
      if ((!this.useContentWordsOnly) || (!((Word)sentenceB.getWords().get(j)).isIsStopWord())) {
        if (!SMUtils.Check4Puntuation(((Word)sentenceB.getWords().get(j)).getRawForm(), ((Word)sentenceB.getWords().get(j)).getPos())) {
          totalSumB += 1.0F;
        }
      }
    }
    float min = totalSumA < totalSumB ? totalSumA : totalSumB;
    float max = totalSumA > totalSumB ? totalSumA : totalSumB;
    if (this.normalizeType == PairwiseComparer.NormalizeType.AVERAGE) {
      score = 2.0D * sum / (totalSumA + totalSumB);
    }
    if (this.normalizeType == PairwiseComparer.NormalizeType.GEOMETRIC) {
      score = sum / Math.sqrt(totalSumA * totalSumB);
    }
    if (this.normalizeType == PairwiseComparer.NormalizeType.MIN) {
      score = sum / min;
    }
    if (this.normalizeType == PairwiseComparer.NormalizeType.MAX) {
      score = sum / max;
    }
    if (this.normalizeType == PairwiseComparer.NormalizeType.TEXTA) {
      score = sum / totalSumA;
    }
    if (this.normalizeType == PairwiseComparer.NormalizeType.TEXTB) {
      score = sum / totalSumB;
    }
    return (float)score;
  }
}

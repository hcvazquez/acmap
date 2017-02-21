package conformance.mapper.utils;

public class Solution {

	protected String etiqueta;
	protected double truePositive;
	protected double falsePositive;
	protected double presicion; 
	protected double recall;
	
	public Solution(String etiqueta, double truePositive, double falsePositive,
			double presicion, double recall) {
		super();
		this.etiqueta = etiqueta;
		this.truePositive = truePositive;
		this.falsePositive = falsePositive;
		this.presicion = presicion;
		this.recall = recall;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public double getTruePositive() {
		return truePositive;
	}

	public double getFalsePositive() {
		return falsePositive;
	}

	public double getPresicion() {
		return presicion;
	}

	public double getRecall() {
		return recall;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public void setTruePositive(double truePositive) {
		this.truePositive = truePositive;
	}

	public void setFalsePositive(double falsePositive) {
		this.falsePositive = falsePositive;
	}

	public void setPresicion(double presicion) {
		this.presicion = presicion;
	}

	public void setRecall(double recall) {
		this.recall = recall;
	}
	
	public String toString(){
		return "Label: "+etiqueta+" ; "+
			   "Mapeos Correctos: "+truePositive+ " ; "+
			   "Mapeos Incorrectos: "+falsePositive+ " ; "+
			   "Accuracy= " + presicion+ " ; "+
			   "Recall= " + recall;
	}

}

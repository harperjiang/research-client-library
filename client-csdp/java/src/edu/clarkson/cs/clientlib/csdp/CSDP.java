package edu.clarkson.cs.clientlib.csdp;

public class CSDP {

	private double objectiveValue;

	private double[] varValues;

	private boolean writeSolution;

	private String solutionFile;

	/**
	 * Solve SDP in the following format
	 * 
	 * Maximize: tr(CX) Constraints: A_i X = a_i X >= 0 (X is positive
	 * semi-definite)
	 */
	public void solve(BlockMatrix c, Constraint[] constraints) {
		
	}
	
	private native void solve(int size, double[][] c, double[][][] constraints, double[] val);

	public double getObjectiveValue() {
		return objectiveValue;
	}

	public void setObjectiveValue(double objectiveValue) {
		this.objectiveValue = objectiveValue;
	}

	public double[] getVarValues() {
		return varValues;
	}

	public void setVarValues(double[] varValues) {
		this.varValues = varValues;
	}

	public boolean isWriteSolution() {
		return writeSolution;
	}

	public void setWriteSolution(boolean writeSolution) {
		this.writeSolution = writeSolution;
	}

	public String getSolutionFile() {
		return solutionFile;
	}

	public void setSolutionFile(String solutionFile) {
		this.solutionFile = solutionFile;
	}

}

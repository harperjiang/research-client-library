package edu.clarkson.cs.clientlib.csdp;

public class CSDP {

	private double objectiveValue;

	private BlockMatrix var;

	private boolean writeSolution;

	private String solutionFile;

	/**
	 * Solve SDP in the following format
	 * 
	 * Maximize: tr(CX) Constraints: A_i X = a_i X >= 0 (X is positive
	 * semi-definite)
	 */
	public native void solve(BlockMatrix c, Constraint[] constraints);

	public double getObjectiveValue() {
		return objectiveValue;
	}

	public void setObjectiveValue(double objectiveValue) {
		this.objectiveValue = objectiveValue;
	}

	public BlockMatrix getVar() {
		return var;
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

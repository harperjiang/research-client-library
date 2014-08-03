package edu.clarkson.cs.clientlib.csdp;

import java.util.ArrayList;
import java.util.List;

public class CSDP {

	private double objectiveValue;

	private BlockMatrix var;

	private boolean writeSolution;

	private String solutionFile;

	private BlockMatrix c;

	private List<Constraint> cons;

	public CSDP() {
		super();
		cons = new ArrayList<Constraint>();
	}

	public void addConstraint(Constraint con) {
		this.cons.add(con);
	}

	public void solve() {
		checkParameters();
		Constraint[] consarray = new Constraint[cons.size()];
		cons.toArray(consarray);
		solve(this.c, consarray);
	}

	protected void checkParameters() {

	}

	/**
	 * Solve SDP in the following format
	 * 
	 * Maximize: tr(CX) Constraints: A_i X = a_i X >= 0 (X is positive
	 * semi-definite)
	 */
	protected native void solve(BlockMatrix c, Constraint[] constraints);

	public double getObjectiveValue() {
		return objectiveValue;
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

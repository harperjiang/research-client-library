package edu.clarkson.cs.clientlib.csdp;

import java.util.ArrayList;
import java.util.List;

public class CSDP implements Target {

	/**
	 * Solve SDP in the following format using CSDP Library
	 * 
	 * Maximize: tr(CX) Constraints: A_i X = a_i X >= 0 (X is positive
	 * semi-definite)
	 */
	protected native void solve(BlockMatrix c, Constraint[] constraints);

	/*
	 * These are the results returned from CSDP
	 */
	private double primalObjective;

	private double dualObjective;

	private BlockMatrix x;

	private double[] y;

	/*
	 * These are the inputs for SDP
	 */
	private BlockMatrix c;

	private List<Constraint> cons;

	/*
	 * These are parameters controlling additional behavior of the library
	 */

	private boolean writeProblem;

	private String problemFile;

	public CSDP() {
		super();
		cons = new ArrayList<Constraint>();
	}

	public BlockMatrix getC() {
		return c;
	}

	public void setC(BlockMatrix c) {
		this.c = c;
	}

	public void addConstraint(Constraint con) {
		this.cons.add(con);
	}

	public List<Constraint> getCons() {
		return cons;
	}

	public void solve() {
		checkParameters();
		Constraint[] consarray = new Constraint[cons.size()];
		cons.toArray(consarray);
		solve(this.c, consarray);
	}

	public double getPrimalObjective() {
		return primalObjective;
	}

	public double getDualObjective() {
		return dualObjective;
	}

	public BlockMatrix getX() {
		return x;
	}

	protected void checkParameters() {
		ConsistencyChecker cc = new ConsistencyChecker();
		this.show(cc);
	}

	@Override
	public void show(Visitor visitor) {
		if (c != null)
			c.show(visitor);
		for (Constraint con : cons) {
			if (con != null)
				con.show(visitor);
		}
		visitor.visit(this);
	}

	public boolean isWriteProblem() {
		return writeProblem;
	}

	public void setWriteProblem(boolean writeProblem) {
		this.writeProblem = writeProblem;
	}

	public String getProblemFile() {
		return problemFile;
	}

	public void setProblemFile(String problemFile) {
		this.problemFile = problemFile;
	}

}

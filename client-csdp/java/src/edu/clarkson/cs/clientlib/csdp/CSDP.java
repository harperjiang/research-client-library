package edu.clarkson.cs.clientlib.csdp;

import java.util.ArrayList;
import java.util.List;

public class CSDP {

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

	public double getPrimalObjective() {
		return primalObjective;
	}

	public double getDualObjective() {
		return dualObjective;
	}

	public BlockMatrix getX() {
		return x;
	}

}

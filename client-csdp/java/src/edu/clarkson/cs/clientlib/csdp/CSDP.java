package edu.clarkson.cs.clientlib.csdp;

public class CSDP {
	
	/**
	 * Solve SDP in the following format
	 * 
	 *  Maximize: tr(CX)
	 *  Constraints: A_i X = a_i
	 *  			 X >= 0 (X is positive semidefinite)
	 */
	public native void solve(BlockMatrix c, Constraint[] constraints);
}

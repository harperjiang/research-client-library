package edu.clarkson.cs.clientlib.csdp;

public class Constraint {

	public SparseMatrix a;

	public double b;

	public Constraint() {
		super();
		a = new SparseMatrix();
	}

	public Constraint(SparseMatrix a, double b) {
		super();
		this.a = a;
		this.b = b;
	}
}

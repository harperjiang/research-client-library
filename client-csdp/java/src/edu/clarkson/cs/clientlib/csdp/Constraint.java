package edu.clarkson.cs.clientlib.csdp;

public class Constraint {

	SparseMatrix a;

	double b;

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

package edu.clarkson.cs.clientlib.csdp;

public class Constraint implements Target {

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

	public SparseMatrix getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public void show(Visitor visitor) {
		visitor.visit(this);
		if (null != a)
			a.show(visitor);
	}
}

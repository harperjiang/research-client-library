package edu.clarkson.cs.clientlib.csdp;

public class SparseBlock {

	int index;

	int size;

	SparseElement[] elements;

	public SparseBlock(int index, int size) {
		super();
		this.index = index;
		this.size = size;
	}

	public void fill(int[] i, int[] j, double[] value) {
		elements = new SparseElement[i.length];

		for (int indx = 0; indx < i.length; indx++) {
			elements[indx] = new SparseElement(i[indx], j[indx], value[indx]);
		}
	}
}

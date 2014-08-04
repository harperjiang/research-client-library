package edu.clarkson.cs.clientlib.csdp;

public class SparseBlock implements Target {

	/**
	 * This index starts from 1 instead of 0
	 */
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

	public int getIndex() {
		return index;
	}

	public int getSize() {
		return size;
	}

	public SparseElement[] getElements() {
		return elements;
	}

	@Override
	public void show(Visitor visitor) {
		visitor.visit(this);
	}

}

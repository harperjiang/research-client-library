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

	public SparseBlock append(SparseBlock... anothers) {
		int newlength = elements.length;
		int newsize = size;
		for (SparseBlock newsb : anothers) {
			newlength += newsb.elements.length;
			newsize += newsb.size;
		}
		SparseElement[] newelem = new SparseElement[newlength];

		System.arraycopy(elements, 0, newelem, 0, elements.length);

		int startindex = size;
		int startlength = elements.length;
		for (SparseBlock another : anothers) {
			for (int i = 0; i < another.elements.length; i++) {
				SparseElement old = another.elements[i];
				newelem[i + startlength] = new SparseElement(
						old.i + startindex, old.j + startindex, old.value);
			}
			startlength += another.elements.length;
			startindex += another.size;
		}

		SparseBlock newblock = new SparseBlock(this.index, newsize);
		newblock.elements = newelem;

		return newblock;
	}

	public SparseBlock append(int[] i, int[] j, double[] value) {
		SparseElement[] newelem = new SparseElement[elements.length + i.length];

		System.arraycopy(elements, 0, newelem, 0, elements.length);

		for (int indx = 0; indx < i.length; indx++) {
			newelem[indx + elements.length] = new SparseElement(i[indx],
					j[indx], value[indx]);
		}

		SparseBlock newblock = new SparseBlock(this.index, this.size);
		newblock.elements = newelem;

		return newblock;
	}
}

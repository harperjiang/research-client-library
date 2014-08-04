package edu.clarkson.cs.clientlib.csdp;

public class SparseMatrix implements Target {

	int size;

	SparseBlock[] blocks;

	public SparseMatrix() {

	}

	public SparseMatrix(int size, SparseBlock... blocks) {
		super();
		this.size = size;
		this.blocks = blocks;
	}

	public int getSize() {
		return size;
	}

	public SparseBlock[] getBlocks() {
		return blocks;
	}

	public void show(Visitor visitor) {
		for (SparseBlock block : blocks) {
			if (null != block)
				block.show(visitor);
		}
		visitor.visit(this);
	}
}

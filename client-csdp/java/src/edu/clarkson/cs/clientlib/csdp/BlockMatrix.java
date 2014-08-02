package edu.clarkson.cs.clientlib.csdp;

/**
 * This class represents a diagonalized block matrix
 * 
 * @author harper
 * 
 */
public class BlockMatrix {

	private MatrixBlock[] blocks;

	private int size = 0;

	public BlockMatrix(MatrixBlock... blocks) {
		this.blocks = blocks;
		for (MatrixBlock block : blocks) {
			this.size += block.size;
		}
	}

	public MatrixBlock[] getBlocks() {
		return blocks;
	}

	public double get(int blockIndex, int i, int j) {
		return blocks[blockIndex].get(i, j);
	}

	public void set(int blockIndex, int i, int j, double value) {
		blocks[blockIndex].set(i, j, value);
	}

	public int getSize() {
		return size;
	}

}

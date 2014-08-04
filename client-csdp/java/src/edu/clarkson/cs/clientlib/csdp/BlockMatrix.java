package edu.clarkson.cs.clientlib.csdp;

import java.text.MessageFormat;

/**
 * This class represents a diagonalized block matrix
 * 
 * @author harper
 * 
 */
public class BlockMatrix implements Target {

	private MatrixBlock[] blocks;

	private int size = 0;

	public BlockMatrix(MatrixBlock... blocks) {
		this.blocks = blocks;
		for (MatrixBlock block : blocks) {
			this.size += block.getSize();
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

	public void show(Visitor visitor) {
		for (MatrixBlock block : blocks) {
			if (block != null)
				block.show(visitor);
		}
		visitor.visit(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(MessageFormat.format("{0} blocks, size {1}\n", blocks.length,
				size));
		for (int i = 0; i < blocks.length; i++) {
			MatrixBlock mb = blocks[i];
			sb.append(MessageFormat.format("Block {0} with size {1}\n", i + 1,
					mb.getSize()));
			sb.append(mb.toString());
		}
		return sb.toString();
	}
}

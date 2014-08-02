package edu.clarkson.cs.clientlib.csdp;

/**
 * This class represents a diagonalized block matrix
 * 
 * @author harper
 * 
 */
public class BlockMatrix {

	private double[][] datas;

	private int[] sizes;

	private int size;

	public BlockMatrix(double[]... datas) {
		sizes = new int[datas.length];
		for (int i = 0; i < datas.length; i++) {
			validate(i, datas[i].length);
			this.size += sizes[i];
		}
		this.datas = datas;
	}

	public double[][] getDatas() {
		return datas;
	}

	public double get(int blockIndex, int i, int j) {
		int reali = i;
		int realj = j;
		if (i > j) {
			reali = j;
			realj = i;
		}

		return datas[blockIndex][ijtok(reali, realj, sizes[blockIndex])];
	}

	public void set(int blockIndex, int i, int j, double value) {
		int reali = i;
		int realj = j;
		if (i > j) {
			reali = j;
			realj = i;
		}
		datas[blockIndex][ijtok(reali, realj, sizes[blockIndex])] = value;
	}

	protected int ijtok(int i, int j, int size) {
		int base = (2 * size - i + 1) * i / 2;
		return base + j - i;
	}

	protected void validate(int index, int size) {
		double val = Math.sqrt(8 * size + 1);
		if (!(((int) val) == val))
			throw new IllegalArgumentException("Incorrect size:" + size);
		else {
			this.sizes[index] = ((int) val - 1) / 2;
		}
	}

	public int getSize() {
		return size;
	}

	public static double[] create(int t) {
		return new double[t * (t + 1) / 2];
	}
}

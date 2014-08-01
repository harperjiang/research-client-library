package edu.clarkson.cs.clientlib.csdp;

/**
 * This class represents a diagonalized block matrix
 * 
 * @author harper
 * 
 */
public class BlockMatrix {

	private double[][] datas;

	private int[] size;

	public BlockMatrix(double[]... datas) {
		size = new int[datas.length];
		for (int i = 0; i < datas.length; i++) {
			validate(i, datas[i].length);
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

		return datas[blockIndex][ijtok(reali, realj, size[blockIndex])];
	}

	public void set(int blockIndex, int i, int j, double value) {
		int reali = i;
		int realj = j;
		if (i > j) {
			reali = j;
			realj = i;
		}
		datas[blockIndex][ijtok(reali, realj, size[blockIndex])] = value;
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
			this.size[index] = ((int) val - 1) / 2;
		}
	}

	public static double[] create(int t) {
		return new double[t * (t + 1) / 2];
	}
}

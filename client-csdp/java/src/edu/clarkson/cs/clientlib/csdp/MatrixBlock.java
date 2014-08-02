package edu.clarkson.cs.clientlib.csdp;

public class MatrixBlock {

	public static final int TYPE_MATRIX = 0;

	public static final int TYPE_DIAG = 1;

	public static final int TYPE_EMPTY = 2;

	public int type;

	public double[] data;

	public int size;

	public MatrixBlock(int type, int size, double[] data) {
		this.type = type;
		this.size = size;
		this.data = data;
	}

	public MatrixBlock(int type, int size) {
		super();
		this.size = size;
		if (type == TYPE_DIAG) {
			data = new double[size];
		}
		if (type == TYPE_MATRIX) {
			data = createMatrix(size);
		}
	}

	protected static int ijtok(int i, int j, int size) {
		int base = (2 * size - i + 1) * i / 2;
		return base + j - i;
	}

	public double get(int i, int j) {
		if (TYPE_DIAG == type)
			return (i == j) ? data[i] : 0;
		if (TYPE_MATRIX == type) {
			int reali = i, realj = j;
			if (reali > realj) {
				reali = j;
				realj = i;
			}
			return data[ijtok(reali, realj, size)];
		}
		return 0;
	}

	public void set(int i, int j, double value) {
		if (TYPE_DIAG == type && i == j) {
			data[i] = value;
			return;
		}
		if (TYPE_MATRIX == type) {
			int reali = i, realj = j;
			if (reali > realj) {
				reali = j;
				realj = i;
			}
			data[ijtok(reali, realj, size)] = value;
		}
	}

	public static double[] createMatrix(int t) {
		return new double[t * (t + 1) / 2];
	}

}

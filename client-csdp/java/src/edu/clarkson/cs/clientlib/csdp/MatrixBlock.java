package edu.clarkson.cs.clientlib.csdp;

import java.text.MessageFormat;
import java.util.Arrays;

public class MatrixBlock implements Target {

	public static final int TYPE_MATRIX = 0;

	public static final int TYPE_DIAG = 1;

	public static final int TYPE_EMPTY = 2;

	private int type;

	private double[] data;

	private int size;

	public MatrixBlock(int type, int size, double[] data) {
		this.type = type;
		this.size = size;
		this.data = data;
	}

	public MatrixBlock(int type, int size) {
		super();
		this.size = size;
		this.type = type;
		if (type == TYPE_DIAG) {
			data = new double[size];
		}
		if (type == TYPE_MATRIX) {
			data = createMatrix(size);
		}
		if (data != null)
			Arrays.fill(data, 0);
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

	public void fill(double[] data) {
		if (data.length != this.data.length)
			throw new IllegalArgumentException("Incorrect data size");
		System.arraycopy(data, 0, this.data, 0, data.length);
	}

	public int getSize() {
		return size;
	}

	public double[] getData() {
		return data;
	}

	public void show(Visitor visitor) {
		visitor.visit(this);
	}

	public static double[] createMatrix(int t) {
		return new double[t * (t + 1) / 2];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sb.append(MessageFormat.format("{0,number,#.00000}", get(i, j)))
						.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

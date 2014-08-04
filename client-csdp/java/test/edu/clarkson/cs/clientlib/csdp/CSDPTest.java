package edu.clarkson.cs.clientlib.csdp;

public class CSDPTest {

	public static void main(String[] args) {
		test2();
	}

	public static void test2() {
		try {
			System.loadLibrary("jcsdp");
		} catch (Throwable t) {
			t.printStackTrace();
		}

		CSDP csdp = new CSDP();

		BlockMatrix c = new BlockMatrix(new MatrixBlock(
				MatrixBlock.TYPE_MATRIX, 2), new MatrixBlock(
				MatrixBlock.TYPE_MATRIX, 3), new MatrixBlock(
				MatrixBlock.TYPE_EMPTY, 2));

		c.set(0, 0, 0, 2d);
		c.set(0, 0, 1, 1d);
		c.set(0, 1, 1, 2d);

		c.set(1, 0, 0, 3);
		c.set(1, 0, 1, 0);
		c.set(1, 0, 2, 1);
		c.set(1, 1, 1, 2);
		c.set(1, 1, 2, 0);
		c.set(1, 2, 2, 3);

		Constraint[] cons = new Constraint[2];

		SparseMatrix sm1 = new SparseMatrix();
		sm1.blocks = new SparseBlock[2];
		sm1.blocks[0] = new SparseBlock(1, 2);
		sm1.blocks[0].fill(new int[] { 0, 0, 1 }, new int[] { 0, 1, 1 },
				new double[] { 3, 1, 3 });
		sm1.blocks[1] = new SparseBlock(3, 2);
		sm1.blocks[1]
				.fill(new int[] { 0 }, new int[] { 0 }, new double[] { 1 });

		cons[0] = new Constraint(sm1, 1);

		SparseMatrix sm2 = new SparseMatrix();
		sm2.blocks = new SparseBlock[2];
		sm2.blocks[0] = new SparseBlock(2, 3);
		sm2.blocks[0].fill(new int[] { 0, 0, 0, 1, 1, 2 }, new int[] { 0, 1, 2,
				1, 2, 2 }, new double[] { 3, 0, 1, 4, 0, 5 });

		sm2.blocks[1] = new SparseBlock(3, 2);
		sm2.blocks[1]
				.fill(new int[] { 1 }, new int[] { 1 }, new double[] { 1 });

		cons[1] = new Constraint(sm2, 2);

		csdp.solve(c, cons);

		System.out.println(csdp.getPrimalObjective());
	}

	public static void test() {
		System.loadLibrary("jcsdp");

		CSDP csdp = new CSDP();

		BlockMatrix param = new BlockMatrix(new MatrixBlock(
				MatrixBlock.TYPE_EMPTY, 3), new MatrixBlock(
				MatrixBlock.TYPE_DIAG, 2), new MatrixBlock(
				MatrixBlock.TYPE_DIAG, 2), new MatrixBlock(
				MatrixBlock.TYPE_DIAG, 2));
		param.getBlocks()[1].fill(new double[] { 1, -1 });
		param.getBlocks()[2].fill(new double[] { 1, -1 });
		param.getBlocks()[3].fill(new double[] { 1, -1 });

		csdp.setC(param);

		// Constraint 1
		SparseBlock sb11 = new SparseBlock(1, 3);
		sb11.fill(new int[] { 0, 0, 0, 1, 1, 2 },
				new int[] { 0, 1, 2, 1, 2, 2 }, new double[] { 4, 4, -2, 4, -2,
						1 });

		SparseBlock sb12 = new SparseBlock(2, 2);
		sb12.fill(new int[] { 0, 1 }, new int[] { 0, 1 },
				new double[] { -1, 1 });

		SparseMatrix sm1 = new SparseMatrix(9, sb11, sb12);

		Constraint c1 = new Constraint(sm1, 8);
		csdp.addConstraint(c1);
		// Constraint 2
		SparseBlock sb21 = new SparseBlock(1, 3);
		sb21.fill(new int[] { 0, 0, 0, 1, 1, 2 },
				new int[] { 0, 1, 2, 1, 2, 2 }, new double[] { 36, 12, -6, 4,
						-2, 1 });

		SparseBlock sb23 = new SparseBlock(3, 2);
		sb23.fill(new int[] { 0, 1 }, new int[] { 0, 1 },
				new double[] { -1, 1 });

		SparseMatrix sm2 = new SparseMatrix(9, sb21, sb23);
		Constraint c2 = new Constraint(sm2, 8);
		csdp.addConstraint(c2);
		// Constraint 3
		SparseBlock sb31 = new SparseBlock(1, 3);
		sb31.fill(new int[] { 0, 0, 0, 1, 1, 2 },
				new int[] { 0, 1, 2, 1, 2, 2 }, new double[] { 64, 48, -8, 36,
						-6, 1 });

		SparseBlock sb34 = new SparseBlock(4, 2);
		sb34.fill(new int[] { 0, 1 }, new int[] { 0, 1 },
				new double[] { -1, 1 });

		SparseMatrix sm3 = new SparseMatrix(9, sb31, sb34);
		Constraint c3 = new Constraint(sm3, 20);
		csdp.addConstraint(c3);

		csdp.solve();
	}
}

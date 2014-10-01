package edu.clarkson.cs.clientlib.csdp;

public class CSDPTest {

	public static void main(String[] args) {
		test2();
	}

	public static void test2() {
		

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

		CSDP csdp = new CSDP();

		int size = 19;

		MatrixBlock negeye = new MatrixBlock(MatrixBlock.TYPE_DIAG, 2);
		negeye.fill(new double[] { -1d, -1d });

		MatrixBlock zero2 = new MatrixBlock(MatrixBlock.TYPE_DIAG, 2);

		MatrixBlock zero = new MatrixBlock(MatrixBlock.TYPE_DIAG, 1);
		zero.fill(new double[] { 0 });

		BlockMatrix param = new BlockMatrix(new MatrixBlock(
				MatrixBlock.TYPE_EMPTY, 3), zero2, zero2, zero2, zero2, zero,
				zero, zero, zero, zero, zero, zero, zero);

		csdp.setC(param);

		double[][] points = new double[][] { new double[] { 2, 2 },
				new double[] { 6, 2 }, new double[] { 8, 6 },
				new double[] { 3, 7 } };

		// Point measured distance
		for (int i = 0; i < points.length; i++) {
			double[] point = points[i];
			// Constraint 1
			SparseBlock sb1 = new SparseBlock(1, 3);
			sb1.fill(new int[] { 0, 0, 0, 1, 1, 2 }, new int[] { 0, 1, 2, 1, 2,
					2 }, new double[] { point[0] * point[0],
					point[0] * point[1], -point[0], point[1] * point[1],
					-point[1], 1 });

			SparseBlock sb2 = new SparseBlock(2 + i, 2);
			sb2.fill(new int[] { 0, 1 }, new int[] { 0, 1 }, new double[] { 1,
					-1 });

			SparseBlock sb3 = new SparseBlock(6 + i, 1);
			sb3.fill(new int[] { 0 }, new int[] { 0 }, new double[] { -1 });

			SparseBlock sb4 = new SparseBlock(10 + i, 1);
			sb4.fill(new int[] { 0 }, new int[] { 0 }, new double[] { 1 });

			SparseMatrix distSm = new SparseMatrix(size, sb1, sb2);

			double distval = (point[0] - 4) * (point[0] - 4) + (point[1] - 4)
					* (point[1] - 4);

			Constraint dist = new Constraint(distSm, distval);
			csdp.addConstraint(dist);

			SparseMatrix lowerSm = new SparseMatrix(size, sb1, sb3);
			Constraint lower = new Constraint(lowerSm, distval - 0.1);
			csdp.addConstraint(lower);

			SparseMatrix upperSm = new SparseMatrix(size, sb1, sb4);
			Constraint upper = new Constraint(upperSm, distval + 0.1);
			csdp.addConstraint(upper);
		}

		// The following 3 constraints says Z starts with I
		SparseBlock sb41 = new SparseBlock(1, 3);
		sb41.fill(new int[] { 0 }, new int[] { 0 }, new double[] { 1 });
		SparseMatrix sm4 = new SparseMatrix(size, sb41);
		Constraint c4 = new Constraint(sm4, 1);
		csdp.addConstraint(c4);

		SparseBlock sb51 = new SparseBlock(1, 3);
		sb51.fill(new int[] { 1 }, new int[] { 1 }, new double[] { 1 });
		SparseMatrix sm5 = new SparseMatrix(size, sb51);
		Constraint c5 = new Constraint(sm5, 1);
		csdp.addConstraint(c5);

		SparseBlock sb61 = new SparseBlock(1, 3);
		sb61.fill(new int[] { 0, 0, 1, 1 }, new int[] { 0, 1, 0, 1 },
				new double[] { 1, 1, 1, 1 });
		SparseMatrix sm6 = new SparseMatrix(size, sb61);
		Constraint c6 = new Constraint(sm6, 2);
		csdp.addConstraint(c6);
		try {
			csdp.solve();
		} catch (Exception e) {

		}
		System.out.println(csdp.getX());
		System.out.println(csdp.getPrimalObjective());
	}
}

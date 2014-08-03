package edu.clarkson.cs.clientlib.csdp;

public class CSDPTest {

	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		System.out.println("Entering Java Test");
		System.out.println(System.getProperty("java.library.path"));
		try {
			System.loadLibrary("jcsdp");
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Loaded CSDP Library");

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

		System.out.println(csdp.getObjectiveValue());
	}
}

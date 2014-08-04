package edu.clarkson.cs.clientlib.csdp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BlockMatrixTest {

	@Test
	public void testGet() {
		double[] block1 = new double[] { 1, 3, 1 };
		double[] block2 = new double[] { 2, 5, 3 };

		BlockMatrix bm = new BlockMatrix(new MatrixBlock(
				MatrixBlock.TYPE_MATRIX, 2, block1), new MatrixBlock(
				MatrixBlock.TYPE_MATRIX, 2, block2));
		assertEquals(1d, bm.get(0, 0, 0), 0.001);
		assertEquals(3d, bm.get(0, 0, 1), 0.001);
		assertEquals(3d, bm.get(0, 1, 0), 0.001);
		assertEquals(1d, bm.get(0, 1, 1), 0.001);
		assertEquals(2d, bm.get(1, 0, 0), 0.001);
		assertEquals(5d, bm.get(1, 0, 1), 0.001);
		assertEquals(5d, bm.get(1, 1, 0), 0.001);
		assertEquals(3d, bm.get(1, 1, 1), 0.001);
	}

	@Test
	public void testSet() {
		BlockMatrix bm = new BlockMatrix(new MatrixBlock(
				MatrixBlock.TYPE_MATRIX, 2), new MatrixBlock(
				MatrixBlock.TYPE_MATRIX, 2));
		bm.set(0, 0, 0, 1d);
		bm.set(0, 0, 1, 2d);
		bm.set(0, 1, 0, 3d);
		bm.set(0, 1, 1, 4d);
		bm.set(1, 0, 0, 5d);
		bm.set(1, 0, 1, 6d);
		bm.set(1, 1, 0, 7d);
		bm.set(1, 1, 1, 8d);

		assertEquals(1d, bm.getBlocks()[0].getData()[0], 0.001);
		assertEquals(3d, bm.getBlocks()[0].getData()[1], 0.001);
		assertEquals(4d, bm.getBlocks()[0].getData()[2], 0.001);
		assertEquals(5d, bm.getBlocks()[1].getData()[0], 0.001);
		assertEquals(7d, bm.getBlocks()[1].getData()[1], 0.001);
		assertEquals(8d, bm.getBlocks()[1].getData()[2], 0.001);
	}

}

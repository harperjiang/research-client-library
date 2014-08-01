package edu.clarkson.cs.clientlib.csdp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BlockMatrixTest {

	@Test
	public void testGet() {
		double[] block1 = new double[] { 1, 3, 1 };
		double[] block2 = new double[] { 2, 5, 3 };

		BlockMatrix bm = new BlockMatrix(block1, block2);
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
		BlockMatrix bm = new BlockMatrix(new double[3], new double[3]);
		bm.set(0, 0, 0, 1d);
		bm.set(0, 0, 1, 2d);
		bm.set(0, 1, 0, 3d);
		bm.set(0, 1, 1, 4d);
		bm.set(1, 0, 0, 5d);
		bm.set(1, 0, 1, 6d);
		bm.set(1, 1, 0, 7d);
		bm.set(1, 1, 1, 8d);

		assertEquals(1d, bm.getDatas()[0][0], 0.001);
		assertEquals(3d, bm.getDatas()[0][1], 0.001);
		assertEquals(4d, bm.getDatas()[0][2], 0.001);
		assertEquals(5d, bm.getDatas()[1][0], 0.001);
		assertEquals(7d, bm.getDatas()[1][1], 0.001);
		assertEquals(8d, bm.getDatas()[1][2], 0.001);
	}

	@Test
	public void testCreate() {
		assertEquals(15, BlockMatrix.create(5).length);
	}

}

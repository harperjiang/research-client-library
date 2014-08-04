package edu.clarkson.cs.clientlib.csdp;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixBlockTest {

	@Test
	public void testGet() {
		MatrixBlock mb = new MatrixBlock(MatrixBlock.TYPE_MATRIX, 3,
				new double[] { 1, 2, 3, 4, 5, 6 });
		assertEquals(1, mb.get(0, 0), 0.001);
		assertEquals(2, mb.get(0, 1), 0.001);
		assertEquals(3, mb.get(0, 2), 0.001);
		assertEquals(2, mb.get(1, 0), 0.001);
		assertEquals(4, mb.get(1, 1), 0.001);
		assertEquals(5, mb.get(1, 2), 0.001);
		assertEquals(3, mb.get(2, 0), 0.001);
		assertEquals(5, mb.get(2, 1), 0.001);
		assertEquals(6, mb.get(2, 2), 0.001);
	}

	@Test
	public void testSet() {
		MatrixBlock mb = new MatrixBlock(MatrixBlock.TYPE_MATRIX, 3);
		mb.set(0, 0, 1);
		mb.set(0, 1, 2);
		mb.set(0, 2, 3);
		mb.set(1, 0, 4);
		mb.set(1, 1, 5);
		mb.set(1, 2, 6);
		mb.set(2, 0, 7);
		mb.set(2, 1, 8);
		mb.set(2, 2, 9);
		assertEquals(1, mb.getData()[0], 0.001);
		assertEquals(4, mb.getData()[1], 0.001);
		assertEquals(7, mb.getData()[2], 0.001);
		assertEquals(5, mb.getData()[3], 0.001);
		assertEquals(8, mb.getData()[4], 0.001);
		assertEquals(9, mb.getData()[5], 0.001);
	}

	@Test
	public void testCreate() {
		assertEquals(15, MatrixBlock.createMatrix(5).length);
	}

	@Test
	public void testIjtok() {
		assertEquals(11, MatrixBlock.ijtok(1, 2, 10));
	}
}

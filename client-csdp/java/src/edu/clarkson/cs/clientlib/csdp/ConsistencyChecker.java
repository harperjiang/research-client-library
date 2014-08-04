package edu.clarkson.cs.clientlib.csdp;

import java.text.MessageFormat;

public class ConsistencyChecker implements Visitor {

	private BlockMatrix bm = null;

	private int conCounter = 0;

	@Override
	public void visit(Target target) {
		if (target instanceof CSDP) {
			CSDP csdp = (CSDP) target;
			for (Constraint c : csdp.getCons()) {
				if (csdp.getC().getSize() != c.getA().getSize())
					throw new IllegalArgumentException();
			}
		} else if (target instanceof BlockMatrix) {
			this.bm = (BlockMatrix) target;
		} else if (target instanceof MatrixBlock) {

		} else if (target instanceof Constraint) {
			conCounter++;
		} else if (target instanceof SparseMatrix) {

		} else if (target instanceof SparseBlock) {
			SparseBlock sb = (SparseBlock) target;
			// Check empty SparseBlock
			if (sb.elements == null || sb.elements.length == 0)
				throw new IllegalArgumentException(MessageFormat.format(
						"Empty SparseBlock:{0}:{1}", conCounter, sb.getIndex()));
			// Check SparseBlock size
			if (bm.getBlocks()[sb.index - 1].getSize() != sb.getSize()) {
				throw new IllegalArgumentException(MessageFormat.format(
						"Inconsistant BlockSize:{0}:{1}", conCounter,
						sb.getIndex()));
			}
		}
	}
}

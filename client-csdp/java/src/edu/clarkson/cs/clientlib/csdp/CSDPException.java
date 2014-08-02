package edu.clarkson.cs.clientlib.csdp;

public class CSDPException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8895833879512673301L;

	private int errorcode;

	public CSDPException(int errorcode) {
		super();
		this.errorcode = errorcode;
	}

	public int getErrorcode() {
		return errorcode;
	}

	// . Success. The problem is primal infeasible.
	public static final int SUCCESS_PRIMAL_INFESIBLE = 1;
	// . Success. The problem is dual infeasible.
	public static final int SUCCESS_DUAL_INFEASIBLE = 2;
	/*
	 * . Partial Success. A solution has been found, but full accuracy was not
	 * achieved. One or more of primal infeasibility, dual infeasibility, or
	 * relative duality gap are larger than their tolerances, but by a factor of
	 * less than 1000.
	 */
	public static final int PARTIAL_SUCCESS_ACCURACY = 3;
	// . Failure. Maximum iterations reached.
	public static final int FAILURE_MAX_ITERATION_EXCEEDED = 4;
	// . Failure. Stuck at edge of primal feasibility.
	public static final int FAILURE_STUCK_AT_EDGE_PRIMAL = 5;
	// . Failure. Stuck at edge of dual infeasibility.
	public static final int FAILURE_STUCK_AT_EDGE_DUAL = 6;
	// . Failure. Lack of progress.
	public static final int FAILURE_LACK_OF_PROGRESS = 7;
	// . Failure. X, Z, or O was singular.
	public static final int FAILURE_SINGULAR = 8;
	// . Failure. X, Z, or O was singular.
	public static final int FAILURE_INVALID_NUM = 9;

}

#include "edu_clarkson_cs_clientlib_csdp_CSDP.h"
#include "declarations.h"

void extract_param_matrix(JNIEnv * env, jobject c, struct blockmatrix* C,
		int* matrix_size) {

}

void extract_constraints(JNIEnv * env, jobjectArray cons, double* b,
		struct constraintmatrix * constraints, int* cons_size) {

}

void setup_ret_value(JNIEnv* env, jobject self, struct blockmatrix* X,
		double pobj, double dobj) {

}

void setup_error(JNIEnv*env, int ret) {

}
/*
 * Part of the code are directly copied from the example contained in CSDP
 *
 * Class:     edu_clarkson_cs_clientlib_csdp_CSDP
 * Method:    solve
 * Signature: (Ledu/clarkson/cs/clientlib/csdp/BlockMatrix;[Ledu/clarkson/cs/clientlib/csdp/Constraint;)V
 */
JNIEXPORT void JNICALL Java_edu_clarkson_cs_clientlib_csdp_CSDP_solve(
		JNIEnv * env, jobject self, jobject c, jobjectArray cons) {
	/*
	 * The problem and solution data.
	 */
	struct blockmatrix C;
	double *b;
	struct constraintmatrix *constraints;

	int matrix_size;
	int cons_size;

	extract_param_matrix(env, c, &C, &matrix_size);
	extract_constraints(env, cons, b, constraints, &cons_size);

	/*
	 * Storage for the initial and final solutions.
	 */
	struct blockmatrix X, Z;
	double *y;
	double pobj, dobj;

	/*
	 * A return code for the call to easy_sdp().
	 */

	int ret;

	initsoln(matrix_size, cons_size, C, b, constraints, &X, &y, &Z);
	ret = easy_sdp(matrix_size, cons_size, C, b, constraints, 0.0, &X, &y, &Z,
			&pobj, &dobj);

	if (ret == 0) {
		setup_ret_value(env, self, &X, pobj, dobj);
		free_prob(matrix_size, cons_size, C, b, constraints, X, y, Z);
	} else {
		free_prob(matrix_size, cons_size, C, b, constraints, X, y, Z);
		setup_error(env, ret);
	}

}


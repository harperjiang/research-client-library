#include "edu_clarkson_cs_clientlib_csdp_CSDP.h"
#include "error_handle.h"
#include "declarations.h"

#include <stdlib.h>
#include <string.h>

int from_array_size(int size) {
	return 0;
}

int myijtok(int size, int i, int j) {
	return 0;
}

void extract_param_matrix(JNIEnv * env, jobjectArray blockMatrix,
		struct blockmatrix* C) {
	C->nblocks = env->GetArrayLength(blockMatrix);
	C->blocks = (struct blockrec*) malloc(
			(C->nblocks + 1) * sizeof(struct blockrec));
	if (C->blocks == NULL) {
		throw_memory_error(env);
	}

	for (int i = 0; i < C->nblocks; i++) {
		struct blockrec * block = C->blocks + i + 1;
		jdoubleArray data = (jdoubleArray) env->GetObjectArrayElement(
				blockMatrix, i);
		block->blockcategory = MATRIX;
		int block_array_size = env->GetArrayLength(data);
		int block_size = from_array_size(block_array_size);
		block->blocksize = block_size;
		block->data.mat = (double*) malloc(
				block_size * block_size * sizeof(double));
		if (block->data.mat == NULL) {
			throw_memory_error(env);
		}

		jdouble* values = env->GetDoubleArrayElements(data, 0);
		for (int i = 0; i < block_size; i++) {
			for (int j = 0; j < block_size; j++) {
				int index = myijtok(block_size, i, j);
				block->data.mat(ijtok(i, j, block_size)) = values[index];
			}
		}
	}
}

void extract_constraints(JNIEnv * env, jobjectArray cons, jdoubleArray consval,
		double* b, struct constraintmatrix * constraints, int* cons_size) {
	*cons_size = env->GetArrayLength(cons);
	// Right hand side value
	jdouble* consval_inarray = env->GetDoubleArrayElements(consval, 0);
	b = (double*) malloc((*cons_size + 1) * sizeof(double));
	if (b == NULL) {
		throw_memory_error(env);
	}
	memcpy(b + 1, consval_inarray, *cons_size * sizeof(double));

	// Constraint matrix
	constraints = (struct constraintmatrix*) malloc(
			(*cons_size + 1) * sizeof(struct constraintmatrix));
	if (NULL == constraints) {
		throw_memory_error(env);
	}

	for (int i = 0; i < *cons_size; i++) {
		struct constraintmatrix* cmatrix = constraints + i + 1;

	}
}

void setup_ret_value(JNIEnv* env, jobject self, struct blockmatrix* X,
		double pobj, double dobj) {

}

/*
 * Part of the code are directly copied from the example contained in CSDP
 *
 * Class:     edu_clarkson_cs_clientlib_csdp_CSDP
 * Method:    solve
 * Signature: (Ledu/clarkson/cs/clientlib/csdp/BlockMatrix;[Ledu/clarkson/cs/clientlib/csdp/Constraint;)V
 */
JNIEXPORT void JNICALL Java_edu_clarkson_cs_clientlib_csdp_CSDP_solve(
		JNIEnv * env, jobject self, jint size, jobjectArray c,
		jobjectArray cons, jdoubleArray consval) {
	/*
	 * The problem and solution data.
	 */
	struct blockmatrix C;
	double *b;
	struct constraintmatrix *constraints;

	int matrix_size = size;
	int cons_size;

	extract_param_matrix(env, c, &C);
	extract_constraints(env, cons, consval, b, constraints, &cons_size);

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
		throw_csdp_error(env, ret);
	}

}


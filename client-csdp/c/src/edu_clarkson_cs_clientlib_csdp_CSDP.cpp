#include <jni.h>
#include <stdlib.h>
#include <string.h>

#ifndef NOSHORTS
#define NOSHORTS
#endif

#include "edu_clarkson_cs_clientlib_csdp_CSDP.h"
#include "error_handle.h"

#ifdef __cplusplus
extern "C" {
#endif
#include "declarations.h"

#ifdef __cplusplus
}
#endif

int myijtok(int i, int j, int size) {
	int reali = i, realj = j;
	if (reali > realj) {
		reali = j;
		realj = i;
	}
	int base = (size - reali + size + 1) * reali / 2;
	return base + realj - reali;
}

int triangle_matrix_size(int dim) {
	return dim * (dim + 1) / 2;
}

jclass CLS_CSDP;
jclass CLS_BM;
jclass CLS_MB;
jclass CLS_CONS;
jclass CLS_SM;
jclass CLS_SB;
jclass CLS_SE;

jclass CLS_OOM;
jclass CLS_CSDPE;

jfieldID FID_CSDP_PRIMAL;
jfieldID FID_CSDP_DUAL;
jfieldID FID_CSDP_X;
jfieldID FID_CSDP_Y;
jfieldID FID_CSDP_WRITE;
jfieldID FID_CSDP_FILENAME;
jfieldID FID_BM_BLOCKS;
jfieldID FID_BM_SIZE;
jfieldID FID_MB_TYPE;
jfieldID FID_MB_SIZE;
jfieldID FID_MB_DATA;
jfieldID FID_CONS_A;
jfieldID FID_CONS_B;
jfieldID FID_SM_BLOCKS;
jfieldID FID_SM_SIZE;
jfieldID FID_SB_ELEMS;
jfieldID FID_SB_INDEX;
jfieldID FID_SB_SIZE;
jfieldID FID_SE_I;
jfieldID FID_SE_J;
jfieldID FID_SE_VALUE;

jmethodID MID_CSDPE_CON;

JNIEnv* globalEnv;
jobject globalSelf;

jclass load_class(JNIEnv* env, const char* name) {
	jclass local_ref = env->FindClass(name);
	if (env->ExceptionCheck()) {
		env->Throw(env->ExceptionOccurred());
	}
	jclass global_ref = (jclass) env->NewGlobalRef(local_ref);
	return global_ref;
}

void extract_field_ids(JNIEnv * env) {
	CLS_CSDP = load_class(env, "edu/clarkson/cs/clientlib/csdp/CSDP");
	CLS_BM = load_class(env, "edu/clarkson/cs/clientlib/csdp/BlockMatrix");
	CLS_MB = load_class(env, "edu/clarkson/cs/clientlib/csdp/MatrixBlock");
	CLS_CONS = load_class(env, "edu/clarkson/cs/clientlib/csdp/Constraint");
	CLS_SM = load_class(env, "edu/clarkson/cs/clientlib/csdp/SparseMatrix");
	CLS_SB = load_class(env, "edu/clarkson/cs/clientlib/csdp/SparseBlock");
	CLS_SE = load_class(env, "edu/clarkson/cs/clientlib/csdp/SparseElement");
	CLS_OOM = load_class(env, "java/lang/OutOfMemoryError");
	CLS_CSDPE = load_class(env, "edu/clarkson/cs/clientlib/csdp/CSDPException");

	FID_CSDP_PRIMAL = env->GetFieldID(CLS_CSDP, "primalObjective", "D");
	FID_CSDP_DUAL = env->GetFieldID(CLS_CSDP, "dualObjective", "D");
	FID_CSDP_X = env->GetFieldID(CLS_CSDP, "x",
			"Ledu/clarkson/cs/clientlib/csdp/BlockMatrix;");
	FID_CSDP_Y = env->GetFieldID(CLS_CSDP, "y", "[D");

	FID_CSDP_WRITE = env->GetFieldID(CLS_CSDP, "writeProblem", "Z");
	FID_CSDP_FILENAME = env->GetFieldID(CLS_CSDP, "problemFile",
			"Ljava/lang/String;");

	FID_BM_BLOCKS = env->GetFieldID(CLS_BM, "blocks",
			"[Ledu/clarkson/cs/clientlib/csdp/MatrixBlock;");
	FID_BM_SIZE = env->GetFieldID(CLS_BM, "size", "I");

	FID_MB_TYPE = env->GetFieldID(CLS_MB, "type", "I");
	FID_MB_SIZE = env->GetFieldID(CLS_MB, "size", "I");
	FID_MB_DATA = env->GetFieldID(CLS_MB, "data", "[D");

	FID_CONS_A = env->GetFieldID(CLS_CONS, "a",
			"Ledu/clarkson/cs/clientlib/csdp/SparseMatrix;");
	FID_CONS_B = env->GetFieldID(CLS_CONS, "b", "D");

	FID_SM_SIZE = env->GetFieldID(CLS_SM, "size", "I");
	FID_SM_BLOCKS = env->GetFieldID(CLS_SM, "blocks",
			"[Ledu/clarkson/cs/clientlib/csdp/SparseBlock;");

	FID_SB_ELEMS = env->GetFieldID(CLS_SB, "elements",
			"[Ledu/clarkson/cs/clientlib/csdp/SparseElement;");
	FID_SB_INDEX = env->GetFieldID(CLS_SB, "index", "I");
	FID_SB_SIZE = env->GetFieldID(CLS_SB, "size", "I");

	FID_SE_I = env->GetFieldID(CLS_SE, "i", "I");
	FID_SE_J = env->GetFieldID(CLS_SE, "j", "I");
	FID_SE_VALUE = env->GetFieldID(CLS_SE, "value", "D");

	MID_CSDPE_CON = env->GetMethodID(CLS_CSDPE, "<init>", "(I)V");

	if (env->ExceptionCheck()) {
		env->Throw(env->ExceptionOccurred());
	}
}

void release_class_ids(JNIEnv * env) {
	env->DeleteGlobalRef(CLS_CSDP);
	env->DeleteGlobalRef(CLS_BM);
	env->DeleteGlobalRef(CLS_MB);
	env->DeleteGlobalRef(CLS_CONS);
	env->DeleteGlobalRef(CLS_SM);
	env->DeleteGlobalRef(CLS_SB);
	env->DeleteGlobalRef(CLS_SE);
	env->DeleteGlobalRef(CLS_OOM);
	env->DeleteGlobalRef(CLS_CSDPE);
}

void extract_param_matrix(JNIEnv * env, jobject blockMatrix,
		struct blockmatrix* C, int *matrix_size) {
	*matrix_size = env->GetIntField(blockMatrix, FID_BM_SIZE);
	jobjectArray mtxblocks = (jobjectArray) env->GetObjectField(blockMatrix,
			FID_BM_BLOCKS);

	C->nblocks = env->GetArrayLength(mtxblocks);
	C->blocks = (struct blockrec*) malloc(
			(C->nblocks + 1) * sizeof(struct blockrec));
	if (C->blocks == NULL) {
		throw_memory_error(env, "Failed to allocate C Matrix");
	}

	for (int i = 0; i < C->nblocks; i++) {
		struct blockrec * block = C->blocks + i + 1;

		jobject mtb = env->GetObjectArrayElement(mtxblocks, i);
		jint mtb_type = env->GetIntField(mtb, FID_MB_TYPE);
		jint mtb_size = env->GetIntField(mtb, FID_MB_SIZE);
		jdouble* mtb_data = NULL;
		jdoubleArray mtb_data_array = NULL;
		// EMPTY block will have an empty data array
		if (mtb_type == 0 || mtb_type == 1) {
			mtb_data_array = (jdoubleArray) env->GetObjectField(mtb,
					FID_MB_DATA);
			mtb_data = env->GetDoubleArrayElements(mtb_data_array, 0);
		}
		block->blocksize = mtb_size;
		if (mtb_type == 0) {
			block->blockcategory = MATRIX;
			block->data.mat = (double*) malloc(
					mtb_size * mtb_size * sizeof(double));
			if (block->data.mat == NULL) {
				throw_memory_error(env, "Failed to allocate C Matrix Block");
			}
			for (int i = 0; i < mtb_size; i++) {
				for (int j = 0; j < mtb_size; j++) {
					block->data.mat[ijtok(i+1, j + 1, mtb_size)] =
							mtb_data[myijtok(i, j, mtb_size)];
				}
			}
		} else if (mtb_type == 1 || mtb_type == 2) {
			block->blockcategory = DIAG;
			block->data.vec = (double*) malloc((mtb_size + 1) * sizeof(double));
			if (block->data.vec == NULL) {
				throw_memory_error(env,
						"Failed to allocate C Matrix Block Data");
			}
			for (int i = 0; i < mtb_size; i++) {
				block->data.vec[i + 1] = (mtb_type == 1 ? mtb_data[i] : 0);
			}
		}
		if (mtb_data != NULL) {
			env->ReleaseDoubleArrayElements(mtb_data_array, mtb_data, 0);
			env->DeleteLocalRef(mtb_data_array);
		}
		env->DeleteLocalRef(mtb);
	}
	env->DeleteLocalRef(mtxblocks);
}

void extract_constraints(JNIEnv * env, jobjectArray cons, double** b,
		struct constraintmatrix ** constraints, int* cons_size) {
	*cons_size = env->GetArrayLength(cons);

	*b = (double*) malloc((*cons_size + 1) * sizeof(double));
	*constraints = (struct constraintmatrix*) malloc(
			(*cons_size + 1) * sizeof(struct constraintmatrix));
	if (NULL == *b || NULL == *constraints) {
		throw_memory_error(env, "Failed to allocate constraints space");
	}
	memset((void*) (*constraints), 0, sizeof(struct constraintmatrix));
	(*b)[0] = 0;
	for (int con_idx = 0; con_idx < *cons_size; con_idx++) {
		jobject con = env->GetObjectArrayElement(cons, con_idx);
		// Right hand side value
		(*b)[con_idx + 1] = env->GetDoubleField(con, FID_CONS_B);

		// Constraint matrix
		jobject jcmtx = env->GetObjectField(con, FID_CONS_A);

		struct constraintmatrix* cmtx = *constraints + con_idx + 1;
		cmtx->blocks = NULL;

		jobjectArray jblocks = (jobjectArray) env->GetObjectField(jcmtx,
				FID_SM_BLOCKS);
		jint jblocks_size = env->GetArrayLength(jblocks);

		for (int blk_idx = 0; blk_idx < jblocks_size; blk_idx++) {
			jobject block = env->GetObjectArrayElement(jblocks, blk_idx);

			jint block_size = env->GetIntField(block, FID_SB_SIZE);
			jint block_index = env->GetIntField(block, FID_SB_INDEX);
			jobjectArray block_entries = (jobjectArray) env->GetObjectField(
					block, FID_SB_ELEMS);
			jint entry_size = env->GetArrayLength(block_entries);

			struct sparseblock* blockptr = (struct sparseblock*) malloc(
					sizeof(struct sparseblock));
			if (NULL == blockptr) {
				throw_memory_error(env,
						"Failed to allocate constraint matrix block");
			}
			blockptr->blocknum = block_index;
			blockptr->blocksize = block_size;
			blockptr->constraintnum = con_idx + 1;
			blockptr->issparse = 0;
			blockptr->next = NULL;
			blockptr->nextbyblock = NULL;

			blockptr->entries = (double*) malloc(
					(entry_size + 1) * sizeof(double));
			blockptr->iindices = (int*) malloc((entry_size + 1) * sizeof(int));
			blockptr->jindices = (int*) malloc((entry_size + 1) * sizeof(int));
			if (blockptr->entries == NULL || blockptr->iindices == NULL
					|| blockptr->jindices == NULL) {
				throw_memory_error(env,
						"Failed to allocate constraint matrix block data");
			}
			blockptr->numentries = entry_size;
			blockptr->entries[0] = 0;
			blockptr->iindices[0] = 0;
			blockptr->jindices[0] = 0;
			for (int entry_idx = 0; entry_idx < entry_size; entry_idx++) {
				jobject entry = env->GetObjectArrayElement(block_entries,
						entry_idx);
				jint ei = env->GetIntField(entry, FID_SE_I);
				jint ej = env->GetIntField(entry, FID_SE_J);
				jdouble evalue = env->GetDoubleField(entry, FID_SE_VALUE);
				blockptr->entries[entry_idx + 1] = evalue;
				// We start from 0, the library start from 1
				blockptr->iindices[entry_idx + 1] = ei + 1;
				blockptr->jindices[entry_idx + 1] = ej + 1;

				env->DeleteLocalRef(entry);
			}

			// Maintain linked list
			if (cmtx->blocks == NULL) {
				cmtx->blocks = blockptr;
			} else {
				cmtx->blocks->next = blockptr;
			}

			env->DeleteLocalRef(block_entries);
			env->DeleteLocalRef(block);
		}
		env->DeleteLocalRef(jblocks);
		env->DeleteLocalRef(jcmtx);
		env->DeleteLocalRef(con);
	}
}

void setup_ret_value(JNIEnv* env, jobject self, int size, int cons_size,
		struct blockmatrix* X, double* y, double pobj, double dobj) {
	env->SetDoubleField(self, FID_CSDP_PRIMAL, pobj);
	env->SetDoubleField(self, FID_CSDP_DUAL, dobj);

	// Allocate double array
	jdoubleArray yarray = env->NewDoubleArray(cons_size);
	jdouble* yarraydata = env->GetDoubleArrayElements(yarray, 0);
	memcpy(yarraydata, y + 1, cons_size * sizeof(double));
	env->ReleaseDoubleArrayElements(yarray, yarraydata, 0);
	env->SetObjectField(self, FID_CSDP_Y, yarray);

	// Allocate Matrix Blocks
	jobject varmtx = env->AllocObject(CLS_BM);
	env->SetObjectField(self, FID_CSDP_X, varmtx);

	// Matrix Size
	env->SetIntField(varmtx, FID_BM_SIZE, size);

	jobjectArray blockArray = env->NewObjectArray(X->nblocks, CLS_MB, NULL);
	env->SetObjectField(varmtx, FID_BM_BLOCKS, blockArray);

	for (int blk_idx = 0; blk_idx < X->nblocks; blk_idx++) {
		struct blockrec* cblock = X->blocks + blk_idx + 1;

		jobject jblock = env->AllocObject(CLS_MB);

		env->SetIntField(jblock, FID_MB_SIZE, cblock->blocksize);
		jdouble* datas = NULL;
		jdoubleArray dataArray = NULL;
		if (cblock->blockcategory == MATRIX) {
			env->SetIntField(jblock, FID_MB_TYPE, 0);
			dataArray = env->NewDoubleArray(
					triangle_matrix_size(cblock->blocksize));
			env->SetObjectField(jblock, FID_MB_DATA, dataArray);
			datas = env->GetDoubleArrayElements(dataArray, 0);
			for (int data_i = 0; data_i < cblock->blocksize; data_i++) {
				for (int data_j = data_i; data_j < cblock->blocksize;
						data_j++) {
					datas[myijtok(data_i, data_j, cblock->blocksize)] =
							cblock->data.mat[ijtok(data_i+1, data_j + 1,
									cblock->blocksize)];
				}
			}
		} else if (cblock->blockcategory == DIAG) {
			env->SetIntField(jblock, FID_MB_TYPE, 1);
			dataArray = env->NewDoubleArray(cblock->blocksize);
			env->SetObjectField(jblock, FID_MB_DATA, dataArray);
			datas = env->GetDoubleArrayElements(dataArray, 0);

			memcpy(datas, cblock->data.vec + 1, cblock->blocksize);
		}
		if (datas != NULL) {
			env->ReleaseDoubleArrayElements(dataArray, datas, 0);
			env->DeleteLocalRef(dataArray);
		}
		env->SetObjectArrayElement(blockArray, blk_idx, jblock);

		env->DeleteLocalRef(jblock);
	}
	env->DeleteLocalRef(blockArray);
	env->DeleteLocalRef(yarray);
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

	globalEnv = env;
	globalSelf = self;
	// preparation, extract all field ID

	extract_field_ids(env);
	/*
	 * The problem and solution data.
	 */
	struct blockmatrix C;
	double *b;
	struct constraintmatrix *constraints;

	int matrix_size;
	int cons_size;

	extract_param_matrix(env, c, &C, &matrix_size);
	extract_constraints(env, cons, &b, &constraints, &cons_size);

	/*
	 * Storage for the initial and final solutions.
	 */
	struct blockmatrix X, Z;
	double *y;
	double pobj, dobj;

	jboolean write_problem = env->GetBooleanField(self, FID_CSDP_WRITE);
	if (write_problem) {
		jstring problem_file = (jstring)env->GetObjectField(self, FID_CSDP_FILENAME);
		const char* name = env->GetStringUTFChars(problem_file, 0);
		write_prob((char*)name, matrix_size, cons_size, C, b, constraints);
		env->ReleaseStringUTFChars(problem_file,name);
		env->DeleteLocalRef(problem_file);
	}

	/*
	 * A return code for the call to easy_sdp().
	 */

	int ret;

	initsoln(matrix_size, cons_size, C, b, constraints, &X, &y, &Z);
	ret = easy_sdp(matrix_size, cons_size, C, b, constraints, 0.0, &X, &y, &Z,
			&pobj, &dobj);

	setup_ret_value(env, self, matrix_size, cons_size, &X, y, pobj, dobj);

	if (ret != 0) {
		throw_csdp_error(env, ret);
	}

	// Release resources
	release_class_ids(env);
	free_prob(matrix_size, cons_size, C, b, constraints, X, y, Z);

}

#ifdef __cplusplus
extern "C" {
#endif

int user_exit(int n, int k, struct blockmatrix C, double *a, double dobj,
		double pobj, double constant_offset,
		struct constraintmatrix *constraints, struct blockmatrix X, double *y,
		struct blockmatrix Z, struct paramstruc params) {
//	setup_ret_value(globalEnv, globalSelf, n, k, &X, y, pobj, dobj);
	return 0;
}

#ifdef __cplusplus
}
#endif


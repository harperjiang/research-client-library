/*
 * error_handle.c
 *
 *  Created on: Aug 2, 2014
 *      Author: harper
 */
#include <jni.h>
#include "error_handle.h"

extern jclass CLS_OOM;
extern jclass CLS_CSDPE;
extern jmethodID MID_CSDPE_CON;

void throw_memory_error(JNIEnv* env, const char* msg) {
	env->ThrowNew(CLS_OOM, msg);
}

void throw_csdp_error(JNIEnv * env, int ret) {
	jthrowable exception = (jthrowable) env->NewObject(CLS_CSDPE, MID_CSDPE_CON,
			ret);
	env->Throw(exception);
}

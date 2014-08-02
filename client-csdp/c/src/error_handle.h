/*
 * error_handle.h
 *
 *  Created on: Aug 2, 2014
 *      Author: harper
 */

#ifndef ERROR_HANDLE_H_
#define ERROR_HANDLE_H_
#include<jni.h>

void throw_memory_error(JNIEnv* env, const char* msg);
void throw_csdp_error(JNIEnv * env, int ret);

#endif /* ERROR_HANDLE_H_ */

/*
 * main.c
 *
 *  Created on: Aug 2, 2014
 *      Author: harper
 */

#include <jni.h>
#include <unistd.h>

int main() {
	JavaVM *jvm; /* denotes a Java VM */
	JNIEnv *env; /* pointer to native method interface */
	JavaVMInitArgs vm_args; /* JDK/JRE 6 VM initialization arguments */
	JavaVMOption* options = new JavaVMOption[2];
	options[0].optionString =
			"-Djava.class.path=/home/harper/Repositories/research-client-library/client-csdp/build/classes";
	options[1].optionString =
			"-Djava.library.path=/home/harper/Repositories/research-client-library/client-csdp/build";
	vm_args.version = JNI_VERSION_1_6;
	vm_args.nOptions = 2;
	vm_args.options = options;
	vm_args.ignoreUnrecognized = false;
	/* load and initialize a Java VM, return a JNI interface
	 * pointer in env */
	JNI_CreateJavaVM(&jvm, (void**) &env, &vm_args);
	delete options;
	/* invoke the Main.test method using the JNI */
	jclass cls = env->FindClass("edu/clarkson/cs/clientlib/csdp/CSDPTest");
	if (env->ExceptionCheck()) {
		return 1;
	}
	jmethodID mid = env->GetStaticMethodID(cls, "test", "()V");
	env->CallStaticVoidMethod(cls, mid);

	while (true) {
		sleep(1000);
	}
	/* We are done. */
	jvm->DestroyJavaVM();

	return 0;
}


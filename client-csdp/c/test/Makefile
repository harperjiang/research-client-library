OS := $(shell uname)

ifeq ($(OS),Darwin)
	JAVA_HEADER=-I"/System/Library/Frameworks/JavaVM.framework/Versions/Current/Headers"
	CSDP_HOME=/Users/harper/Documents/Libraries/Csdp-6.1.1
	CSDP_HEADER=-I"$(CSDP_HOME)/include"
	GCC=g++
	OUTPUT=libcsdp.dylib
	LIBFLAG=-dynamiclib -undefined suppress -flat_namespace -L"$(CSDP_HOME)/lib" -lsdp
	CFLAG=-fPIC -c $(JAVA_HEADER) 
else
	JAVA_HOME=/usr/lib/jvm/jdk1.8.0_05
	JAVA_HEADER=-I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/linux"
	CSDP_HOME=/home/harper/Libraries/Csdp-6.1.1
	CSDP_HEADER=-I"$(CSDP_HOME)/include"
	GCC=g++
	LIBPATH=-L"$(JAVA_HOME)/jre/lib/amd64/server/"
	LIBFLAG=-ljvm
	OUTPUT=tester
	CFLAG=-fPIC -c -g -O0 $(JAVA_HEADER)
endif


all: 
	make tester
	
tester: tester.o
	$(GCC) -o $(OUTPUT) $(LIBPATH) tester.o $(LIBFLAG)
	
tester.o : tester.cpp
	$(GCC) $(CFLAG) -o $@ tester.cpp


clean:
	rm -rf *.o
	rm -rf tester
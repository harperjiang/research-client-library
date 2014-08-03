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
	OUTPUT=tester
	CFLAG=-fPIC -c -g -O0 $(JAVA_HEADER)
endif


all: tester example 
	
	
tester: tester.o
	$(GCC) -o $(OUTPUT) $(LIBPATH) tester.o -ljvm
	
tester.o : tester.cpp
	$(GCC) $(CFLAG) -o $@ tester.cpp

example : example.o
	gcc -o example example.o -L$(CSDP_HOME)/lib -Wl,-Bstatic -lsdp -Wl,-Bdynamic -llapack -lblas -lgfortran -lm
example.o : example.c
	gcc -c example.c -o $@ -O0 -g -ansi -Wall -DNOSHORTS $(CSDP_HEADER)  


clean:
	rm -rf *.o
	rm -rf tester
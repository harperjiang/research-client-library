OS := $(shell uname)

ifeq ($(OS),Darwin)
	JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home
	JAVA_HEADER=-I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/darwin"
	CSDP_HOME=/Users/harper/Documents/Libraries/Csdp-6.1.1
	CSDP_HEADER=-I"$(CSDP_HOME)/include"
	GCC=g++
	LIBFLAG=-dynamiclib -undefined suppress -flat_namespace -L"$(CSDP_HOME)/lib" -lsdp
	LIBPATH=-L"$(JAVA_HOME)/jre/lib/server/"
	CFLAG=-fPIC -c $(JAVA_HEADER) 
	LIBFLAG=-lsdp -llapack -lblas -lgfortran -lm
else
	JAVA_HOME=/usr/lib/jvm/jdk1.8.0_05
	JAVA_HEADER=-I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/linux"
	CSDP_HOME=/home/harper/Libraries/Csdp-6.1.1
	CSDP_HEADER=-I"$(CSDP_HOME)/include"
	GCC=g++
	LIBPATH=-L"$(JAVA_HOME)/jre/lib/amd64/server/" 
	CFLAG=-fPIC -c -g -O0 -ansi -Wall -DNOSHORTS $(JAVA_HEADER)
	LIBFLAG=-Wl,-Bstatic -lsdp -Wl,-Bdynamic -llapack -lblas -lgfortran -lm
endif


all: tester example 
	
	
tester: tester.o
	$(GCC) -o $@ $(LIBPATH) tester.o -ljvm
	
tester.o : tester.cpp
	$(GCC) $(CFLAG) -o $@ tester.cpp

example : example.o
	gcc -o $@ example.o -L$(CSDP_HOME)/lib $(LIBFLAG)
example.o : example.c
	gcc -c example.c -o $@ $(CFLAG) $(CSDP_HEADER)  


clean:
	rm -rf *.o
	rm -rf tester
	rm -rf example
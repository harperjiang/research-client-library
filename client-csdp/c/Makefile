OS := $(shell uname)

ifeq ($(OS),Darwin)
	JAVA_HEADER=-I"/System/Library/Frameworks/JavaVM.framework/Versions/Current/Headers"
	CSDP_HOME=/Users/harper/Documents/Libraries/Csdp-6.1.1
	CSDP_HEADER=-I"$(CSDP_HOME)/include"
	GCC=g++
	OUTPUT=libcsdp.dylib
	LIBFLAG=-dynamiclib -undefined suppress -flat_namespace -L"$(CSDP_HOME)/lib" -lsdp
	CFLAG=-fPIC -c $(JAVA_HEADER) $(CSDP_HEADER) 
else
	JAVA_HOME=/usr/lib/jvm/jdk_1.8.0_05
	JAVA_HEADER=-I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/linux"
	CSDP_HOME=/home/harper/Libraries/Csdp-6.1.1
	CSDP_HEADER=-I"$(CSDP_HOME)/include"
	GCC=g++
	OUTPUT=libcsdp.so
	LIBFLAG=-shared -L"$(CSDP_HOME)/lib" -Wl,-whole-archive -lsdp -Wl,-no-whole-archive
	CFLAG=-fPIC -c $(JAVA_HEADER) $(CSDP_HEADER) 
endif


all: 
	make clean
	make libcsdp
	
libcsdp: edu_clarkson_cs_clientlib_csdp_CSDP.o error_handle.o
	$(GCC) -o $(OUTPUT) edu_clarkson_cs_clientlib_csdp_CSDP.o error_handle.o $(LIBFLAG) 

error_handle.o : error_handle.cpp
	$(GCC) $(CFLAG) -o $@ error_handle.cpp

edu_clarkson_cs_clientlib_csdp_CSDP.o : edu_clarkson_cs_clientlib_csdp_CSDP.cpp
	$(GCC) $(CFLAG) -o $@ edu_clarkson_cs_clientlib_csdp_CSDP.cpp  

clean:
	rm -rf *.o
	rm -rf $(OUTPUT) 

JAVA_HOME=/usr/lib/jvm/jdk1.8.0_05/
CSDP_HOME=/home/harper/Libraries/Csdp-6.1.1
GCC=gcc
OUTPUT=libcsdp.so

all: 
	make clean
	make libcsdp
	
libcsdp: edu_clarkson_cs_clientlib_csdp_CSDP.o
	$(GCC) -shared -L"$(CSDP_HOME)/lib" -Wl,-whole-archive -lsdp -Wl,-no-whole-archive -o libcsdp.so edu_clarkson_cs_clientlib_csdp_CSDP.o

edu_clarkson_cs_clientlib_csdp_CSDP.o : edu_clarkson_cs_clientlib_csdp_CSDP.cpp
	$(GCC) -fPIC -c -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/linux" -I"$(CSDP_HOME)/include" -o $@ edu_clarkson_cs_clientlib_csdp_CSDP.cpp

clean:
	rm -rf *.o
	rm -rf $(OUTPUT) 

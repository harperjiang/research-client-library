OS := $(shell uname)

ifeq ($(OS),Darwin)
	OUTPUT=libjcsdp.dylib
else
	OUTPUT=libjcsdp.so
endif

all:
	cd c; make all; 
	cp c/src/$(OUTPUT) build; cp build/$(OUTPUT) lib
clean:
	cd c; make clean; 
	rm build/$(OUTPUT); rm lib/$(OUTPUT)
genheader:
	cd java/src; javah edu.clarkson.cs.clientlib.csdp.CSDP; mv *.h ../../c 
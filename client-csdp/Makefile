all:
	cd c; make all; 
	cp c/src/*.so build; cp build/*.so lib
clean:
	cd c; make clean; 
	rm build/*.so; rm lib/*.so
genheader:
	cd java/src; javah edu.clarkson.cs.clientlib.csdp.CSDP; mv *.h ../../c 
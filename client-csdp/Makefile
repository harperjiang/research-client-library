all:
	cd c; make all
	mv c/src/*.so build/
clean:
	cd c; make clean
genheader:
	cd java/src; javah edu.clarkson.cs.clientlib.csdp.CSDP; mv *.h ../../c 
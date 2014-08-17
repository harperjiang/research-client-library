package edu.clarkson.cs.clientlib.svm.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.clarkson.cs.clientlib.lang.proc.OutputHandler;
import edu.clarkson.cs.clientlib.lang.proc.ProcessRunner;
import edu.clarkson.cs.clientlib.svm.DataSet;
import edu.clarkson.cs.clientlib.svm.FileDataSet;
import edu.clarkson.cs.clientlib.svm.FileModel;
import edu.clarkson.cs.clientlib.svm.Model;
import edu.clarkson.cs.clientlib.svm.Trainer;

public class LibSVMTrainer implements Trainer {

	private static final String SVM_TRAIN = "svm-train";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Model train(DataSet input) {
		if (!(input instanceof FileDataSet)) {
			throw new IllegalArgumentException();
		}
		FileDataSet trainset = (FileDataSet) input;
		ProcessRunner runner = new ProcessRunner(
				Configuration.ROOT_FOLDER.getAbsoluteFile() + File.separator
						+ SVM_TRAIN, trainset.getFile().getAbsolutePath(),
				trainset.getFile().getAbsolutePath() + ".model");
		runner.setHandler(new OutputHandler() {
			@Override
			public void output(String input) {
				// TODO If there's error message,throw an exception

			}
		});
		try {
			runner.runAndWait();
		} catch (Exception e) {
			logger.error("Exception when executing external trainer", e);
			throw new RuntimeException(e);
		}
		return new FileModel(new File(trainset.getFile().getAbsolutePath()
				+ ".model"));
	}

}

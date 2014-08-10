package edu.clarkson.cs.clientlib.ripeatlas.model;

import java.math.BigDecimal;

public class PingOutput extends Output {

	private BigDecimal rtt;

	public PingOutput() {
		super();
	}

	public BigDecimal getRtt() {
		return rtt;
	}

	public void setRtt(BigDecimal rtt) {
		this.rtt = rtt;
	}

}

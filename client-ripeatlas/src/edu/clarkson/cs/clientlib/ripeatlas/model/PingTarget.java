package edu.clarkson.cs.clientlib.ripeatlas.model;

public class PingTarget extends MeasurementTarget {

	public PingTarget() {
		super();
		setType("ping");
	}

	private String target;

	private int interval;

	private int packets = 3;

	private int sizes = 48;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getPackets() {
		return packets;
	}

	public void setPackets(int packets) {
		this.packets = packets;
	}

	public int getSizes() {
		return sizes;
	}

	public void setSizes(int sizes) {
		this.sizes = sizes;
	}

}

package edu.clarkson.cs.clientlib.ipinfo.dao;

import java.util.List;

import edu.clarkson.cs.clientlib.ipinfo.model.RingNode;

public interface RingNodeDao {
	
	public List<RingNode> allatonce();

	public List<RingNode> incountry(String country);
}

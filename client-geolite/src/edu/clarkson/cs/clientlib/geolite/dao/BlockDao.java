package edu.clarkson.cs.clientlib.geolite.dao;

import edu.clarkson.cs.clientlib.geolite.model.Block;
import edu.clarkson.cs.persistence.EntityDao;

public interface BlockDao extends EntityDao<Block> {

	Block getBlock(long ip);
}

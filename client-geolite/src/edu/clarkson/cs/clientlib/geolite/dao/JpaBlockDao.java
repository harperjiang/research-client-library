package edu.clarkson.cs.clientlib.geolite.dao;

import javax.persistence.NoResultException;

import edu.clarkson.cs.clientlib.geolite.model.Block;
import edu.clarkson.cs.persistence.JpaEntityDao;

public class JpaBlockDao extends JpaEntityDao<Block> implements BlockDao {

	@Override
	public Block getBlock(long ip) {
		try {
			return getEntityManager()
					.createQuery(
							"select b from Block b where b.fromIp <= :ip and b.toIp >= :ip",
							Block.class).setParameter("ip", ip)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}

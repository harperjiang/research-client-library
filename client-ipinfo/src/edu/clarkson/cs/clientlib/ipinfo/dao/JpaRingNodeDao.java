package edu.clarkson.cs.clientlib.ipinfo.dao;

import java.util.List;

import edu.clarkson.cs.clientlib.ipinfo.model.RingNode;
import edu.clarkson.cs.persistence.JpaDao;

public class JpaRingNodeDao extends JpaDao<RingNode> implements RingNodeDao {

	public List<RingNode> incountry(String country) {
		return getEntityManager()
				.createQuery(
						"select rn from RingNode rn where rn.country = :country",
						RingNode.class).setParameter("country", country)
				.getResultList();
	}
}

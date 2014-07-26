package edu.clarkson.cs.clientlib.ipinfo.api;

import java.util.List;

import edu.clarkson.cs.clientlib.ipinfo.model.RingNode;
import edu.clarkson.cs.persistence.JpaDao;

public class RingNodeDao extends JpaDao {

	public List<RingNode> allatonce() {
		return getEntityManager().createQuery("select rn from RingNode rn",
				RingNode.class).getResultList();
	}

	public List<RingNode> incountry(String country) {
		return getEntityManager()
				.createQuery(
						"select rn from RingNode rn where rn.country = :country",
						RingNode.class).setParameter("country", country)
				.getResultList();
	}
}

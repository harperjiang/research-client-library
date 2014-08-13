package edu.clarkson.cs.persistence.dv;

import java.util.Date;

public interface DataVersionDao {

	public Date get(String key);

	public void update(String key, Date newdate);
}

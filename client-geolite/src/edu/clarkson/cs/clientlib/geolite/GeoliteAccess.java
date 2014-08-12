package edu.clarkson.cs.clientlib.geolite;

import edu.clarkson.cs.clientlib.geolite.dao.BlockDao;
import edu.clarkson.cs.clientlib.geolite.dao.LocationDao;
import edu.clarkson.cs.clientlib.geolite.model.Block;
import edu.clarkson.cs.clientlib.geolite.model.Location;

public class GeoliteAccess {

	private BlockDao blockDao;

	private LocationDao locationDao;

	public BlockDao getBlockDao() {
		return blockDao;
	}

	public void setBlockDao(BlockDao blockDao) {
		this.blockDao = blockDao;
	}

	public LocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public Location getLocation(String ip) {
		long ipint = ipToInteger(ip);
		Block block = getBlockDao().getBlock(ipint);
		if (null == block)
			return null;
		return block.getLocation();
	}

	public static final long ipToInteger(String ip) {
		String[] split = ip.split("\\.");
		long sum = 0;
		for (int i = 0; i < split.length; i++) {
			sum += (1 << ((3 - i) * 8)) * Integer.valueOf(split[i]);
		}
		return sum;
	}
}

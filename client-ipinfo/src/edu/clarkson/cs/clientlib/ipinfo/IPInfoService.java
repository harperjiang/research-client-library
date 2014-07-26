package edu.clarkson.cs.clientlib.ipinfo;

import java.nio.charset.Charset;
import java.util.List;

import org.eclipse.persistence.queries.ScrollableCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import edu.clarkson.cs.clientlib.ipinfo.api.QueryIPInfoRequest;
import edu.clarkson.cs.clientlib.ipinfo.dao.IPInfoDao;
import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;
import edu.clarkson.cs.clientlib.ipinfo.util.CapChecker;

public class IPInfoService {

	private BloomFilter<CharSequence> filter;

	private IPInfoDao ipInfoDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private CapChecker capChecker;

	private Environment env;

	public IPInfoService() {
		super();
	}

	public void afterPropertySet() {
		// Initalize Bloom Filter
		filter = BloomFilter.create(
				Funnels.stringFunnel(Charset.forName("utf8")), 100000, 0.001);
		// Fill in bloom filter
		ScrollableCursor cursor = ipInfoDao.all();

		int size = cursor.size();
		int count = 0;
		while (count < size) {
			int page = Math.min(1000, size - count);
			List<Object> infos = cursor.next(page);
			for (Object i : infos) {
				IPInfo info = (IPInfo) i;
				filter.put(info.getIp());
			}
			count += page;
		}

		capChecker = new CapChecker();
		capChecker.addDailyCap(REQ_DAILY);
		capChecker.addMinuteCap(REQ_PER_MIN);
	}

	public QueryIPInfoRequest queryip(String ip) {
		QueryIPInfoRequest request = new QueryIPInfoRequest();
		request.setHttpClient(env.getHttpClient());
		request.setGson(env.getGson());
		request.setParser(env.getReader());
		request.setIp(ip);
		return request;
	}

	public IPInfo getInfo(String ip) {
		if (filter.mightContain(ip)) {
			IPInfo info = ipInfoDao.find(ip);
			if (info != null)
				return info;
		}

		try {
			// Query remote info, check time constraint
			while (!capChecker.couldRequest()) {
				try {
					Thread.sleep(capChecker.available());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			IPInfo info = queryip(ip).execute().getResult();
			capChecker.requested();
			if (info != null) {
				filter.put(info.getIp());
				ipInfoDao.save(info);
			}
			return info;
		} catch (Exception e) {
			logger.error("Exception when requesting ip info", e);
			return null;
		}
	}

	public IPInfoDao getIpInfoDao() {
		return ipInfoDao;
	}

	public void setIpInfoDao(IPInfoDao ipInfoDao) {
		this.ipInfoDao = ipInfoDao;
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	private static final int REQ_PER_MIN = 300;

	private static final int REQ_DAILY = 100000;
}

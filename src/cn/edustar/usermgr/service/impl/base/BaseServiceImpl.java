package cn.edustar.usermgr.service.impl.base;

import java.util.concurrent.ConcurrentHashMap;

import cn.edustar.usermgr.dao.ConfigDao;
import cn.edustar.usermgr.dao.UserDao;
import cn.edustar.usermgr.pojos.Ticket;

/**
 * Service
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-10-12 13:05:30
 */
public class BaseServiceImpl {
	protected static final String SUCCESS = "success";
	protected static final String ERROR = "error";
	protected static final String NULL = "null";
	protected ConcurrentHashMap<String, Ticket> HASHMAP_TICKET = new ConcurrentHashMap<String, Ticket>();
	protected UserDao userDao;
	protected ConfigDao configDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setConfigDao(ConfigDao configDao) {
		this.configDao = configDao;
	}

}

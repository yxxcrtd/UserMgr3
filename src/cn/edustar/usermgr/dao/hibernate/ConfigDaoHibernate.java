package cn.edustar.usermgr.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import cn.edustar.usermgr.dao.ConfigDao;
import cn.edustar.usermgr.pojos.Config;

@SuppressWarnings("unchecked")
public class ConfigDaoHibernate extends HibernateDaoSupport implements ConfigDao {
	private static final String LOAD_UPDATE_VALUE_BY_KEY = "UPDATE Config SET value = ? WHERE key = ?";

	public Config getConfigByKey(String key) {
		List<Config> list = this.getHibernateTemplate().find("FROM Config WHERE key = ?", key);
		return (null != list && list.size() > 0) ? (Config) list.get(0) : null;
	}
	
	public void updateValueByKey(String key, String value) {
		getHibernateTemplate().bulkUpdate(LOAD_UPDATE_VALUE_BY_KEY, new String[] { value, key });
	}

}

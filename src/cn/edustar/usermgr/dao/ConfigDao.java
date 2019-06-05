package cn.edustar.usermgr.dao;

import cn.edustar.usermgr.pojos.Config;

public interface ConfigDao {
	public Config getConfigByKey(String key);
	public void updateValueByKey(String key, String value);
}

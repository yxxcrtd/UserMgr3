package cn.edustar.usermgr.service;

import cn.edustar.usermgr.pojos.Config;

public interface ConfigService {
	public Config getConfigByKey(String key);
	public void updateValueByKey(String key, String value);
}

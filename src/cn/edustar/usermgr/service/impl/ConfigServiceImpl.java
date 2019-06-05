package cn.edustar.usermgr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.usermgr.pojos.Config;
import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.impl.base.BaseServiceImpl;

public class ConfigServiceImpl extends BaseServiceImpl implements ConfigService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	public Config getConfigByKey(String key) {
		Config cnfg=configDao.getConfigByKey(key);
		logger.debug("配置["+key+"]="+cnfg.getValue());
		return cnfg;
	}

	public void updateValueByKey(String key, String value) {
		configDao.updateValueByKey(key, value);
	}

}

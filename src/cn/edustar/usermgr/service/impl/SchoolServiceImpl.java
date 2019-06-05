package cn.edustar.usermgr.service.impl;

import java.util.List;

import cn.edustar.usermgr.dao.SchoolDao;
import cn.edustar.usermgr.param.query.SchoolQueryParam;
import cn.edustar.usermgr.pojos.School;
import cn.edustar.usermgr.service.SchoolService;
import cn.edustar.usermgr.util.Pager;

public class SchoolServiceImpl implements SchoolService {

	protected SchoolDao schoolDao;

	public SchoolDao getSchoolDao() {
		return this.schoolDao;
	}

	public void setSchoolDao(SchoolDao schoolDao) {
		this.schoolDao = schoolDao;
	}

	public School getSchool(int id) {
		return this.schoolDao.getSchool(id);
	}

	public School getSchool(String schoolGuid) {
		return this.schoolDao.getSchool(schoolGuid);
	}

	public School getSchoolByName(String schoolName) {
		return this.schoolDao.getSchoolByName(schoolName);
	}

	public List<School> getSchools() {
		return this.schoolDao.getSchools();
	}

	public List<School> getSchoolList(SchoolQueryParam param, Pager pager) {
		return this.schoolDao.getSchoolList(param, pager);
	}

	public void saveOrUpdate(School school) {
		this.schoolDao.saveOrUpdate(school);
	}

	public void delschool(String schoolGuid) {
		this.schoolDao.delschool(schoolGuid);
	}
	
}

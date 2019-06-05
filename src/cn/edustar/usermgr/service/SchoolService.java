package cn.edustar.usermgr.service;
import cn.edustar.usermgr.param.query.SchoolQueryParam;
import cn.edustar.usermgr.pojos.School;
import cn.edustar.usermgr.util.Pager;

import java.util.List;
public interface SchoolService {
	public School getSchool(String schoolGuid);
	public School getSchool(int id);
	public School getSchoolByName(String schoolName);
	public List<School> getSchools();
	public List<School> getSchoolList(SchoolQueryParam param, Pager pager);
	public void saveOrUpdate(School school);
	public void delschool(String schoolGuid);
}


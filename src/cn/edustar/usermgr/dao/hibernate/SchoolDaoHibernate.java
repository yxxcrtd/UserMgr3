package cn.edustar.usermgr.dao.hibernate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import cn.edustar.usermgr.dao.SchoolDao;
import cn.edustar.usermgr.param.query.SchoolQueryParam;
import cn.edustar.usermgr.pojos.School;
import cn.edustar.usermgr.util.Pager;
import cn.edustar.usermgr.util.QueryHelper;

import java.util.List;

@SuppressWarnings("unchecked")
public class SchoolDaoHibernate extends HibernateDaoSupport implements SchoolDao{
	
	public School getSchool(String schoolGuid){
		List<School> list = this.getHibernateTemplate().find("FROM School WHERE unitGuid = ?", schoolGuid);
		return (null != list && list.size() > 0) ? (School) list.get(0) : null;			
	}
	public School getSchool(int id){
		List<School> list = this.getHibernateTemplate().find("FROM School WHERE id = ?", id);
		return (null != list && list.size() > 0) ? (School) list.get(0) : null;			
	}	
	public School getSchoolByName(String schoolName){
		List<School> list = this.getHibernateTemplate().find("FROM School WHERE schoolName = ?", schoolName);
		return (null != list && list.size() > 0) ? (School) list.get(0) : null;			
	}
	public List<School> getSchools(){
		List<School> list = this.getHibernateTemplate().find("FROM School");
		return list;		
	}
	public List<School> getSchoolList(SchoolQueryParam param, Pager pager){
		QueryHelper query; 
		query=param.getQuery();
		if(query==null){
			query= param.createQuery();
		}
		if (pager == null)
			return query.queryData(getHibernateTemplate(), -1, param.count);
		else
			return query.queryDataAndTotalCount(getHibernateTemplate(), pager);		
	}
	public void saveOrUpdate(School school){
		this.getHibernateTemplate().saveOrUpdate(school);
	}
	public void delschool(String schoolGuid){
		this.getHibernateTemplate().bulkUpdate("DELETE FROM School WHERE unitGuid = ?", schoolGuid);
	}
}

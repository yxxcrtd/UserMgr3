package cn.edustar.usermgr.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.edustar.usermgr.util.Pager;
import cn.edustar.usermgr.param.query.UserQueryParam;
import cn.edustar.usermgr.dao.UserDao;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.pojos.UserToken;
import cn.edustar.usermgr.util.QueryHelper;;

/**
 * 用户DAO的实现
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-01 11:03:22
 */
@SuppressWarnings("unchecked")
public class UserDaoHibernate extends HibernateDaoSupport implements UserDao {
	
	/**
	 * 根据用户名修改密码
	 */
	private static final String LOAD_UPDATE_PASSWORD_BY_USERNAME = "UPDATE User SET password = ? WHERE username = ?";
	
	/**
	 * 根据用户名重置问题和答案
	 */
	private static final String LOAD_RESET_QUESTION_AND_ANSWER_BY_USERNAME = "UPDATE User SET question = ?, answer = ? WHERE username = ?";
	
	/**
	 * 根据用户名修改用户状态
	 */
	private static final String LOAD_UPDATE_STATUS_BY_USERNAME = "UPDATE User SET status = ? WHERE username = ?";
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.dao.UserDao#getUserByQueryString(java.lang.String, java.lang.String)
	 */
	public User getUserByQueryString(String queryString, String value) {
		List<User> list = this.getHibernateTemplate().find("FROM User WHERE (" + queryString + " = ?)", value);
		return (null != list && list.size() > 0) ? (User) list.get(0) : null;
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.dao.UserDao#getUserCounts()
	 */
	public int getUserCounts() {
		List<User> list = this.getHibernateTemplate().find("FROM User");
		return list.size();
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.dao.UserDao#saveOrUpdate(cn.edustar.usermgr.pojos.User)
	 */
	public void saveOrUpdate(User user) {
		this.getHibernateTemplate().saveOrUpdate(user);
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.dao.UserDao#updateUserInfoByUsername(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public void updateUserInfoByUsername(String username, String trueName, String email, int role) {
		this.getHibernateTemplate().bulkUpdate("UPDATE User SET trueName = '" + trueName + "', email = '" + email + "', role = '" + role + "' WHERE username = '" + username + "'");
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.dao.UserDao#updatePasswordByUsername(java.lang.String, java.lang.String)
	 */
	public void updatePasswordByUsername(String username, String password) {
		this.getHibernateTemplate().bulkUpdate(LOAD_UPDATE_PASSWORD_BY_USERNAME, new String[] { password, username });
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.dao.UserDao#resetQuestionAndAnswerByUsername(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void resetQuestionAndAnswerByUsername(String username, String question, String answer) {
		this.getHibernateTemplate().bulkUpdate(LOAD_RESET_QUESTION_AND_ANSWER_BY_USERNAME, new String[] { question, answer, username });
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.dao.UserDao#updateStatusByUsername(java.lang.String, int)
	 */
	public void updateStatusByUsername(String username, int status) {
		this.getHibernateTemplate().bulkUpdate(LOAD_UPDATE_STATUS_BY_USERNAME, new Object[] { status, username });
	}
	
	public User getUserByLoginname(String loginName){
		List<User> list = this.getHibernateTemplate().find("FROM User WHERE username = ?", loginName);
		return (null != list && list.size() > 0) ? (User) list.get(0) : null;		
	}
	public User getUserById(int id){
		List<User> list = this.getHibernateTemplate().find("FROM User WHERE id = ?", id);
		return (null != list && list.size() > 0) ? (User) list.get(0) : null;		
	}
	public User getUserByEmail(String email){
		List<User> list = this.getHibernateTemplate().find("FROM User WHERE email = ?", email);
		return (null != list && list.size() > 0) ? (User) list.get(0) : null;		
	}
	
	public User getUserByLoginnameOrEmail(String loginNameOrEmail){
		List<User> list = this.getHibernateTemplate().find("FROM User WHERE username = ? Or email = ?", loginNameOrEmail,loginNameOrEmail);
		return (null != list && list.size() > 0) ? (User) list.get(0) : null;
	}
	
	public boolean checkUserAnswer(String loginName,String answer){
		List<User> list = this.getHibernateTemplate().find("FROM User WHERE username = ? And answer = ?", loginName,answer);
		return (null != list && list.size() > 0) ? true: false;
	}
	
	public boolean checkUserAnswerByLoginnameOrEmail(String loginNameOrEmail,String answer){
		List<User> list = this.getHibernateTemplate().find("FROM User WHERE (username = ? Or email = ?) And answer = ?", loginNameOrEmail,loginNameOrEmail,answer);
		return (null != list && list.size() > 0) ? true: false;
	}
	public boolean SavePassword(String loginName,String password){
		User user=getUserByLoginname(loginName);
		if(user==null){
			return false;
		}
		user.setPassword(password);
		saveOrUpdate(user);
		return true;
	}
	public boolean SavePasswordByLoginnameOrEmail(String loginNameOrEmail,String password){
		User user=getUserByLoginnameOrEmail(loginNameOrEmail);
		if(user==null){
			return false;
		}
		user.setPassword(password);
		saveOrUpdate(user);
		return true;
	}
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.dao.UserDao#deleteUser(cn.edustar.usermgr.pojos.User)
	 */
	public void deleteUser(User user) {
		this.getHibernateTemplate().delete(user);
	}

	public void saveOrUpdateUserToken(UserToken userToken)
	{
		this.getHibernateTemplate().saveOrUpdate(userToken);
	}
	public UserToken getUserTokenByLoginName(String loginName)
	{
		List<UserToken> list = this.getHibernateTemplate().find("FROM UserToken WHERE loginName = ?", loginName);
		return (null != list && list.size() > 0) ? (UserToken) list.get(0) : null;
	}
	
	public UserToken getUserTokenByToken(String token)
	{
		List<UserToken> list = this.getHibernateTemplate().find("FROM UserToken WHERE token = ?", token);
		return (null != list && list.size() > 0) ? (UserToken) list.get(0) : null;
	}
	
	public void deleteUserToken(UserToken userToken)
	{
		this.getHibernateTemplate().delete(userToken);
	}
	public void deleteUserTokenByLoginName(String loginName)
	{
		this.getHibernateTemplate().bulkUpdate("DELETE FROM UserToken WHERE loginName = ?", loginName);		
	}
	public void deleteUserTokenByToken(String userToken)
	{
		this.getHibernateTemplate().bulkUpdate("DELETE FROM UserToken WHERE token = ?", userToken);		
	}
	
	public void deleteUnValidToken()
	{
		this.getHibernateTemplate().bulkUpdate("DELETE FROM UserToken WHERE DateDiff(day,CreateDate,getDate()) > 2");
	}
	public List<User> getUserList(String unitGuid){
		List<User> list = this.getHibernateTemplate().find("FROM User WHERE unitGuid = ?", unitGuid);
		return list;		
		
	}
	public List<User> getUserList(UserQueryParam param, Pager pager) {
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
	
	public void updateUserSchoolGuid(String oldSchoolGuid, String newSchoolGuid){
		this.getHibernateTemplate().bulkUpdate("UPDATE User SET unitGuid=? WHERE unitGuid = ?", newSchoolGuid,oldSchoolGuid);		
	}
}

package cn.edustar.usermgr.service.impl;

import java.util.Date;
import java.util.List;

import cn.edustar.usermgr.util.Pager;
import cn.edustar.usermgr.param.query.UserQueryParam;
import cn.edustar.usermgr.pojos.Ticket;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.pojos.UserToken;
import cn.edustar.usermgr.service.UserService;
import cn.edustar.usermgr.service.impl.base.BaseServiceImpl;
import cn.edustar.usermgr.util.MD5;

/**
 * UserService
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-02 11:03:12
 */
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#getUserByQueryString(java.lang.String)
	 */
	
	public User getUserByQueryString(String queryString) {
		if (queryString.contains("@")) { /* 用户名里面不能使用@字符？？？？？ */
			return userDao.getUserByQueryString("email", queryString);
		} else {
			return userDao.getUserByQueryString("username", queryString);
		}
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#getUserCounts()
	 */
	public int getUserCounts() {
		return userDao.getUserCounts();
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#createUserTicket(java.lang.String)
	 */
	public Ticket createUserTicket(String username) {
		Ticket ticket = new Ticket(username);
		HASHMAP_TICKET.put(ticket.getTicket(), ticket);
		return ticket;
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#saveOrUpdate(cn.edustar.usermgr.pojos.User)
	 */
	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#getUserByUserTicket(java.lang.String)
	 */
	public User getUserByUserTicket(String userTicket) {
		if (null == userTicket || "".equals(userTicket)
				|| userTicket.length() == 0) {
			return null;
		}
		Ticket ticket = getTicketByUserTicket(userTicket);
		if (null == ticket) {
			return null;
		}
		if (null == ticket.getUsername() || "".equals(ticket.getUsername())
				|| ticket.getUsername().length() < 0) {
			return null;
		}
		ticket.setLastAccessed(new Date());
		return getUserByQueryString(ticket.getUsername());
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#getTicketByUserTicket(java.lang.String)
	 */
	public Ticket getTicketByUserTicket(String userTicket) {
		Ticket ticket = HASHMAP_TICKET.get(userTicket);
		if (null != ticket) {
			if (null == ticket.getLastAccessed()) {
				return null;
			}
		}
		return ticket;
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#verifyUser(java.lang.String)
	 */
	public String verifyUser(String username) {
		User user = getUserByQueryString(username);
		if (null != user) {
			if (null == user.getQuestion() || "".equals(user.getQuestion())) {
				return NULL;
			}
			return user.getQuestion();
		} else {
			return ERROR;
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#verifyAnswer(java.lang.String, java.lang.String)
	 */
	public String verifyAnswer(String username, String answer) {
		User user = getUserByQueryString(username);
		if (null != user) {
			if (user.getAnswer().equals(MD5.toMD5(answer))) {
				return SUCCESS;
			} else {
				return ERROR;
			}
		} else {
			return "";
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#resetPassword(java.lang.String, java.lang.String)
	 */
	public String resetPassword(String username, String answer, String password) {
		User user = getUserByQueryString(username);
		if (null != user && null != answer) {
			if (user.getAnswer().equals(MD5.toMD5(answer))) {
				user.setPassword(MD5.toMD5(password));
				userDao.saveOrUpdate(user);
				return SUCCESS;
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#updateUserInfoByUsername(java.lang.String, int)
	 */
	public void updateUserInfoByUsername(String username, String trueName, String email, int role) {
		userDao.updateUserInfoByUsername(username, trueName, email, role);
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#updatePasswordByUsername(java.lang.String, java.lang.String)
	 */
	public void updatePasswordByUsername(String username, String password) {
		userDao.updatePasswordByUsername(username, MD5.toMD5(password));
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#resetQuestionAndAnswerByUsername(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void resetQuestionAndAnswerByUsername(String username, String question, String answer) {
		userDao.resetQuestionAndAnswerByUsername(username, question, MD5.toMD5(answer));
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#updateStatusByUsername(java.lang.String, int)
	 */
	public void updateStatusByUsername(String username, int status) {
		userDao.updateStatusByUsername(username, status);
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.usermgr.service.UserService#deleteUser(java.lang.String)
	 */
	public void deleteUser(String username) {
		if (!"".equals(username) || null != username) {
			userDao.deleteUser(getUserByQueryString(username));
		}
	}
	
	public void saveOrUpdateUserToken(UserToken userToken)
	{
		this.userDao.saveOrUpdateUserToken(userToken);
	}
	public UserToken getUserTokenByLoginName(String loginName)
	{
		return this.userDao.getUserTokenByLoginName(loginName);
	}
	public UserToken getUserTokenByToken(String token)
	{
		return this.userDao.getUserTokenByToken(token);
	}
	public void deleteUserToken(UserToken userToken)
	{
		this.userDao.deleteUserToken(userToken);
	}
	public void deleteUserTokenByLoginName(String loginName)
	{
		this.userDao.deleteUserTokenByLoginName(loginName);
	}
	public void deleteUserTokenByToken(String token)
	{
		this.userDao.deleteUserTokenByToken(token);
	}
	public void deleteUnValidToken()
	{
		this.userDao.deleteUnValidToken();
	}
	
	public User getUserByLoginname(String loginName){
		return this.userDao.getUserByLoginname(loginName);
	}
	
	public User getUserByLoginnameOrEmail(String loginNameOrEmail){
		return this.userDao.getUserByLoginnameOrEmail(loginNameOrEmail);
	}
	
	public User getUserById(int id){
		return this.userDao.getUserById(id);
	}
	
	public User getUserByEmail(String email){
		return this.userDao.getUserByEmail(email);
	}
	
	public boolean checkUserAnswer(String loginName,String answer){
		return this.userDao.checkUserAnswer(loginName, answer);
	}
	public boolean checkUserAnswerByLoginnameOrEmail(String loginNameOrEmail,String answer){
		return this.userDao.checkUserAnswerByLoginnameOrEmail(loginNameOrEmail, answer);
	}
	public boolean SavePassword(String loginName,String password){
		return this.userDao.SavePassword(loginName, password);
	}
	public boolean SavePasswordByLoginnameOrEmail(String loginNameOrEmail,String password){
		return this.userDao.SavePasswordByLoginnameOrEmail(loginNameOrEmail, password);
	}
	
	public List<User> getUserList(UserQueryParam param, Pager pager){
		return userDao.getUserList(param, pager);
	}
	
	public List<User> getUserList(String unitGuid){
		return userDao.getUserList(unitGuid);
	}
	
	public void updateUserSchoolGuid(String oldSchoolGuid, String newSchoolGuid){
		this.userDao.updateUserSchoolGuid(oldSchoolGuid,newSchoolGuid);
	}
}

package cn.edustar.usermgr.service;

import java.util.List;

import cn.edustar.usermgr.util.Pager;
import cn.edustar.usermgr.param.query.UserQueryParam;
import cn.edustar.usermgr.pojos.Ticket;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.pojos.UserToken;

/**
 * 用户服务
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-01 11:00:28
 */
public interface UserService {
	
	public User getUserByQueryString(String queryString);

	public Ticket createUserTicket(String username);

	public void saveOrUpdate(User user);

	public User getUserByUserTicket(String userTicket);

	public Ticket getTicketByUserTicket(String userTicket);

	public String verifyUser(String username);

	public String verifyAnswer(String username, String answer);

	public String resetPassword(String username, String answer, String password);

	public void updateStatusByUsername(String username, int status);

	public void updateUserInfoByUsername(String username, String trueName, String email, int role);

	public void updatePasswordByUsername(String username, String password);
	
	public void updateUserSchoolGuid(String oldSchoolGuid, String newSchoolGuid);
	
	public void resetQuestionAndAnswerByUsername(String username, String question, String answer);
	
	public void deleteUser(String username);
	
	public int getUserCounts();
	
	public void saveOrUpdateUserToken(UserToken userToken);
	public UserToken getUserTokenByLoginName(String loginName);
	public UserToken getUserTokenByToken(String token);
	public void deleteUserToken(UserToken userToken);
	public void deleteUserTokenByLoginName(String loginName);
	public void deleteUserTokenByToken(String token);
	public void deleteUnValidToken();
	
	public User getUserByLoginname(String loginName);
	public User getUserByLoginnameOrEmail(String loginNameOrEmail);	
	public User getUserById(int id);
	public User getUserByEmail(String email);
	public boolean checkUserAnswer(String loginName,String answer);
	public boolean checkUserAnswerByLoginnameOrEmail(String loginNameOrEmail,String answer);
	public boolean SavePassword(String loginName,String password);
	public boolean SavePasswordByLoginnameOrEmail(String loginNameOrEmail,String password);	
	
	/**
	 * 得到用户数据表
	 * 
	 * @param param 查询参数
	 * @param pager 分页条件
	 * @return
	 */
	public List<User> getUserList(UserQueryParam param, Pager pager);
	public List<User> getUserList(String unitGuid);
	
}

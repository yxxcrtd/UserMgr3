package cn.edustar.usermgr.dao;

import java.util.List;

import cn.edustar.usermgr.util.Pager;
import cn.edustar.usermgr.param.query.UserQueryParam;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.pojos.UserToken;

/**
 * 用户DAO
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-01 11:03:02
 */
public interface UserDao {
	
	public void saveOrUpdate(User user);

	public User getUserByQueryString(String queryString, String value);
	
	public void updateStatusByUsername(String username, int status);
	
	public void updatePasswordByUsername(String username, String password);
	
	public void resetQuestionAndAnswerByUsername(String username, String question, String answer);
	
	public void updateUserInfoByUsername(String username, String trueName, String email, int role);
	
	public void deleteUser(User user);
	
	public int getUserCounts();
	
	public User getUserByLoginname(String loginName);
	public User getUserById(int id);
	public User getUserByEmail(String email);
	public User getUserByLoginnameOrEmail(String loginNameOrEmail);
	
	public boolean checkUserAnswer(String loginName,String answer);
	public boolean checkUserAnswerByLoginnameOrEmail(String loginNameOrEmail,String answer);
	public boolean SavePassword(String loginName,String password);
	public boolean SavePasswordByLoginnameOrEmail(String loginNameOrEmail,String password);
	
	public void saveOrUpdateUserToken(UserToken userToken);
	public UserToken getUserTokenByLoginName(String loginName);
	public UserToken getUserTokenByToken(String token);
	public void deleteUserToken(UserToken userToken);
	public void deleteUserTokenByLoginName(String loginName);
	public void deleteUserTokenByToken(String token);
	public void deleteUnValidToken();
	/**
	 * 根据指定条件得到用户列表
	 * 
	 * @param param 条件参数
	 * @param pager 分页参数
	 * @return 返回 List&lt;User&gt; 集合
	 */
	public List<User> getUserList(UserQueryParam param, Pager pager);
	public List<User> getUserList(String unitGuid);
	public void updateUserSchoolGuid(String oldSchoolGuid, String newSchoolGuid);
}

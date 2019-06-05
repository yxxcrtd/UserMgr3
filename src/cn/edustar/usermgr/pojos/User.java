package cn.edustar.usermgr.pojos;

import java.io.Serializable;

import cn.edustar.usermgr.BaseUser;

/**
 * 用户
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-09 21:37:01
 */
public class User extends BaseUser implements Serializable {
	
	private static final long serialVersionUID = -6056246015995021236L;
	
	public static final String SESSION_LOGIN_USERID_KEY = "jitar.login.userId";
	
	public static final String SESSION_LOGIN_NAME_KEY = "jitar.login.username";
	
}

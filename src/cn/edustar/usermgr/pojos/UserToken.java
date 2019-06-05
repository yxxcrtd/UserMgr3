package cn.edustar.usermgr.pojos;

import java.io.Serializable;
import java.util.Date;

public class UserToken implements Serializable {

	/**
	 * 记录登录票证，以便其他平台调用
	 */
	private static final long serialVersionUID = -3009194973762336787L;
	private int tokenId;
	private String loginName;
	private String userGuid;
	private String token;
	private Date createDate;
	
	public UserToken(){}

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}

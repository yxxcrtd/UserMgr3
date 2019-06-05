package cn.edustar.usermgr.pojos;

import java.util.Date;
import java.util.UUID;

/**
 * 票证
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-09 22:29:50
 */
public class Ticket {
	private final String ticket = UUID.randomUUID().toString().toLowerCase();
	private String username;
	private Date createDate = new Date();
	private Date lastAccessed = new Date();
	private String ip;

	public Ticket(String username) {
		this.username = username;
	}

	public String getTicket() {
		return this.ticket;
	}

	public String getUsername() {
		return this.username;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public String getIp() {
		return this.ip;
	}

}

package cn.edustar.usermgr.pojos;

import java.io.Serializable;

/**
 * @author Yang XinXin
 * @version 2.0.0, 2011-05-19 11:19:30
 */
public class Config implements Serializable {
	private static final long serialVersionUID = -3257372388229715961L;
	private int id;
	private String key;
	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

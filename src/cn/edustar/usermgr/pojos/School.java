package cn.edustar.usermgr.pojos;

import java.io.Serializable;

public class School  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7461653320839424184L;

	private int id;
	private String unitGuid;
	private String schoolName;
	
	public School(){}
	
	public School(String unitGuid,String schoolName){
		this.id=0;
		this.unitGuid=unitGuid;
		this.schoolName=schoolName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUnitGuid() {
		return unitGuid;
	}

	public void setUnitGuid(String unitGuid) {
		this.unitGuid = unitGuid;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}	
}

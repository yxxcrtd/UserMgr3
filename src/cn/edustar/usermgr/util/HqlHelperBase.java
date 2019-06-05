package cn.edustar.usermgr.util;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Query;

/**
 * QueryHelper的基类
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-02 10:22:19
 */
public abstract class HqlHelperBase {

	private ArrayList<QueryParam> query_param = new ArrayList<QueryParam>();

	public void setString(String paramName, String paramValue) {
		QueryParam p = new QueryParam(paramName, "setString", paramValue);
		query_param.add(p);
	}

	public void setBoolean(String paramName, boolean paramValue) {
		QueryParam p = new QueryParam(paramName, "setBoolean", paramValue);
		query_param.add(p);
	}

	public void setInteger(String paramName, int paramValue) {
		QueryParam p = new QueryParam(paramName, "setInteger", paramValue);
		query_param.add(p);
	}

	public void setDate(String paramName, Date paramValue) {
		QueryParam p = new QueryParam(paramName, "setDate", paramValue);
		query_param.add(p);
	}

	public void setTimestamp(String paramName, Date paramValue) {
		QueryParam p = new QueryParam(paramName, "setTimestamp", paramValue);
		query_param.add(p);
	}

	protected void initQuery(Query query) {
		for (int i = 0; i < query_param.size(); ++i)
			query_param.get(i).setParam(query);
	}

	private static class QueryParam {
		public String paramName;
		public String setterName;
		public Object paramValue;

		@SuppressWarnings("unused")
		public QueryParam() {
		}

		public QueryParam(String paramName, String setterName, Object paramValue) {
			this.paramName = paramName;
			this.setterName = setterName;
			this.paramValue = paramValue;
		}

		public void setParam(Query query) {
			if ("setString".equals(setterName))
				query.setString(paramName, (String) paramValue);
			else if ("setInteger".equals(setterName))
				query.setInteger(paramName, (Integer) paramValue);
			else if ("setDate".equals(setterName))
				query.setDate(paramName, (Date) paramValue);
			else if ("setBoolean".equals(setterName))
				query.setBoolean(paramName, (Boolean) paramValue);
			else if ("setTimestamp".equals(setterName))
				query.setTimestamp(paramName, (Date) paramValue);
			else
				query.setParameter(paramName, paramValue);
		}
	}

}

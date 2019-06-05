package cn.edustar.usermgr.param.query;

import cn.edustar.usermgr.param.base.BaseQueryParam;
import cn.edustar.usermgr.util.QueryHelper;


public class SchoolQueryParam extends BaseQueryParam implements QueryParam {
	
	private QueryHelper query;
	
	public QueryHelper createQuery() {		
		query = new QueryHelper();
		query.fromClause = "FROM School s";
		query.orderClause = "ORDER BY id DESC";
		return query;
	}
	
	public QueryHelper getQuery(){
		return this.query;
	}
	
	public void SetQuery(QueryHelper query){
		this.query=query;
	}
	
}

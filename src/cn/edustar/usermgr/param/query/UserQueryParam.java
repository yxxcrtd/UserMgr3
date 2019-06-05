package cn.edustar.usermgr.param.query;

import cn.edustar.usermgr.param.base.BaseQueryParam;
import cn.edustar.usermgr.util.QueryHelper;

/**
 * 用户查询参数
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-10-12 15:30:19
 */
public class UserQueryParam extends BaseQueryParam implements QueryParam {
	
	private QueryHelper query;
	
	public QueryHelper createQuery() {		
		query = new QueryHelper();
		query.fromClause = "FROM User u";
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

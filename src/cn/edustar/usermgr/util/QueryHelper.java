package cn.edustar.usermgr.util;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-02 10:06:52
 */
@SuppressWarnings("rawtypes")
public class QueryHelper extends HqlHelperBase {
	public String selectClause = "";
	public String fromClause = "";
	public String whereClause = "";
	public String orderClause = "";
	public String groupbyClause = "";
	public String havingClause = "";

	public QueryHelper addAndWhere(String condition) {
		if (this.whereClause.length() == 0)
			this.whereClause = " WHERE (" + condition + ")";
		else
			this.whereClause += " AND (" + condition + ")";
		return this;
	}

	public QueryHelper addOrder(String order) {
		if (this.orderClause.length() == 0)
			this.orderClause = " ORDER BY " + order;
		else
			this.orderClause += ", " + order;
		return this;
	}

	public int queryTotalCount(HibernateTemplate hiber) {
		final String hql = "SELECT COUNT(*) " + this.fromClause + " "
				+ this.whereClause;
		Object result = hiber.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				initQuery(query);
				return query.uniqueResult();
			}
		});
		return safeGetIntResult(result);
	}

	public void queryTotalCount(HibernateTemplate hiber, Pager pager) {
		int tc = queryTotalCount(hiber);
		pager.setTotalRows(tc);
	}

	public List queryData(HibernateTemplate hiber) {
		return queryData(hiber, -1, -1);
	}

	public Object querySingleData(HibernateTemplate hiber) {
		List list = queryData(hiber, 0, 1);
		if (null == list || list.size() == 0)
			return null;
		return list.get(0);
	}

	public int queryIntValue(HibernateTemplate hiber) {
		List list = queryData(hiber, 0, 1);
		if (null == list || list.size() == 0)
			return 0;
		Object v = list.get(0);
		if (null == v)
			return 0;
		if (v instanceof Number)
			return ((Number) v).intValue();
		return 0;
	}

	public List queryData(HibernateTemplate hiber, final int first_result,
			final int max_results) {
		final String hql = this.selectClause + " " + this.fromClause + " "
				+ this.whereClause + " " + this.orderClause + " "
				+ this.groupbyClause + " " + this.havingClause;

		return hiber.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				initQuery(query);
				if (first_result != -1)
					query.setFirstResult(first_result);
				if (max_results != -1)
					query.setMaxResults(max_results);
				return query.list();
			}
		});
	}

	public List queryData(HibernateTemplate hiber, final Pager page_info) {
		return queryData(hiber,
				(page_info.getCurrentPage() - 1) * page_info.getPageSize(),
				page_info.getPageSize());
	}

	public List queryDataAndTotalCount(HibernateTemplate hiber, Pager pager) {
		int tc = this.queryTotalCount(hiber);
		pager.setTotalRows(tc);
		return this.queryData(hiber, pager);
	}

	private static final int safeGetIntResult(Object v) {
		if (null == v)
			return 0;
		if (v instanceof Integer)
			return (Integer) v;
		if (v instanceof Number)
			return ((Number) v).intValue();
		return 0;
	}

}

package org.jasig.cas.authentication.principal;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.CredentialsToPrincipalResolver;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.jdbc.core.JdbcTemplate;

public class EdustarCredentialsToPrincipalResolver implements CredentialsToPrincipalResolver {

	private DataSource dataSource;//查询数据库用
	
	@Override
	public Principal resolvePrincipal(Credentials credentials) {
		System.err.println("将凭据转换成被代理人："+credentials);
		 //强制类型转换
		UsernamePasswordCredentials up =(UsernamePasswordCredentials) credentials;
		String name = up.getUsername();
		String pwd  = up.getPassword();
		String sql = "select id from users2 where u2_name=? and u2_pwd=?"; //查询id
		String id  = null;
		try{
			id=new JdbcTemplate(getDataSource()).queryForObject(sql, String.class, name,pwd);
			if(id!=null){
				//封装其他信息
				Map<String,Object> attrs = new HashMap<String,Object>();
				attrs.put("username",name);
				attrs.put("pwd",pwd);
				Principal p = new SimplePrincipal(id,attrs);//封装成包含id的Principal对象
				System.err.println("生成的属性值是：:"+attrs);
				return p;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean supports(Credentials credentials) {
		boolean boo =  //判断是否是用户和密码凭据 
		UsernamePasswordCredentials.class.isAssignableFrom(credentials.getClass());
		return boo;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}

package com.chinaedustar.sso.web;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.mvc.AbstractController;

import com.chinaedustar.sso.web.support.CurrentUser;

import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.UserService;

/**
 * Base Controller
 */
@SuppressWarnings("deprecation")
public abstract class BaseController extends AbstractController {
	
	protected UserService userService;
	
	protected ConfigService configService;
	
	protected CurrentUser currentUser;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}

//	@NotNull
//	private SimpleJdbcTemplate jdbcTemplate;

	@NotNull
	private DataSource dataSource;

	public final void setDataSource(final DataSource dataSource) {
//		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

//	protected final SimpleJdbcTemplate getJdbcTemplate() {
//		return this.jdbcTemplate;
//	}

	protected final DataSource getDataSource() {
		return this.dataSource;
	}

}

package com.chinaedustar.sso.web;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.chinaedustar.sso.web.support.CurrentUser;
//import org.springframework.web.servlet.mvc.BaseCommandController;
//import org.springframework.web.servlet.mvc.SimpleFormController;
import cn.edustar.usermgr.BaseUser;
import cn.edustar.usermgr.param.query.UserQueryParam;
import cn.edustar.usermgr.pojos.Config;
import cn.edustar.usermgr.pojos.School;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.SchoolService;
import cn.edustar.usermgr.service.UserService;
import cn.edustar.usermgr.util.MD5;
import cn.edustar.usermgr.util.Pager;
import cn.edustar.usermgr.util.QueryHelper;

/**
 * SimpleFormController的处理流程是这样的： 　　
 get请求来到时，这样处理： 　　
1) 请求传递给一个controller对象 　　
2) 调用formBackingObject()方法，创建一个command对象的实例。　　
3) 调用initBinder()，注册需要的类型转换器 　　
4) 调用showForm()方法，返回准备呈现给用户的视图 ,如果“bindOnNewForm”属性设为true,则ServletRequestDataBinder会将初始请求参数填入一个新的表单对象，并且执行onBindOnNewForm()方法。
5) 调用referenceData()方法，准备给用户显示相关的数据。如用户登录需要选择的年度信息　　
6) 返回formView指定的视图

post请求来到时，这样处理： 　　
1) 如果sessionForm属性没有设定，则调用formBackingObject()方法，创建一个command对象的实例。否则从session中取得表单对象　　
2) 将请求传来的参数写入command对象，看它的源代码，会发现它是这样来做的：

ServletRequestDataBinder binder = createBinder(request, command);
binder.bind(request);

3）执行onBind()方法，在绑定数据之后，验证数据之前对表单数据进行一些自制的修改动作。 　　
4) 如果设置为要求验证（validateOnBinding属性被设定），则调用validator类进行数据验证　　
5) 调用onBindAndValidate()方法，该方法允许自定义数据绑定和校验处理　　
6）执行processFormSubmission()检验 Errors对象中含不含错误，如果含有错误则执行showForm()返回到填写表单页面;否则执行onSubmit()方法，进行提交表单，然后转向成功页面。　


 * @author dell
 *
 */

public class EditUserController extends SimpleFormController {
    /** Logger instance */
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @NotNull
    private UserService userService;

    @NotNull
    private CurrentUser currentUser;
    
    @NotNull
    private ConfigService configService;    
    
    @NotNull
    private SchoolService schoolService;    

    private String messageView="casMessageView";
    
    public void setUserService(UserService userService){
    	this.userService=userService;
    }
    public void setSchoolService(SchoolService schoolService){
    	this.schoolService=schoolService;
    }
    public SchoolService getSchoolService(){
    	return this.schoolService;
    }    
    public void setConfigService(ConfigService configService){
    	this.configService=configService;
    }
    
    public ConfigService getConfigService(){
    	return this.configService;
    }    
    
	/**
	 * showForm()这个方法已经被类SimpleFormController实现了并被限定为final，你不可以在继承SimpleFormController的子类里覆写这个类
	 * 
	protected ModelAndView showForm(final HttpServletRequest request, final HttpServletResponse response,BindException errors,@SuppressWarnings("rawtypes") Map controlModel)
            throws Exception {
    	logger.debug("显示输入页面");
    	final Map<String, Object> model = new HashMap<String, Object>();
    	User u=currentUser.getUser(request, response);
    	if(u==null){
	        model.put("message", "请确定您已经登录");
	        model.put("messageType", "error");
	        return new ModelAndView(messageView, model);    		
    	}
    	if(u!=null){
    		if(u.getRole()!=1){
		        model.put("message", "请确定您拥有管理权限");
		        model.put("messageType", "error");
		        return new ModelAndView(messageView, model);    		
    		}
    	}
    	
    	String userid=request.getParameter("id");
    	
    	if(userid==null || userid.length()==0){
	        model.put("message", "请选择用户");
	        model.put("messageType", "error");
	        return new ModelAndView(messageView, model);    		
    	}
    	
    	User user =this.userService.getUserById(Integer.parseInt(userid));
    	if(user==null){
	        model.put("message", "没有找到用户");
	        model.put("messageType", "error");
	        return new ModelAndView(messageView, model);    		
    	}
    	model.put(this.getCommandName(), user);
    	
   		return new ModelAndView(this.getFormView(), model);
    }
*/
    
    protected ModelAndView onSubmit(Object cmd, BindException ex)throws Exception {
    	logger.debug("提交输入页面");
    	
    	HashMap result_map= new HashMap();
		User user = (User) cmd;
		
		String password="";
		String answer="";
		
		User _user=this.userService.getUserById(user.getId());
		if(_user!=null){
			password=_user.getPassword();
			answer=_user.getAnswer();
		}
		if(user.getPassword().length()>0){
			user.setPassword(MD5.toMD5(user.getPassword(),32));
		}else{
			user.setPassword(password);
		}
		user.setCurrentLoginIp("");
		if(user.getAnswer().length()>0){
			user.setAnswer(MD5.toMD5(user.getAnswer(),32));
		}else{
			user.setAnswer(answer);
		}
		this.userService.saveOrUpdate(user);
		result_map.put("userinfo", user);
		return new ModelAndView(this.getSuccessView(), result_map);
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	final Map<String, Object> model = new HashMap<String, Object>();
    	String userid=request.getParameter("id");
    	User user =this.userService.getUserById(Integer.parseInt(userid));
    	if (!isFormSubmission(request)) {
    		//初次GET
    	}else{
    		//POST
    	}
        return user;
    }
    
    /**
     * 准备初始化数据 
     */
    protected Map referenceData(HttpServletRequest req) {
    	Map<String, Object> refData = new HashMap<String, Object>();
    	
    	List<School> schoolList= this.schoolService.getSchools();
    	refData.put("schoolList", schoolList);
    	
    	Config isSelectSchool= this.configService.getConfigByKey("isSelectSchool");
    	refData.put("isSelectSchool", isSelectSchool);
    	
        return(refData);
    } 

    
	public String getMessageView() {
		return messageView;
	}

	public void setMessageView(String messageView) {
		this.messageView = messageView;
	}
	public CurrentUser getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}


    
}

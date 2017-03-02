package cn.zju.action;

import java.io.Serializable;

import org.apache.struts2.ServletActionContext;
import cn.zju.dao.po.User;
import cn.zju.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements Serializable{

	private String username;
	private String password;
	private UserService service; 
	private User user;
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String execute() throws Exception {
		
		return SUCCESS;
	}
	
	public void validateLogin(){
		
	    if("".equals(username) || "".equals(password)){
			ServletActionContext.getRequest().setAttribute("error", "用户名或密码错误");
            addFieldError("", "");
		}
	}
	
	public String login(){
		
		user.setUsername(username);
		user.setPassword(password);
		try {
			String user_name = service.checkUser(user);
			if( user_name != null && (!"".equals(user_name)) ){
				//如果登陆成功 把用户名放到session域
				ActionContext.getContext().getSession().put("user_name", user_name);
			    return SUCCESS;
			}
			ServletActionContext.getRequest().setAttribute("error", "用户名或密码错误");
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

}

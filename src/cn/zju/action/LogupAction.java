package cn.zju.action;

import java.io.File;
import java.io.Serializable;

import org.apache.struts2.ServletActionContext;

import cn.zju.dao.po.User;
import cn.zju.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class LogupAction extends ActionSupport implements Serializable{

	private String username;
	private String password;
	
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
	
	//当进行注册前，检查参数是否正确
	public void validateLogup(){
	    System.out.println(username);
	    System.out.println(password);
	    
	    if("".equals(username) || "".equals(password)){
			ServletActionContext.getRequest().setAttribute("usernameerror", "用户名必须6-20位");
			ServletActionContext.getRequest().setAttribute("passworderror", "密码必须6-20位");
		    addFieldError("", "");
	    }else if(username.length() > 20 || username.length() < 6){
			ServletActionContext.getRequest().setAttribute("usernameerror", "用户名必须6-20位");
	        addFieldError("", "");
		}else if(password.length() > 20 || password.length() < 6){
			ServletActionContext.getRequest().setAttribute("passworderror", "密码必须6-20位");
			addFieldError("", "");
		}
	}
	
	public String logup(){
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		try {
			UserService.createUser(user);
			//注册成功，就在upload下分配一个私人的文件夹
			String path = ServletActionContext.getServletContext().getRealPath("WEB-INF/upload");
			File file = new File(path+File.separator+username);
			file.mkdir();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getRequest().setAttribute("usernameerror", "该用户已注册");
			return ERROR;
		}
	}

}

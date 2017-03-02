package cn.zju.action;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AutoLoginAction extends ActionSupport implements Serializable {
	
	private String user_name;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public void validateAutoLogin(){
		
		if(user_name == null){
			addFieldError("", "");
		}
		if(user_name != null)
			try {
				user_name = new String(user_name.getBytes("iso8859-1"),"utf-8");
				if("".equals(user_name)){
					addFieldError("", "");
				} 
				//虽然有，但是值不匹配，也不能让它自动登陆。因为有可能人为伪造user_name数据传送过来
				if(!user_name.equals(ActionContext.getContext().getSession().get("user_name"))){
					addFieldError("", "");
				}
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				addFieldError("", "");
			}
	}
	
	public String autoLogin(){
		
		return SUCCESS;
		
	}
}

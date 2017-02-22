package cn.zju.action;

import java.io.Serializable;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction  extends ActionSupport implements Serializable{

	@Override
	public String execute() throws Exception {
		
		ServletActionContext.getRequest().getSession().invalidate();
		
		return SUCCESS;
	}
}

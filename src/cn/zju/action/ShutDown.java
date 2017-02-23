package cn.zju.action;

import java.io.Serializable;

import com.opensymphony.xwork2.ActionSupport;

public class ShutDown extends ActionSupport implements Serializable{

	@Override
	public String execute() throws Exception {
		Runtime.getRuntime().exec("shutdown -s -t 30");
		return super.execute();
	}
}

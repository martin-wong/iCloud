package cn.zju.action;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.zju.dao.po.File;
import cn.zju.dao.po.PageBean;
import cn.zju.service.FileService;
import cn.zju.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchUserFileAction extends ActionSupport implements Serializable{

	private int currentpage = 1; //用户想看的页(用户点击的那一页)，默认是第1页
	private int pagesize = 5 ;   //每一个页面呈现几条数据，默认一页是5条数据
	private int startindex;      //用户想看的页的数据在数据库的起始位置 由上面的值计算
	
	private String filepath; //file表的文件路径就是所属的用户的用户名
	
	
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		if(currentpage <= 0)
			this.currentpage = 1;
		else
			this.currentpage = currentpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		    if(pagesize<=0)
		    	this.pagesize = 5;
		    else
			    this.pagesize = pagesize;
	}
	public int getStartindex() {
		this.startindex = (this.currentpage-1)*this.pagesize; 
		return startindex;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
		
	@Override
	public String execute() throws Exception  {
		System.out.println(currentpage);
		System.out.println(pagesize);
		//根据用户查找出它所有的文件
		List<File> list;
		try {
			String username = (String) ActionContext.getContext().getSession().get("user_name");
			//session没有用户名说明没有登陆，让他转去主页
			if(username==null || "".equals(username)){
				return INPUT;
			}
			this.filepath = username;
			list = FileService.getUserFiles(this);
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		} 
		Integer isvip = (Integer) ServletActionContext.getRequest().getAttribute("isvip");
		if(isvip==null){  //没有上传文件之前会调用到这里的代码，上传的时候在uploadAction里会添加isvip
		   isvip = UserService.isVip(this.filepath);
           ServletActionContext.getRequest().setAttribute("isvip", isvip);   
		}
		//拿到每页的数据，每个元素就是一条记录
		PageBean pagebean = new PageBean();
		pagebean.setList(list);
	    pagebean.setCurrentpage(currentpage);
		pagebean.setPagesize(pagesize);
		pagebean.setTotalrecord(FileService.countUserFiles(this));
		
		ServletActionContext.getRequest().setAttribute("pagebean", pagebean);
		
		return SUCCESS;
	}
		
}

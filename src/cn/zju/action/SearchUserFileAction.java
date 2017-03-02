package cn.zju.action;

import java.io.Serializable;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
	
	private PageBean pageBean;
	
	private FileService fileService; 
	private UserService userService; 
	
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
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
		//根据用户查找出它所有的文件
		List<File> list;
		try {
			String username = (String) ActionContext.getContext().getSession().get("user_name");
			//session没有用户名说明没有登陆，让他转去主页
			if(username==null || "".equals(username)){
				return INPUT;
			}
			this.filepath = username;
			list = fileService.getUserFiles(this);
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		} 
		Integer isvip = (Integer) ServletActionContext.getRequest().getAttribute("isvip");
		if(isvip==null){  //没有上传文件之前会调用到这里的代码，上传的时候在uploadAction里会添加isvip
		   isvip = userService.isVip(this.filepath);
           ServletActionContext.getRequest().setAttribute("isvip", isvip);   
		}
		//拿到每页的数据，每个元素就是一条记录
		pageBean.setList(list);
		pageBean.setCurrentpage(currentpage);
	    pageBean.setPagesize(pagesize);
		pageBean.setTotalrecord(fileService.countUserFiles(this));
		
		ServletActionContext.getRequest().setAttribute("pagebean", pageBean);
		
		return SUCCESS;
	}
		
}

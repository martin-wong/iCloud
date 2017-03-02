package cn.zju.action;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.zju.dao.po.File;
import cn.zju.dao.po.PageBean;
import cn.zju.service.FileService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchFileAction extends ActionSupport implements Serializable {
	
	private String searchcontent; //搜索的内容

	private int currentpage = 1; //用户想看的页(用户点击的那一页)，默认是第1页
	private int pagesize = 5 ;   //每一个页面呈现几条数据，默认一页是5条数据
	private int startindex;      //用户想看的页的数据在数据库的起始位置
	
	private PageBean pageBean;
	private FileService service; 

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public void setService(FileService service) {
		this.service = service;
	}
		
	public String getSearchcontent() {
		return searchcontent;
	}
	public void setSearchcontent(String searchcontent) {
		this.searchcontent = searchcontent;
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
		//比如用户想看第2页，每页5条数据，那么数据在数据库里的起始位置是 5
		this.startindex = (this.currentpage-1)*this.pagesize; 
		return startindex;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
		
	public String listFiles() throws Exception{
		//解决get参数乱码
		searchcontent = new String(searchcontent.getBytes("iso8859-1"),"utf-8");
		return execute();
	}

	public void validateExecute(){
		if("".equals(searchcontent) || searchcontent==null){
			addFieldError("", "");
		}
	}
	
	@Override
	public String execute() throws Exception  {
		
		List<File> list;
		try {
			list = service.getAllFiles(this);
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		} 
		//拿到每页的数据，每个元素就是一条记录
		pageBean.setList(list);
		pageBean.setCurrentpage(currentpage);
		pageBean.setPagesize(pagesize);
		pageBean.setTotalrecord(service.countShareFiles(this));
		
		ServletActionContext.getRequest().setAttribute("pagebean", pageBean);
		ServletActionContext.getRequest().setAttribute("searchcontent", searchcontent);
		
		return SUCCESS;
	}
}

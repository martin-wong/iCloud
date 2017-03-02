package cn.zju.action;

import java.io.File;
import java.io.Serializable;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.zju.service.FileService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
 
public class DeleteFileAction extends ActionSupport implements Serializable{
	
	private int currentpage;
	private int pagesize;
	private int startindex;
	private int id; //文件id
	private FileService service; 

	public void setService(FileService service) {
		this.service = service;
	}
	
	public int getStartindex() {
		return startindex;
	}

	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	
	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public String deleteFile(){
	   System.out.println(id);	
	   
	   //判断该用户是否拥有此文件
	   try{
		   String username = service.findFilepathById(id);
		   String login_user = (String) ActionContext.getContext().getSession().get("user_name");
		   String filename = service.findFilenameById(id); //查出文件名
		   if(username!=null && login_user.equals(username) ){
			   service.deleteFileById(id); //删除数据库的该文件记录
			   //从硬盘上删除文件
			   String storepath = new String("D:"+File.separator+"upload"+File.separator+login_user+File.separator);
			   storepath = storepath+filename;
			   System.out.println(storepath);
			   File file = new File(storepath);
			   if(file.exists()){
				   file.delete();
			   }else{
				   ServletActionContext.getRequest().setAttribute("globalmessage", "文件已不存在");
				   return ERROR;
			   }
			   return SUCCESS;
		   }else{ //不通过，可能是人为篡改数据，转发至全局消息页面
			ServletActionContext.getRequest().setAttribute("globalmessage", "该文件可能不属于你");
			return ERROR;
		   }
	   }catch(Exception e){
		   e.printStackTrace();
		   ServletActionContext.getRequest().setAttribute("globalmessage", "该文件可能不属于你");
		   return ERROR;
	   }
	}
}

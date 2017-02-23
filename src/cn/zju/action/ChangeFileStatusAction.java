package cn.zju.action;

import java.io.Serializable;

import org.apache.struts2.ServletActionContext;

import cn.zju.service.FileService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ChangeFileStatusAction extends ActionSupport implements Serializable {

	private int currentpage;
	private int pagesize;
	private int startindex;
	private int id; //文件id
	private int canshare; //1 共享    0 私有
	
	
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

	public int getCanshare() {
		return canshare;
	}

	public void setCanshare(int canshare) {
		this.canshare = canshare;
	}

	//修改用户的某个文件的状态（共享/私有）
	public String changeFileStatus(){
		//把canshare修改进数据库
		System.out.println(id);
		System.out.println(canshare);
		
		try {
			//检查该文件是否属于该用户,否则不允许修改文件状态
			String username = FileService.findFilepathById(id);
			String login_user = (String) ActionContext.getContext().getSession().get("user_name");
			if(username!=null && login_user.equals(username) ){
		    	FileService.updateFileById(this);
			}else{ //不通过，可能是人为篡改数据，转发至首页
				ServletActionContext.getRequest().setAttribute("globalmessage", "该文件可能不属于你");
				return INPUT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getRequest().setAttribute("globalmessage", "未知错误，可能是参数异常");
			return INPUT;
		}
		
		//转发到searchUserFile显示用户的文件
		return SUCCESS;
	}
}

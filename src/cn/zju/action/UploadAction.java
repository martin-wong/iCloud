package cn.zju.action;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import cn.zju.service.FileService;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport implements Serializable{

	
	private String username;
	private File file;  //对应的就是表单中文件上传的那个输入域的名称，Struts2框架会封装成File类型的
	private String fileFileName;//   上传输入域+FileName  文件名  JavaWeb.pdf
	private String fileContentType;// 上传文件的MIME类型  application/pdf

	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	public String upload(){
		
		String storePath = null;
		File store = null;
		try {
			 storePath = "D:"+File.separator+"upload"; //存储目录 D:\\upload
			//存在每个用户有一个自己名字命名的文件夹
			 store = new File(storePath+File.separator+username,fileFileName);
			if(store.exists()){
				ServletActionContext.getRequest().setAttribute("message", "文件已存在");
				return SUCCESS;
			}
			long size = this.file.length() ;
			if( size == 0 ){
				ServletActionContext.getRequest().setAttribute("message", "文件大小不能为0");
				return SUCCESS;
			}
			
			FileUtils.copyFile(file,store); //上传文件到本地硬盘
			//把文件信息存入数据库
			cn.zju.dao.po.File f = new cn.zju.dao.po.File();
			f.setCreatetime(new java.util.Date());
			f.setFilename(fileFileName);
			f.setFilepath(username);
			f.setFilesize(String.valueOf(size/1024+1));
			f.setCanshare(0);
			
			FileService.insertFile(f);
			ServletActionContext.getRequest().setAttribute("message", "上传成功！");
			return SUCCESS;
		} catch (Exception e) {
			ServletActionContext.getRequest().setAttribute("message", "请先选择文件！");
			return SUCCESS;
		}
	}
}


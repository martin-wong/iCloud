package cn.zju.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.zju.service.FileService;

import com.opensymphony.xwork2.ActionSupport;

public class DownloadAction extends ActionSupport implements Serializable{
	
	private int id;
	private String filename;
	private FileService service; 

	public void setService(FileService service) {
		this.service = service;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void validateDownload() throws Exception{
		filename = new String(filename.getBytes("iso-8859-1"),"UTF-8"); //对中文数据处理
		if(id<1)
			addFieldError("", "");
	}
	
	public String download(){
		
		FileInputStream in = null ;
		try {
			String path = service.findFilepathById(id); //相对于/upload的路径
			if(path==null || "".equals(path)){
			    ServletActionContext.getRequest().setAttribute("message", "对不起，您要下载的资源已被删除");
			    return INPUT;
			}
			path = "D:"+File.separator+"upload"+File.separator+path;
			
		    File file = new File(path+File.separator+filename);
			//通知浏览器以下载方式打开
			ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename,"UTF-8"));
			
			in = new FileInputStream(file);
			int len = 0;
			byte buffer[] = new byte[1024];
			OutputStream out = ServletActionContext.getResponse().getOutputStream();
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
			
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return INPUT;
		}finally{
		   try {
			   if(in != null)
			       in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

}

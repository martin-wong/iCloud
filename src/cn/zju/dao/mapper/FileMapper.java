package cn.zju.dao.mapper;

import java.util.List;

import cn.zju.action.ChangeFileStatusAction;
import cn.zju.action.SearchFileAction;
import cn.zju.action.SearchUserFileAction;
import cn.zju.dao.po.File;

public interface FileMapper {
	
	public List<File> getAllFiles(SearchFileAction searchFileAction) throws Exception;
	public int count(SearchFileAction searchFileAction) throws Exception;
	public String findFilepathById(int id) throws Exception;
	public Integer insertFile(File file) throws Exception;
	public List<File> getUserFiles(SearchUserFileAction action)throws Exception;
	public int countUserFiles(SearchUserFileAction action) throws Exception;
	public void updateFileById(ChangeFileStatusAction changeFileStatusAction) throws Exception;
	public void deleteFileById(int id);
	public String findFilenameById(int id);
	
}

package cn.zju.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import cn.zju.action.ChangeFileStatusAction;
import cn.zju.action.SearchFileAction;
import cn.zju.action.SearchUserFileAction;
import cn.zju.dao.mapper.FileMapper;
import cn.zju.dao.po.File;

@Service(value="fileService")
public class FileService {

	@Autowired
	private FileMapper dao;
	
	public  List<File> getAllFiles(SearchFileAction searchFileAction) throws Exception{
		return dao.getAllFiles(searchFileAction);
	}
	
	public  int countShareFiles(SearchFileAction searchFileAction)throws Exception{
		return dao.count(searchFileAction);
	}
	
	public  String findFilepathById(int id) throws Exception{
		return dao.findFilepathById(id);
	}
	
	public  Integer insertFile(File file) throws Exception{
		return dao.insertFile(file);
	}

	public  List<File> getUserFiles(SearchUserFileAction action) throws Exception {
		return dao.getUserFiles(action);
	}

	public  int countUserFiles(SearchUserFileAction action) throws Exception {
		return dao.countUserFiles(action);
		
	}

	public  void updateFileById(ChangeFileStatusAction changeFileStatusAction) throws Exception{
		 dao.updateFileById(changeFileStatusAction);
	}

	public  void deleteFileById(int id) {
		dao.deleteFileById(id);
	}

	public  String findFilenameById(int id) {
		return dao.findFilenameById(id);
	}

}

package cn.zju.service;

import java.util.List;

import cn.zju.action.ChangeFileStatusAction;
import cn.zju.action.SearchFileAction;
import cn.zju.action.SearchUserFileAction;
import cn.zju.dao.FileDao;
import cn.zju.dao.po.File;

public class FileService {

	private static FileDao dao = new FileDao();
	
	public static List<File> getAllFiles(SearchFileAction searchFileAction) throws Exception{
		return dao.getAllFiles(searchFileAction);
	}
	
	public static int countShareFiles(SearchFileAction searchFileAction)throws Exception{
		return dao.countShareFiles(searchFileAction);
	}
	
	public static String findFilepathById(int id) throws Exception{
		return dao.findFilepathById(id);
	}
	
	public static Integer insertFile(File file) throws Exception{
		return dao.insertFile(file);
	}

	public static List<File> getUserFiles(SearchUserFileAction action) throws Exception {
		return dao.getUserFiles(action);
	}

	public static int countUserFiles(SearchUserFileAction action) throws Exception {
		return dao.countUserFiles(action);
		
	}

	public static void updateFileById(ChangeFileStatusAction changeFileStatusAction) throws Exception{
		 dao.updateFileById(changeFileStatusAction);
	}

	public static void deleteFileById(int id) {
		dao.deleteFileById(id);
	}

	public static String findFilenameById(int id) {
		return dao.findFilenameById(id);
	}

}

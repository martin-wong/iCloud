package cn.zju.service;

import cn.zju.dao.UserDao;
import cn.zju.dao.po.User;

public class UserService {
	private static UserDao dao = new UserDao();

	public static void createUser(User user) throws Exception{
		Boolean found = findUser(user.getUsername());
		if(!found)
		   dao.createUser(user);
		else
			throw new RuntimeException();
	}
	
	public static String checkUser(User user ) throws Exception{
		return dao.checkUser(user);
	}
	
	public static boolean findUser(String username) throws Exception{
		return dao.findUser(username);
	}

	public static int isVip(String user_name)throws Exception {
		return dao.isVip(user_name);
	}
}

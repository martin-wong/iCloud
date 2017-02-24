package cn.zju.dao;

import org.apache.ibatis.session.SqlSession;

import cn.zju.dao.mapper.UserMapper;
import cn.zju.dao.po.User;

public class UserDao {
     //创建用户
	public void createUser(User user) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		mapper.createUser(user);
		session.commit();
		session.close();
	}
	//如果数据库里存在用户名和密码都匹配的记录 代表登陆成功
	public String checkUser(User user)throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		String user_name = mapper.checkUser(user);
		session.close();
		return user_name;
	}
	//查找指定用户名的用户是否存在
	public boolean findUser(String username)throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		Integer found = mapper.findUser(username);
		session.close();
		if(found==null || found<1)  return false;
		return true;
	}
	public int isVip(String user_name) throws Exception{
		SqlSession session = DaoUtil.getSqlSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		Integer isvip = mapper.isVip(user_name);
		session.close();
		if(isvip==null || isvip == 0)  return 0;
		else return 1;
	
	}
	
}

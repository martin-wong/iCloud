package cn.zju.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;


public class DaoUtil {

		private static SqlSessionFactory sqlSessionFactory;

		static{
			// 创建sqlSessionFactory
			// mybatis配置文件
			String resource = "SqlMapConfig.xml";
			// 得到配置文件流
			InputStream inputStream;
			try {
				inputStream = Resources.getResourceAsStream(resource);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			// 创建会话工厂，传入mybatis的配置文件信息
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}
		
		public static SqlSession getSqlSession(){
			return sqlSessionFactory.openSession();
		}

}

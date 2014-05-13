package com.god.article.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.god.article.bean.ArticleBean;

@Repository
public class ArticleDao {
	
	@Resource(name="dataSource")
	DataSource dataSource;
	
	
	
	public String add(final ArticleBean bean){
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		int i =	jdbc.update("insert into article(title,content) value (?,?)",new PreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement ps)
						throws SQLException {
					ps.setString(1, bean.getName());
					ps.setString(2, bean.getContent());
				}
				
			});
		return i+"";
	}
//	public ArticleBean find(String key){
//		
//	}
//	public List<ArticleBean> list(ArticleBean bean){
//		
//	}
	

}

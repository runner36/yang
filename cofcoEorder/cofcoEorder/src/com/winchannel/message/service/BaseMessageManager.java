package com.winchannel.message.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.winchannel.base.model.BaseOrg;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.message.model.BaseAuthoritys;
import com.winchannel.message.model.BaseMessages;
import com.winchannel.message.model.BaseMessageOnShows;

public class BaseMessageManager extends HibernateEntityDao<BaseMessages>{

	
	public String[] getOrgIdByBaseAuthorities(Set BaseAuthorities ) {
		String orgIds = "";
		String orgNames = "";
		for (Iterator it = BaseAuthorities.iterator(); it.hasNext();) {
			BaseAuthoritys baseAuth = (BaseAuthoritys) it.next();
			if(baseAuth.getFlag()==0){
			BaseOrg org=getOrgByOrgId(baseAuth.getOrgId());
			orgIds += org.getOrgId();
			orgNames+=org.getOrgName();
			 orgNames += ",";
			 orgIds += ",";
			}
		}
		
		return new String[]{orgIds, orgNames};
	}
	public BaseOrg getOrgByOrgId(Long orgId) {
		return (BaseOrg) this.findUnique("from BaseOrg baseOrg where baseOrg.orgId=?",orgId);
	}
	
	public List<BaseMessageOnShows> getAllBaseMessageOnShow(String subCode){
		String sql ="SELECT DISTINCT m.title as title ,m.type as type ,m.isdel as isdel,m.createdBy as createdBy,m.content as content,m.id as id,fi.FILEURL AS fileurl from BASE_MESSAGE m " +
 		"LEFT JOIN BASE_AUTHORITY o ON o.MAIN_ID=m.ID " +
 		"LEFT JOIN FILE_ITEM fi ON  m.id=fi.id "+
 		"LEFT JOIN BASE_ORG r ON r.ORG_ID=o.ORG_ID WHERE r.SUB_CODE LIKE '"+
 		subCode +"%'"+" and (fi.flag IS NULL  or  fi.FLAG=0) AND  o.FLAG=0  ORDER BY m.isdel, m.createdBy desc ";
        List<BaseMessageOnShows> list=new ArrayList<BaseMessageOnShows>();
        System.out.println(sql);
        list = this.executeSqlQuery(BaseMessageOnShows.class,sql);
		return list;
	}
	
	public List<BaseMessageOnShows> getAllBaseMessageOnShowAll(String subCode){
		String sql ="SELECT DISTINCT m.title as title ,m.type as type ,m.isdel as isdel,m.createdBy as createdBy,m.content as content,m.id as id,fi.FILEURL AS fileurl from BASE_MESSAGE m " +
		"LEFT JOIN BASE_AUTHORITY o ON o.MAIN_ID=m.ID " +
		"LEFT JOIN FILE_ITEM fi ON  m.id=fi.id "+
 		"where (fi.flag IS NULL  or  fi.FLAG=0) AND  o.FLAG=0 ORDER BY m.isdel, m.createdBy desc ";
        List<BaseMessageOnShows> list=new ArrayList<BaseMessageOnShows>();
        System.out.println(sql);
        list = this.executeSqlQuery(BaseMessageOnShows.class,sql);
		return list;
	}
	public List<BaseMessageOnShows> getOnlyNew(int newId){
		String sql="SELECT DISTINCT m.title as title ,m.type as type ,m.isdel as isdel, m.createdBy as createdBy,m.content as content,m.id as id,fi.FILEURL AS fileurl from BASE_MESSAGE m " +
 		"LEFT JOIN BASE_AUTHORITY o ON o.MAIN_ID=m.ID " +
 		"LEFT JOIN FILE_ITEM fi ON  m.id=fi.id "+
 		
 		"where m.id="+newId+" and (fi.flag IS NULL  or  fi.FLAG=0) AND  o.FLAG=0  ORDER BY m.isdel, m.createdBy desc";
		 List<BaseMessageOnShows>  onShow=new ArrayList<BaseMessageOnShows>();
		 System.out.println(sql);
		 onShow=this.executeSqlQuery(BaseMessageOnShows.class, sql);
		return onShow;
	}
	
	
	public List<BaseMessageOnShows> getBaseMessageByType(long type,String subCode){
		String sql="SELECT DISTINCT m.title as title ,m.type as type ,m.isdel as isdel,m.createdBy as createdBy,m.content as content,m.id as id,fi.FILEURL AS fileurl from BASE_MESSAGE m " +
 		"LEFT JOIN BASE_AUTHORITY o ON o.MAIN_ID=m.ID " +
 		"LEFT JOIN FILE_ITEM fi ON  m.id=fi.id "+
 		"LEFT JOIN BASE_ORG r ON r.ORG_ID=o.ORG_ID "+
 		"where m.type="+type+"and r.SUB_CODE LIKE '"+subCode +"%'"+" and (fi.flag IS NULL  or  fi.FLAG=0) AND  o.FLAG=0  ORDER BY m.isdel, m.createdBy desc";
		List<BaseMessageOnShows> result=new ArrayList<BaseMessageOnShows>();
		 System.out.println(sql);
		result=this.executeSqlQuery(BaseMessageOnShows.class, sql);
		return result;
	}
	
	public List<BaseMessageOnShows> getBaseMessageByTypeAll(long type,String subCode){
		String sql="SELECT DISTINCT m.title as title ,m.type as type ,m.isdel as isdel,m.createdBy as createdBy,m.content as content,m.id as id,fi.FILEURL AS fileurl from BASE_MESSAGE m " +
 		"LEFT JOIN BASE_AUTHORITY o ON o.MAIN_ID=m.ID " +
 		"LEFT JOIN FILE_ITEM fi ON  m.id=fi.id "+
 		"where m.type="+type+" and (fi.flag IS NULL  or  fi.FLAG=0) AND  o.FLAG=0  ORDER BY m.isdel, m.createdBy desc";
		List<BaseMessageOnShows> result=new ArrayList<BaseMessageOnShows>();
		 System.out.println(sql);
		result=this.executeSqlQuery(BaseMessageOnShows.class, sql);
		return result;
	}
	public List<BaseMessageOnShows>  getBaseOpnionByEmpId(Long empId){
		String sql="SELECT DISTINCT m.title as title ,m.created_By as createdBy,m.content as content,m.id as id,fi.FILEURL AS fileurl " +
				" from   BASE_EMPLOYEE emp LEFT JOIN BASE_ORG r   ON r.ORG_ID =emp.ORG_ID  LEFT JOIN BASE_AUTHORITY o   ON r.ORG_ID=o.ORG_ID " +
				" LEFT JOIN BASE_OPINION m    ON o.OPN_ID=m.ID  LEFT JOIN FILE_ITEM fi ON  m.id=fi.id " +
				" WHERE  emp.EMP_ID="+empId+"  AND    (fi.flag IS NULL  or  fi.FLAG=0) AND  o.FLAG=0  " +
				"ORDER BY m.created_By desc";   //根据该用户的人员ID来找到组织查是否有改组织的建议，
		 System.out.println(sql);
		 List<BaseMessageOnShows>  result=new ArrayList<BaseMessageOnShows>();
		 result=this.executeSqlQuery(BaseMessageOnShows.class, sql);
		return result;	
	}
	
	public List<BaseMessageOnShows> getOnlyOpnion(long id){
		String sql="SELECT DISTINCT m.title as title ,m.created_By as createdBy,m.content as content,m.id as id,fi.FILEURL AS fileurl " +
				"from BASE_OPINION m  LEFT JOIN BASE_AUTHORITY o ON o.opn_ID=m.ID " +
				"LEFT JOIN FILE_ITEM fi ON  m.id=fi.opn_id  where m.id="+id+" and (fi.flag IS NULL  or  fi.FLAG=0) AND  o.FLAG=0 " +
				"ORDER BY m.created_By desc";
		System.out.println(sql);
		List<BaseMessageOnShows> result=new ArrayList<BaseMessageOnShows>();
		result=this.executeSqlQuery(BaseMessageOnShows.class, sql);
		return result;	
	}
}

package com.winchannel.message.service;

import java.util.List;

import com.winchannel.base.model.BaseOrg;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.message.model.BaseOpinions;

public class BaseOpinionManager  extends HibernateEntityDao<BaseOpinions>{

  public List<BaseOrg> getBaseOrg(String subCode){
	  
	  String sql ="SELECT org_id as orgId FROM BASE_ORG WHERE  '"+subCode+"'  LIKE sub_code +'%'";
	  List<BaseOrg> baseOrg=this.executeSqlQuery(BaseOrg.class, sql);
	  return baseOrg;  
  }   
  
  
}

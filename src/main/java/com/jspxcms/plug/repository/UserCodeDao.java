package com.jspxcms.plug.repository;


import org.springframework.data.repository.Repository;

import com.jspxcms.plug.domain.UserCode;

public interface UserCodeDao extends Repository<UserCode, Integer>, UserCodeDaoPlus {
	
	public  UserCode find(String id,String name);
	
	public  UserCode save(UserCode bean);
	
	
	//public  UserCode update(UserCode bean);
	
}

package com.jspxcms.plug.repository;


import java.util.List;

import com.jspxcms.plug.domain.UserCode;

public interface UserCodeDaoPlus {

	public  UserCode find(String id,String name);
	
	public  List<UserCode> findAll();
	
	public  UserCode find(Integer id);

}

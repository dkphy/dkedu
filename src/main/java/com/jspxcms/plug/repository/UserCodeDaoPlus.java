package com.jspxcms.plug.repository;


import java.util.List;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.UserCode;

@Deprecated
public interface UserCodeDaoPlus {

	public UserCode find(String id,String name);
	
	public List<UserCode> findAll();
	
	public UserCode find(Integer id);
	
	public List<UserCode> getList(Integer[] siteId, Limitable limitable);

}

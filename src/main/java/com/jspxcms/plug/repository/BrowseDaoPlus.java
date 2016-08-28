package com.jspxcms.plug.repository;

import java.util.List;

import com.jspxcms.plug.domain.Browse;

public interface BrowseDaoPlus {
	
	public List<Browse> findByUserId(Integer userId,Integer limitCount);

}

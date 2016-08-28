package com.jspxcms.plug.repository;

import org.springframework.data.repository.Repository;

import com.jspxcms.plug.domain.Browse;

public interface BrowseDao extends Repository<Browse, Integer>,BrowseDaoPlus{
	
	public Browse save(Browse bean);
}

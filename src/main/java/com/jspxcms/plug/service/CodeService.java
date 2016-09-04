package com.jspxcms.plug.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jspxcms.common.orm.RowSide;
import com.jspxcms.plug.domain.UserCode;

public interface CodeService {
	
	public Page<UserCode> findAll(Integer siteId, Map<String, String[]> params,
			Pageable pageable);
	
	public RowSide<UserCode> findSide(Integer siteId,
			Map<String, String[]> params, UserCode bean, Integer position,
			Sort sort);
	
	public abstract UserCode findByNameAndIdCard(String name, String idCard);
	
	public UserCode save(UserCode bean);
	
	public UserCode update(UserCode bean);
	
	public abstract UserCode add(String name, String idCard, Double score);
	
	public abstract UserCode find(Integer id);
	
	public abstract UserCode update(String idCard, String name, Double Score);
	
	public UserCode[] delete(Integer[] ids);
	
	public UserCode delete(Integer id);

}
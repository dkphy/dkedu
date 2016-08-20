package com.jspxcms.plug.service;

import java.util.List;

import com.jspxcms.plug.domain.UserCode;

public interface CodeService {

	
	public abstract UserCode find(String name,String idCard);
	
	public abstract UserCode add(String name,String idCard,Double score);
	
	public abstract List<UserCode> findAll();
	
	public abstract UserCode find(Integer id);
	
	public abstract UserCode update(String idCard,String name,Double Score);
	
	//public abstract void delete(String name,String idCard,Double score);

}
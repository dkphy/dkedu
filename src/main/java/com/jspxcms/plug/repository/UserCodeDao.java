package com.jspxcms.plug.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.UserCode;

public interface UserCodeDao extends Repository<UserCode, Integer> {
	
	public UserCode save(UserCode bean);
	
	public UserCode findOne(Integer id);
	
	public void delete(UserCode bean);
	
	public Page<UserCode> findAll(Specification<UserCode> spec, Pageable pageable);

	public List<UserCode> findAll(Specification<UserCode> spec, Limitable limitable);
	
	@Query(value = "from UserCode bean where bean.name=?1 and bean.idCard=?2")
	public List<UserCode> findByNameAndIdCard(String name,String idCard);
}

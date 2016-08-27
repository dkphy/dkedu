package com.jspxcms.plug.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.plug.domain.MobileVerify;

public interface MobileVerifyDao extends Repository<MobileVerify, Integer> {
	
	void save(MobileVerify v);
	
	@Query("from MobileVerify bean where bean.mobile=?1 and bean.status='waiting'")
	List<MobileVerify> findWaitingRecordByMobile(String mobile, Pageable pageable);
	
	@Modifying
	@Query("update MobileVerify bean set bean.status = 'verified', bean.modifyTime = now() where bean.mobile = ?1")
	public int verify(String mobile);

}

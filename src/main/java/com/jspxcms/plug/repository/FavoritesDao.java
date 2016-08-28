package com.jspxcms.plug.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.Favorites;

public interface FavoritesDao extends Repository<Favorites, Integer>{
	
	public Favorites findById(Integer id);
	
	public Favorites save(Favorites f);
	
	@Modifying
	@Query("update Favorites f set f.gmtModify = now() where f.id = ?1")
	public void modifyFavorites(Integer id);
	
	@Query("from Favorites f where f.userId=?1 and f.type=?2")
	public List<Favorites> findByUserIdAndType(Integer userId, String type, Pageable pageable);
	
	@Query("from Favorites f where f.userId=?1 and f.objectId=?2 and f.type=?3")
	public List<Favorites> findByUserIdTypeAndCourseId(Integer userId, Integer objectId, String type);
	
}

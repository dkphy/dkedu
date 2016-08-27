package com.jspxcms.plug.repository;


import org.springframework.data.repository.Repository;

import com.jspxcms.plug.domain.Favorites;

public interface FavoritesDao extends Repository<Favorites, Integer>,FavoritesDaoPlus{
	
	public  Favorites findById(Integer id);
	
	public  Favorites save(Favorites f);
	
}

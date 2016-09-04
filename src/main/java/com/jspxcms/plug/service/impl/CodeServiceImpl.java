package com.jspxcms.plug.service.impl;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.RowSide;
import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.plug.domain.ScoreStatus;
import com.jspxcms.plug.domain.UserCode;
import com.jspxcms.plug.repository.UserCodeDao;
import com.jspxcms.plug.service.CodeService;

/**
 * 成绩
 * @author Administrator
 *
 */
@Service
public class CodeServiceImpl implements CodeService {
	
	@Autowired
	private UserCodeDao dao;
	
	public Page<UserCode> findAll(Integer siteId, Map<String, String[]> params,
			Pageable pageable) {
		return dao.findAll(spec(siteId, params), pageable);
	}
	
	private Specification<UserCode> spec(final Integer siteId,
			Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<UserCode> fsp = SearchFilter.spec(filters, UserCode.class);
		Specification<UserCode> sp = new Specification<UserCode>() {
			public Predicate toPredicate(Root<UserCode> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				return pred;
			}
		};
		return sp;
	}
	
	public RowSide<UserCode> findSide(Integer siteId,
			Map<String, String[]> params, UserCode bean, Integer position,
			Sort sort) {
		if (position == null) {
			return new RowSide<UserCode>();
		}
		Limitable limit = RowSide.limitable(position, sort);
		List<UserCode> list = dao.findAll(spec(siteId, params), limit);
		return RowSide.create(list, bean);
	}
	
	@Override
	//查询一条记录
	public UserCode findByNameAndIdCard(String name,String idCard){
		if(StringUtils.isEmpty(name)){
			throw new RuntimeException("查询成绩：姓名不得为空");
		}
		if(StringUtils.isEmpty(idCard)){
			throw new RuntimeException("查询成绩：身份证号不得为空");
		}
		List<UserCode> list = dao.findByNameAndIdCard(name, idCard);
		if(list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	@Transactional
	public UserCode save(UserCode bean) {
		bean.applyDefaultValue();
		return dao.save(bean);
	}
	
	@Transactional
	public UserCode update(UserCode bean) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	//新增
	@Override
	@Transactional
	public UserCode add(String name, String idCard, Double score) {

		if(StringUtils.isEmpty(name)){
			throw new RuntimeException("录入成绩：姓名不得为空");
		}
		if(StringUtils.isEmpty(idCard)){
			throw new RuntimeException("录入成绩：身份证号不得为空");
		}
		if(score==null || score<0){
			throw new RuntimeException("录入成绩：成绩输入有误");
		}
		UserCode uc = new UserCode();
		uc.setIdCard(idCard);
		uc.setName(name);
		uc.setScore(score);
		uc.setScoreStatus(ScoreStatus.SCORE_USEABLE);
		Date date = new Date();
		uc.setGmtCreate(date);
		uc.setGmtModify(date);
		uc.setVersion(1);
		
		UserCode ucSql = dao.save(uc);
		
		return ucSql;
	}


	@Override
	public UserCode find(Integer id) {
		if(id==null){
			throw new RuntimeException("查询一条记录：获取ID异常");
		}
		UserCode uc = dao.findOne(id);
		return uc;
	}

	//修改
	@Override
	@Transactional
	public UserCode update(String idCard, String name, Double score) {
		if(score==null){
			throw new  RuntimeException("成绩不能不为空");
		}
		if(StringUtils.isBlank(idCard)){
			throw new  RuntimeException("姓名不能不为空");
		}
		if(StringUtils.isBlank(idCard)){
			throw new  RuntimeException("身份证号不能不为空");
		}
		UserCode uc = this.findByNameAndIdCard(name, idCard);
		uc.setGmtModify(new Date());
		uc.setScore(score);
		uc.setVersion(uc.getVersion()+1);
		
		return dao.save(uc);
	}
	
	@Transactional
	public UserCode[] delete(Integer[] ids) {
		UserCode[] beans = new UserCode[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = delete(ids[i]);
		}
		return beans;
	}
	
	@Transactional
	public UserCode delete(Integer id) {
		UserCode entity = dao.findOne(id);
		dao.delete(entity);
		return entity;
	}
}

package com.jspxcms.plug.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.plug.domain.ScoreStatus;
import com.jspxcms.plug.domain.UserCode;
import com.jspxcms.plug.repository.UserCodeDao;
import com.jspxcms.plug.service.CodeService;
import com.jspxcms.plug.web.back.ResumeController;

/**
 * 成绩
 * @author Administrator
 *
 */
@Service
public class CodeServiceImpl implements CodeService {
private static final Logger log = LoggerFactory
			.getLogger(ResumeController.class);
	
	@Autowired
	private UserCodeDao dao;
	
	@Override
	//查询一条记录
	public UserCode find(String name,String idCard){
		if(StringUtils.isEmpty(name)){
			throw new RuntimeException("查询成绩：姓名不得为空");
		}
		if(StringUtils.isEmpty(idCard)){
			throw new RuntimeException("查询成绩：身份证号不得为空");
		}
		return dao.find(idCard, name);
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
		
		log.info("验证通过");
		
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

	//查询所有
	@Override
	public List<UserCode> findAll() {
		return (List<UserCode>) dao.findAll();
	}

	@Override
	public UserCode find(Integer id) {
		if(id==null){
			throw new RuntimeException("查询一条记录：获取ID异常");
		}
		UserCode uc = dao.find(id);
		return uc;
	}

	//修改
	@Override
	@Transactional
	public UserCode update(String idCard,String name,Double score) {
		if(score==null){
			throw new  RuntimeException("成绩不能不为空");
		}
		if(StringUtils.isBlank(idCard)){
			throw new  RuntimeException("姓名不能不为空");
		}
		if(StringUtils.isBlank(idCard)){
			throw new  RuntimeException("身份证号不能不为空");
		}
		UserCode uc = dao.find(idCard, name);
		uc.setGmtModify(new Date());
		uc.setScore(score);
		uc.setVersion(uc.getVersion()+1);
		
		return dao.save(uc);
	}
	
	/*@Override
	@Transactional
	public void delete(String name, String idCard, Double score) {

		if(StringUtils.isEmpty(name)){
			throw new RuntimeException("录入成绩：姓名不得为空");
		}
		if(StringUtils.isEmpty(idCard)){
			throw new RuntimeException("录入成绩：身份证号不得为空");
		}
		if(score==null || score<0){
			throw new RuntimeException("录入成绩：成绩输入有误");
		}
		
		log.info("验证通过");
		
		UserCode uc = new UserCode();
		uc.setIdCard(idCard);
		uc.setName(name);
		uc.setScore(score);
		uc.setScoreStatus(ScoreStatus.SCORE_ABANDON);
		Date date = new Date();
		uc.setGmtModify(date);
		uc.setVersion(1);
		
		dao.update(uc);
		
	}*/
	
}

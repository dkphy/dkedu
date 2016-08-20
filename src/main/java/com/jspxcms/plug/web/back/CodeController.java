package com.jspxcms.plug.web.back;


import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.plug.domain.UserCode;
import com.jspxcms.plug.service.CodeService;

@Controller
@RequestMapping(value = "/plug/find_code")
public class CodeController {
	private static final Logger logger = LoggerFactory
			.getLogger(ResumeController.class);

	@Resource
	private CodeService fc;

	// 权限注解
	@RequiresPermissions("plug:find_code:codelist")
	@RequestMapping(value = "find.do")
	//查询所有
	public String userList(Model modelMap) {
		
		logger.info("enter in  后台");
		List<UserCode> list = fc.findAll();
		modelMap.addAttribute("listCode", list);
		logger.info("获取第一个姓名"+list.get(0).getName());
		return "plug/resume/find_all";
	}
	
	// 修改页面跳转
	@RequiresPermissions("plug:find_code:updateUser")
	@RequestMapping(value = "updateUser.do")
	public String updateUser(Integer id,Model modelMap) {
		logger.info("enter in  updateUser 后台");
		UserCode uc = fc.find(id);
		modelMap.addAttribute("uc", uc);
		return "plug/find_code/updateUser";
	}

	// 修改成绩
	@RequiresPermissions("plug:find_code:update")
	@RequestMapping(value = "update.do")
	public String updateUser(String idCard, String name,Double score, Model modelMap) {
		logger.info("enter in  update 后台");
		fc.update(idCard, name, score);
		List<UserCode> list = fc.findAll();
		modelMap.addAttribute("listCode", list);
		return "plug/resume/find_all";
	}
}

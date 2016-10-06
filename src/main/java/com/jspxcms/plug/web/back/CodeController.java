package com.jspxcms.plug.web.back;


import static com.jspxcms.core.support.Constants.CREATE;
import static com.jspxcms.core.support.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.support.Constants.EDIT;
import static com.jspxcms.core.support.Constants.MESSAGE;
import static com.jspxcms.core.support.Constants.OPRT;
import static com.jspxcms.core.support.Constants.SAVE_SUCCESS;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jspxcms.common.orm.RowSide;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.plug.domain.UserCode;
import com.jspxcms.plug.service.CodeService;

@Controller
@RequestMapping(value = "/plug/find_code")
public class CodeController {

	@Resource
	private CodeService service;

	// 权限注解
	@RequiresPermissions("plug:score:list")
	@RequestMapping(value = "list.do")
	//查询所有
	public String list(
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		Page<UserCode> pagedList = service.findAll(null, params, pageable);
		modelMap.addAttribute("pagedList", pagedList);
		return "plug/find_code/score_list";
	}
	
	@RequiresPermissions("core:score:create")
	@RequestMapping("create.do")
	public String create(Integer id, org.springframework.ui.Model modelMap) {
		if (id != null) {
			UserCode bean = service.find(id);
			modelMap.addAttribute("bean", bean);
		}
		modelMap.addAttribute(OPRT, CREATE);
		return "plug/find_code/score_form";
	}

	@RequiresPermissions("core:score:edit")
	@RequestMapping("edit.do")
	public String edit(Integer id, Integer position,
			@PageableDefaults(sort = "id", sortDir = Direction.DESC) Pageable pageable,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Integer siteId = Context.getCurrentSiteId(request);
		UserCode bean = service.find(id);
		Map<String, String[]> params = Servlets.getParameterValuesMap(request,
				Constants.SEARCH_PREFIX);
		RowSide<UserCode> side = service.findSide(siteId, params, bean, position,
				pageable.getSort());
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("side", side);
		modelMap.addAttribute("position", position);
		modelMap.addAttribute(OPRT, EDIT);
		return "plug/find_code/score_form";
	}

	@RequiresPermissions("core:score:save")
	@RequestMapping("save.do")
	public String save(@Valid UserCode bean, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		service.save(bean);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else if (Constants.REDIRECT_CREATE.equals(redirect)) {
			return "redirect:create.do";
		} else {
			ra.addAttribute("id", bean.getId());
			return "redirect:edit.do";
		}
	}

	@RequiresPermissions("core:score:update")
	@RequestMapping("update.do")
	public String update(@ModelAttribute("bean") UserCode bean, Integer position,
			String redirect, HttpServletRequest request, RedirectAttributes ra) {
		service.update(bean);
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		if (Constants.REDIRECT_LIST.equals(redirect)) {
			return "redirect:list.do";
		} else {
			ra.addAttribute("id", bean.getId());
			ra.addAttribute("position", position);
			return "redirect:edit.do";
		}
	}
	
	@RequiresPermissions("core:score:delete")
	@RequestMapping("delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			RedirectAttributes ra) {
		service.delete(ids);
		ra.addFlashAttribute(MESSAGE, DELETE_SUCCESS);
		return "redirect:list.do";
	}
}

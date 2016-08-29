package com.jspxcms.core.web.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.jspxcms.common.freemarker.Freemarkers;
import com.jspxcms.plug.domain.Browse;
import com.jspxcms.plug.service.BrowseService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 *BrowseListDirective
 * 
 * @author zhw
 * 
 * 
 */
public class BrowseListDirective implements TemplateDirectiveModel {
	
	public static final String USER_ID = "userId";
	public static final String LIMIT = "limit";
	
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if (loopVars.length < 1) {
			throw new TemplateModelException("Loop variable is required.");
		}
		if (body == null) {
			throw new RuntimeException("missing body");
		}
		// 提取Parameter的参数
		Integer userId = Freemarkers.getInteger(params, USER_ID);
		Integer limit = Freemarkers.getInteger(params, LIMIT);
		// 进行查询
		Sort defSort = new Sort(Direction.DESC, "gmtModify");
		Pageable limitable = new PageRequest(0, limit, defSort);
		List<Browse> list = service.findByUserId(userId, limitable);
//			List<Special> list = service.findList(siteId, categoryId,
//					beginDate, endDate, isWithImage, isRecommend, limitable);
		// 渲染结果
		loopVars[0] = env.getObjectWrapper().wrap(list);
		body.render(env.getOut());
	}
	
	@Autowired
	private BrowseService service;
}

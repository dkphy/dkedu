package com.jspxcms.core.web.directive;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

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
 * SpecialDirective
 * 
 * @author Zhanghongwei
 * 
 */
public class BrowseDirective implements TemplateDirectiveModel {
	public static final String ID = "id";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if (loopVars.length < 1) {
			throw new TemplateModelException("Loop variable is required.");
		}
		if (body == null) {
			throw new RuntimeException("missing body");
		}
		Integer id = Freemarkers.getInteger(params, ID);
		Browse browse = query.get(id);
		loopVars[0] = env.getObjectWrapper().wrap(browse);
		body.render(env.getOut());
	}

	@Autowired
	private BrowseService query;
}

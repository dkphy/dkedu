package com.jspxcms.core.web.fore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspxcms.common.orm.LimitRequest;
import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.util.JsonMapper;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Tag;
import com.jspxcms.core.fulltext.InfoFulltextService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.NodeQueryService;

/**
 * 异步方式分页查询Controller
 * @author lvbin
 *
 */
@Controller
public class AjaxPageController {
	
	@RequestMapping(value = "/ajaxInfo/{targetId:[0-9]+}.jspx")
	public void ajaxInfo(@PathVariable Integer targetId, @RequestParam Integer offset,
			@RequestParam(defaultValue = "5") Integer count,
			HttpServletResponse response) {
		List<Info> list = doQuery(offset, count, targetId, null);
		renderResult(list, response);
	}
	
	@RequestMapping(value = "/ajaxTag/{targetId:[0-9]+}.jspx")
	public void ajaxTag(@PathVariable Integer targetId, @RequestParam Integer offset,
			@RequestParam(defaultValue = "5") Integer count,
			HttpServletResponse response) {
		List<Info> list = doQuery(offset, count, null, targetId);
		renderResult(list, response);
	}
	
	@RequestMapping(value = "/ajaxSearch.jspx")
	public void ajaxSearch(@RequestParam String q, @RequestParam Integer offset,
			@RequestParam(defaultValue = "5") Integer count,
			HttpServletResponse response) {
		List<Info> list = doFullTextQuery(offset, count, q);
		renderResult(list, response);
	}
	
	private void renderResult(List<Info> list, HttpServletResponse response) {
		List<SimpleInfoDTO> simpleList = new ArrayList<SimpleInfoDTO>();
		for (Info info : list) {
			SimpleInfoDTO si = new SimpleInfoDTO();
			BeanUtils.copyProperties(info, si);
			if(info.getTags() != null && !info.getTags().isEmpty()) {
				Integer[] tagIds = new Integer[info.getTags().size()];
				String[] tagNames = new String[info.getTags().size()];
				List<Tag> tags = info.getTags();
				for(int i=0; i<tags.size(); i++) {
					tagIds[i] = tags.get(i).getId();
					tagNames[i] = tags.get(i).getName();
				}
				si.setTagIds(tagIds);
				si.setTagNames(tagNames);
			}
			simpleList.add(si);
		}
		
		JsonMapper mapper = new JsonMapper();
		try {
			String result = mapper.toJson(simpleList);
			Servlets.writeHtml(response, result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	private List<Info> doQuery(Integer offset, Integer count, Integer nodeId, Integer tagId) {
		Integer[] siteId = null;
		Integer[] modelId = null;

		Integer[] nodeIds = (nodeId==null) ? new Integer[]{} : new Integer[]{nodeId};
		Integer[] tagIds = (tagId==null) ? new Integer[]{} : new Integer[]{tagId};
		String[] treeNumber = null;
		Integer[] attrId = null;
		String[] tagName = null;

		Integer[] specialId = null;
		String[] specialTitle = null;

		Integer[] userId = null;
		Integer[] priority = null;
		Date beginDate = null;
		Date endDate = null;
		String[] title = null;

		Integer[] includeId = null;
		Integer[] excludeId = null;
		String[] status = new String[] { Info.NORMAL };
		Boolean isWithImage = null;
		Integer[] viewGroupId = null;
		Integer[] viewOrgId = null;
		Integer[] mainNodeId = null;
		String[] excludeTreeNumber = null;
		Integer[] excludeMainNodeId = null;
		
		Integer[] p1 = null;
		Integer[] p2 = null;
		Integer[] p3 = null;
		Integer[] p4 = null;
		Integer[] p5 = null;
		Integer[] p6 = null;
		if(nodeId != null) {
			Node node = nodeQuery.get(nodeId);
			// 如果node有子节点，会取出子节点的所有文章
			if(node.getChildren() != null && !node.getChildren().isEmpty()) {
				treeNumber = getNodeTreeNumberList(nodeIds).toArray(
						new String[nodeIds.length]);
				nodeIds = null;
			}
		}
		Sort defSort = new Sort(new Sort.Order(Direction.ASC, "priority"), 
				new Sort.Order(Direction.DESC, "publishDate"), new Sort.Order(Direction.DESC, "id"));
		Limitable limitable = new LimitRequest(offset, count, defSort);
		return query.findList(modelId, nodeIds, attrId,
				specialId, tagIds, siteId, mainNodeId, userId, viewGroupId,
				viewOrgId, treeNumber, specialTitle, tagName, priority,
				beginDate, endDate, title, includeId, excludeId,
				excludeMainNodeId, excludeTreeNumber, isWithImage, status,
				p1, p2, p3, p4, p5, p6, limitable);
	}
	
	private List<Info> doFullTextQuery(Integer offset, Integer count, String q) {
		Integer[] siteId = null;
		Integer[] nodeId = null;
		Date beginDate = null;
		Date endDate = null;
		String title = null;
		String[] keyword = null;
		String description = null;
		String text = null;
		String[] creator = null;
		String[] author = null;

		Integer[] excludeId = null;
		String[] status = new String[] { Info.NORMAL };
		Integer fragmentSize = null;
		org.apache.lucene.search.Sort sort = null;
		Limitable limitable = new LimitRequest(offset, count, null);
		List<Info> list = fulltext.list(siteId, nodeId, null, beginDate,
					endDate, status, excludeId, q, title, keyword, description,
					text, creator, author, fragmentSize, limitable, sort);
		// XXX 原框架代码有bug，并未对结果集进行分页，因此查出来的是符合条件的全部记录
		if(list.size() < offset) {
			return new ArrayList<Info>(0);
		}
		int endIndex = offset+count > list.size() ? list.size() : offset+count;
		return list.subList(offset, endIndex);
	}

	private List<String> getNodeTreeNumberList(Integer[] nodeList) {
		List<String> numbers = new ArrayList<String>();
		Node node;
		for (Integer nodeId : nodeList) {
			node = nodeQuery.get(nodeId);
			numbers.add(node.getTreeNumber());
		}
		return numbers;
	}
	
	@Autowired
	private NodeQueryService nodeQuery;
	@Autowired
	private InfoQueryService query;
	@Autowired
	private InfoFulltextService fulltext;
}

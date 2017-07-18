package com.simple.web.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simple.core.service.ArticleService;
import com.simple.web.controller.AbstractController;

/***
 * 文章查询接口
 * 
 * @Title: simple-web
 * @Description:文章查询接口
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月21日 下午4:28:18
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/web/article/")
public class ArticleController extends AbstractController {

	@Autowired
	private ArticleService articleService;

	/***
	 * 
	 * 根据ID查询
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get/{id}", method = { RequestMethod.GET })
	public Object get(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) {
		try {
			return success(articleService.getByPk(id));
		} catch (Throwable e) {
			return fail(e);
		}
	}

	/***
	 * 
	 * 根据租户ID，文章、类型查询
	 * 
	 * @param params
	 * @param tenantId 租户
	 * @param articleType 文章类型
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getlist/by/{tenantId}/for/{articleType}", method = { RequestMethod.POST })
	public Object getList(@RequestBody(required = false) Map<String, Object> params, @PathVariable("tenantId") Long tenantId, @PathVariable("articleType") Integer articleType, HttpServletRequest request, HttpServletResponse response) {
		params = null == params ? new HashMap<String, Object>() : params;
		try {
			params.put("tenantId", tenantId);
			params.put("articleType", articleType);
			return success(articleService.getpage(checkPageAndSize(cleanEmptyObject(params, true, null))));
		} catch (Throwable e) {
			return fail(e);
		}
	}

}

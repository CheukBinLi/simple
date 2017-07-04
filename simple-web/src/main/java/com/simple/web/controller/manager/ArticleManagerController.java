package com.simple.web.controller.manager;

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

import com.simple.core.model.LoginInfoModel;
import com.simple.core.service.ArticleService;
import com.simple.web.controller.AbstractController;

/***
 * 文章信息管理接口
 * 
 * @Title: simple-web
 * @Description:文章信息管理接口
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月21日 下午5:06:57
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/manager/article/")
public class ArticleManagerController extends AbstractController {

	@Autowired
	private ArticleService articleService;

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
			return success(articleService.getpage(checkPageAndSize(params)));
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "put", method = { RequestMethod.PUT })
	public Object put(@RequestBody(required = false) Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		params = null == params ? new HashMap<String, Object>() : params;
		try {
			LoginInfoModel loginInfoModel = getLoginInfo(request);
			articleService.saveOrUpdate(loginInfoModel.getUser().getTenantId(), params);
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = { RequestMethod.DELETE })
	public Object delete(@RequestBody(required = false) Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		params = null == params ? new HashMap<String, Object>() : params;
		try {
			articleService.delete(getLoginInfo(request).getUser().getTenantId(), params);
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}

}

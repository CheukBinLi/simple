package com.simple.web.controller.manager;

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

import com.simple.core.entity.Authority;
import com.simple.core.model.LoginInfoModel;
import com.simple.core.service.AuthorityService;
import com.simple.web.controller.AbstractController;

@Controller
@Scope("prototype")
@RequestMapping("/manager/authority/")
public class AuthorityController extends AbstractController {

	@Autowired
	private AuthorityService authorityService;

	@ResponseBody
	@RequestMapping(value = "get/{id}", method = { RequestMethod.GET })
	public Object get(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) {
		try {
			return success(authorityService.getByPk(id));
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "getlist", method = { RequestMethod.POST })
	public Object getList(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		try {
			return success(authorityService.getpage(checkPageAndSize(params)));
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "put", method = { RequestMethod.PUT })
	public Object put(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginInfoModel loginInfoModel = getLoginInfo(request);
			params.put("tenantId", loginInfoModel.getUser().getTenantId());
			authorityService.saveOrUpdate(fillObject(new Authority(), params));
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = { RequestMethod.DELETE })
	public Object delete(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		try {
			authorityService.delete(params);
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}

}

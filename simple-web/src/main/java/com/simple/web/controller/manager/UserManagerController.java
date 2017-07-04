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

import com.simple.core.entity.User;
import com.simple.core.model.LoginInfoModel;
import com.simple.core.service.UserService;
import com.simple.web.controller.AbstractController;

@Controller
@Scope("prototype")
@RequestMapping("/manager/user/")
public class UserManagerController extends AbstractController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "get/{id}", method = { RequestMethod.GET })
	public Object get(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) {
		try {
			return success(userService.getByPk(id));
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "getlist", method = { RequestMethod.POST })
	public Object getList(@RequestBody(required = false) Map<String, Object> params, HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) {
		params = null == params ? new HashMap<String, Object>() : params;
		try {
			return success(userService.getpage(checkPageAndSize(params)));
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
			User user = fillObject(new User(), params);
			user.setTenantId(loginInfoModel.getUser().getTenantId());
			userService.saveOrUpdate(user);
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}

	//	@ResponseBody
	//	@RequestMapping(value = "login", method = { RequestMethod.POST })
	//	public Object login(HttpServletRequest request, HttpServletResponse response, @RequestParam("userName") String userName, @RequestParam("password") String password) {
	//		try {
	//			User user = fillObject(new User(), params);
	//			user.setTenantId(loginInfoModel.getUser().getTenantId());
	//			userService.saveOrUpdate(user);
	//			return success();
	//		} catch (Throwable e) {
	//			return fail(e);
	//		}
	//	}

	@ResponseBody
	@RequestMapping(value = "delete", method = { RequestMethod.DELETE })
	public Object delete(@RequestBody(required = false) Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		params = null == params ? new HashMap<String, Object>() : params;
		try {
			userService.delete(params);
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}

}

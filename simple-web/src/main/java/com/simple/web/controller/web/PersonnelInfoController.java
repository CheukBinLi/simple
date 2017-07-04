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

import com.simple.core.service.PersonnelInfoService;
import com.simple.web.controller.AbstractController;

/***
 * 人员信息接口
 * 
 * @Title: simple-web
 * @Description: 人员信息接口
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月21日 下午4:57:35
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/web/personnelInfo/")
public class PersonnelInfoController extends AbstractController {

	@Autowired
	private PersonnelInfoService personnelInfoService;

	/***
	 * 根据ID查询人员信息
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
			return success(personnelInfoService.getByPk(id));
		} catch (Throwable e) {
			return fail(e);
		}
	}

	/***
	 * 根据租户查询人员信息列表
	 * 
	 * @param params 附加：各种条件参数
	 * @param tenantId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getlist/by/{tenantId}", method = { RequestMethod.POST })
	public Object getList(@RequestBody(required = false) Map<String, Object> params, @PathVariable("tenantId") Long tenantId, HttpServletRequest request, HttpServletResponse response) {
		params = null == params ? new HashMap<String, Object>() : params;
		try {
			params.put("tenantId", tenantId);
			return success(personnelInfoService.getpage(checkPageAndSize(params)));
		} catch (Throwable e) {
			return fail(e);
		}
	}
}

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

import com.simple.core.entity.Appointment;
import com.simple.core.service.AppointmentService;
import com.simple.web.controller.AbstractController;

/***
 * 
 * 预约接口
 * 
 * @Title: simple-web
 * @Description: 预约接口
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月21日 下午3:26:41
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/web/appointment/")
public class AppointmentController extends AbstractController {

	@Autowired
	private AppointmentService appointmentService;

	/***
	 * 预约查询接口:(根据ID)
	 * 
	 * @param request
	 * @param responses
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Object get(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) {
		try {
			Appointment appointment = appointmentService.getByPk(id);
			return success(appointment);
		} catch (Throwable e) {
			return fail(e);
		}
	}

	/***
	 * 根据租户ID查询预约
	 * 
	 * @param params 附加:各种查询条件(驼峰参数名)
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
			return success(appointmentService.getpage(checkPageAndSize(params)));
		} catch (Throwable e) {
			return fail(e);
		}
	}

	/***
	 * 添加预约
	 * 
	 * @param params
	 * @param tenantId 租户ID
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "{tenantId}/put", method = { RequestMethod.PUT })
	public Object put(@RequestBody(required = false) Map<String, Object> params, @PathVariable("tenantId") Long tenantId, HttpServletRequest request, HttpServletResponse response) {
		params = null == params ? new HashMap<String, Object>() : params;
		try {
			appointmentService.saveOrUpdate(tenantId, params);
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}

}

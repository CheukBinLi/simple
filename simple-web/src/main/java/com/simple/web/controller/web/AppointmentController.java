package com.simple.web.controller.web;

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

@Controller
@Scope("prototype")
@RequestMapping("/web/appointment/")
public class AppointmentController extends AbstractController {

	@Autowired
	private AppointmentService appointmentService;

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

	@ResponseBody
	@RequestMapping(value = "getlist/by/{tenantId}", method = { RequestMethod.POST })
	public Object getList(@RequestBody Map<String, Object> params, @PathVariable("tenantId") Long tenantId, HttpServletRequest request, HttpServletResponse response) {
		try {
			params.put("tenantId", tenantId);
			return success(appointmentService.getpage(checkPageAndSize(params)));
		} catch (Throwable e) {
			return fail(e);
		}
	}

}

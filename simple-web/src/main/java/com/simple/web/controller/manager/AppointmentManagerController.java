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

import com.simple.core.entity.Appointment;
import com.simple.core.model.LoginInfoModel;
import com.simple.core.service.AppointmentService;
import com.simple.web.controller.AbstractController;

/***
 * 预约管理接口
 * 
 * @Title: simple-web
 * @Description:预约管理接口
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月21日 下午4:59:08
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/manager/appointment/")
public class AppointmentManagerController extends AbstractController {

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
            return success(appointmentService.getpage(checkPageAndSize(checkDateTimeObject(params, null,true))));
        } catch (Throwable e) {
            return fail(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "put", method = { RequestMethod.PUT })
    public Object put(@RequestBody(required = false) Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        params = null == params ? new HashMap<String, Object>() : params;
        LoginInfoModel loginInfoModel = getLoginInfo(request);
        try {
            appointmentService.saveOrUpdate(loginInfoModel.getUser().getTenantId(), params);
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
            appointmentService.delete(getLoginInfo(request).getUser().getTenantId(), params);
            return success();
        } catch (Throwable e) {
            return fail(e);
        }
    }

}

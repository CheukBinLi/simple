package com.simple.web.controller.manager;

import java.util.List;
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
import com.simple.core.service.PersonnelInfoService;
import com.simple.web.controller.AbstractController;

@Controller
@Scope("prototype")
@RequestMapping("/manager/personnelInfo/")
public class PersonnelInfoController extends AbstractController {

	@Autowired
	private PersonnelInfoService personnelInfoService;

	@ResponseBody
	@RequestMapping(value = "get/{id}", method = { RequestMethod.GET })
	public Object get(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) {
		try {
			return success(personnelInfoService.getByPk(id));
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "getlist/by/{tenantId}", method = { RequestMethod.POST })
	public Object getList(@RequestBody Map<String, Object> params, @PathVariable("tenantId") Long tenantId, HttpServletRequest request, HttpServletResponse response) {
		try {
			params.put("tenantId", tenantId);
			return success(personnelInfoService.getpage(checkPageAndSize(params)));
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "put", method = { RequestMethod.PUT })
	public Object put(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginInfoModel loginInfoModel = getLoginInfo(request);
			boolean isUpload;
			String savePath = (isUpload = params.containsKey("id")) ? params.get("pic").toString() : String.format("upload/%d/%d", loginInfoModel.getUser().getTenantId(), loginInfoModel.getUser().getTenantId());
			List<String> uploadFile = uploadFile(request, savePath, isUpload);
			params.put("pic", uploadFile.get(0));//第0张图片
			personnelInfoService.saveOrUpdate(loginInfoModel.getUser().getTenantId(), params);
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = { RequestMethod.DELETE })
	public Object delete(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		try {
			personnelInfoService.delete(getLoginInfo(request).getUser().getTenantId(), params);
			return success();
		} catch (Throwable e) {
			return fail(e);
		}
	}
}

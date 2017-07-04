package com.simple.web.controller.manager;

import java.util.HashMap;
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

import com.cheuks.bin.original.common.web.common.model.UploadFileModel;
import com.simple.core.model.LoginInfoModel;
import com.simple.core.service.PersonnelInfoService;
import com.simple.web.controller.AbstractController;

/***
 * 人员信息管理接口
 * 
 * @Title: simple-web
 * @Description:人员信息管理接口
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月21日 下午5:06:33
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/manager/personnelInfo/")
public class PersonnelInfoManagerController extends AbstractController {

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
    public Object getList(@RequestBody(required = false) Map<String, Object> params, @PathVariable("tenantId") Long tenantId, HttpServletRequest request, HttpServletResponse response) {
        params = null == params ? new HashMap<String, Object>() : params;
        try {
            params.put("tenantId", tenantId);
            return success(personnelInfoService.getpage(checkPageAndSize(params)));
        } catch (Throwable e) {
            return fail(e);
        }
    }

    /***
     * 添加人员信息
     * 
     * @param params
     *            更新时必传：id/pic
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "put", method = { RequestMethod.POST })
    public Object put(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getParams(request);
        try {
            LoginInfoModel loginInfoModel = getLoginInfo(request);
            boolean isUpload;
            if (params.containsKey("id") && !params.containsKey("pic")) {
                return fail("can't found pic param.", null);
            }
            String savePath = (isUpload = params.containsKey("id")) ? params.get("pic").toString() : String.format("upload/personnelInfo/%d", loginInfoModel.getUser().getTenantId());
            List<UploadFileModel> uploadFile = uploadFile(request, savePath, isUpload);
            params.put("pic", uploadFile.get(0).getPath());// 第0张图片
            personnelInfoService.saveOrUpdate(loginInfoModel.getUser().getTenantId(), params);
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
            params.put(REAL_PATH, getRealPath(request));
            personnelInfoService.delete(getLoginInfo(request).getUser().getTenantId(), params);
            return success();
        } catch (Throwable e) {
            return fail(e);
        }
    }
}

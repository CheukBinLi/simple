package com.simple.web.controller.web;

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
import com.simple.core.service.GalleryService;
import com.simple.web.controller.AbstractController;

/***
 * 图库接口
 * 
 * @Title: simple-web
 * @Description: 图库接口
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月21日 下午4:57:35
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/web/gallery/")
public class GalleryController extends AbstractController {

    @Autowired
    private GalleryService galleryService;

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
            return success(galleryService.getByPk(id));
        } catch (Throwable e) {
            return fail(e);
        }
    }

    /***
     * 根据租户查询人员信息列表
     * 
     * @param params
     *            附加：各种条件参数
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
            return success(galleryService.getpage(checkPageAndSize(params)));
        } catch (Throwable e) {
            return fail(e);
        }
    }

    /***
     * 添加图片列表
     * 
     * @param params
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "put", method = { RequestMethod.POST })
    public Object put(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoginInfoModel loginInfoModel = getLoginInfo(request);
            String savePath = String.format("upload/gallery/%d", loginInfoModel.getUser().getTenantId());
            List<UploadFileModel> uploadFiles = uploadFile(request, savePath, false);
            galleryService.save(loginInfoModel.getUser().getTenantId(), uploadFiles);
            return success();
        } catch (Throwable e) {
            return fail(e);
        }
    }

    /***
     * 添加图片列表
     * 
     * @param params 必传参数：id/path
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public Object update(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getParams(request);
        if (!params.containsKey("id")) {
            return fail("can't found id param", null);
        }
        if (!params.containsKey("path")) {
            return fail("can't found path param.", null);
        }
        try {
            LoginInfoModel loginInfoModel = getLoginInfo(request);

            List<UploadFileModel> uploadFiles = uploadFile(request, params.get("path").toString(), true);
            UploadFileModel uploadFile = uploadFiles.get(0);
            params.put("id", Long.valueOf(params.get("id").toString()));
            params.put("name", uploadFile.getName());
            params.put("size", uploadFile.getSize());
            params.put("path", uploadFile.getPath());
            galleryService.update(loginInfoModel.getUser().getTenantId(), params);
            return success();
        } catch (Throwable e) {
            return fail(e);
        }
    }
}

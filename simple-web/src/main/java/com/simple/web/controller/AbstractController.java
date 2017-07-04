package com.simple.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.MultiValueMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.cheuks.bin.original.cache.FstCacheSerialize;
import com.cheuks.bin.original.common.cache.CacheSerialize;
import com.cheuks.bin.original.common.util.JsonMsgModel;
import com.cheuks.bin.original.common.util.ObjectFill;
import com.cheuks.bin.original.common.web.common.controller.BaseController;
import com.cheuks.bin.original.common.web.common.model.UploadFileModel;
import com.simple.core.model.LoginInfoModel;
import com.simple.core.util.SimpleConstant;

public abstract class AbstractController extends ObjectFill implements SimpleConstant, ApplicationContextAware, BaseController<HttpServletRequest, HttpServletResponse, JsonMsgModel, ModelAndView> {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractController.class);

    private static volatile CacheSerialize cacheSerialize;

    private static volatile RequestMappingHandlerMapping requestMappingHandlerMapping;

    private static volatile String pageNumber = "pageNumber";

    private static volatile String pageSize = "pageSize";

    private static volatile boolean isInit;

    public AbstractController setCacheSerialize(CacheSerialize cacheSerialize) {
        AbstractController.cacheSerialize = cacheSerialize;
        return this;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (!isInit) {
            synchronized (AbstractController.class) {
                if (isInit) {
                    return;
                }
                isInit = true;
                cacheSerialize = applicationContext.containsBean("cacheSerialize") ? (CacheSerialize) applicationContext.getBean("cacheSerialize") : new FstCacheSerialize();
                requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
                Map<RequestMappingInfo, HandlerMethod> mapping = requestMappingHandlerMapping.getHandlerMethods();
                for (Entry<RequestMappingInfo, HandlerMethod> en : mapping.entrySet()) {
                    System.err.println("key:" + en.getKey() + " value:" + en.getKey());
                }
            }
        }
    }

    /***
     * 获取用户信息
     * 
     * @param request
     * @return
     */
    protected LoginInfoModel getLoginInfo(HttpServletRequest request) {
        Object attr = request.getSession().getAttribute(SESSION_LOGIN_INFO);
        return null == attr ? null : (LoginInfoModel) attr;
        // try {
        // if (null != attr) return null;
        // return (LoginInfoModel) cacheSerialize.decode((byte[]) attr);
        // } catch (CacheException e) {
        // LOG.error(null, e);
        // }
        // return null;
    }

    /***
     * 添加用户信息
     * 
     * @param request
     * @param loginInfoModel
     * @return
     */
    protected boolean setLoginInfo(HttpServletRequest request, LoginInfoModel loginInfoModel) {
        // request.getSession().setAttribute(SESSION_LOGIN_INFO,
        // cacheSerialize.encode(loginInfoModel));
        request.getSession().setAttribute(SESSION_LOGIN_INFO, loginInfoModel);
        return true;
    }

    protected final Map<String, Object> getParams(HttpServletRequest request) {
        Enumeration<String> en = request.getParameterNames();
        Map<String, Object> map = new HashMap<String, Object>();
        String name;
        while (en.hasMoreElements()) {
            name = en.nextElement();
            map.put(name, request.getParameter(name));
        }
        return map;
    }

    protected Map<String, Object> checkPageAndSize(HttpServletRequest request) {
        return checkPageAndSize(getParams(request));
    }

    public ModelAndView redirect(String url, Map<String, Object> params) {
        return new ModelAndView("redirect:/" + url + paramsToUrl(params));
    }

    public ModelAndView forward(String url, Map<String, Object> params) {
        return new ModelAndView("forward:/" + url).addAllObjects(params);
    }

    protected Map<String, Object> checkPageAndSize(final Map<String, Object> params) {
        if (!params.containsKey(pageNumber)) {
            params.put(pageNumber, -1);
            params.put(pageSize, -1);
        }
        return params;
    }

    public String getRealPath(HttpServletRequest request) {
        return request.getServletContext().getRealPath("/");
    }

    protected List<UploadFileModel> uploadFile(HttpServletRequest request, String savePath, boolean isUpdate) throws IllegalStateException, IOException {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        String realPath = getRealPath(request);
        List<UploadFileModel> result = null;
        File file;
        if (commonsMultipartResolver.isMultipart(request)) {
            result = new ArrayList<UploadFileModel>();
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> multiValueMap = multiRequest.getMultiFileMap();
            if (isUpdate && multiValueMap.size() > 1) {
                throw new RuntimeException("the upload file size more then one.");
            }
            List<MultipartFile> fileList;

            if (null == multiValueMap)
                return result;
            Iterator<String> it = multiValueMap.keySet().iterator();
            String fileName = null;
            while (it.hasNext()) {
                fileList = multiValueMap.get(it.next());
                if (null == fileList) {
                    continue;
                } else if (fileList.size() > 1) {
                    throw new RuntimeException("the upload file size more then one.");
                }
                for (MultipartFile multipartFile : fileList) {
                    if (multipartFile.getSize() < 1)
                        continue;
                    if (isUpdate) {
                        file = new File(realPath + "/" + savePath);
                        file.delete();
                        savePath = savePath.substring(0, savePath.lastIndexOf("/"));
                    } else {
                        fileName = multipartFile.getOriginalFilename();
                        fileName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    }
                    fileName = String.format("%s/%d%s", savePath, System.currentTimeMillis(), fileName);
                    file = new File(realPath + "/" + fileName);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    result.add(new UploadFileModel(multipartFile.getOriginalFilename(), multipartFile.getSize(), fileName));
                    file.createNewFile();
                    multipartFile.transferTo(file);
                }
            }
        }
        return result;
    }

    public JsonMsgModel fail(String msg, Throwable e) {
        if (null != e)
            LOG.error(null, e);
        return new JsonMsgModel(-1, msg);
    }

    public JsonMsgModel fail(Throwable e) {
        LOG.error(null, e);
        return new JsonMsgModel(-1, e.getMessage());
    }

    public JsonMsgModel fail() {
        return new JsonMsgModel(-1, "fail");
    }

    public JsonMsgModel success(String msg, Object data, Object attachment) {
        return new JsonMsgModel(0, msg, data, attachment);
    }

    public JsonMsgModel success(Object data) {
        return success("success", data, null);
    }

    public JsonMsgModel success() {
        return success(null, null, null);
    }

    public ModelAndView exceptionPage(Throwable e) {
        return exceptionPage("操作失败", e);
    }

    protected ModelAndView exceptionPage(String msg, Throwable e) {
        if (null != e)
            LOG.error(null, e);
        return new ModelAndView("forward:/error");
    }

    public static String getPageNumber() {
        return pageNumber;
    }

    public AbstractController setPageNumber(String pageNumber) {
        AbstractController.pageNumber = pageNumber;
        return this;
    }

    public static String getPageSize() {
        return pageSize;
    }

    public AbstractController setPageSize(String pageSize) {
        AbstractController.pageSize = pageSize;
        return this;
    }

    public CacheSerialize getCacheSerialize() {
        return cacheSerialize;
    }

    protected String paramsToUrl(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("?");
        for (Entry<String, Object> en : params.entrySet()) {
            sb.append(en.getKey()).append("=").append(en.getValue()).append("&");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}

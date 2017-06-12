package com.simple.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cheuks.bin.original.cache.FstCacheSerialize;
import com.cheuks.bin.original.common.cache.CacheSerialize;
import com.cheuks.bin.original.common.util.JsonMsgModel;
import com.cheuks.bin.original.common.util.ObjectFill;
import com.simple.core.model.LoginInfoModel;
import com.simple.core.util.SimpleConstant;

public abstract class AbstractController extends ObjectFill implements SimpleConstant, ApplicationContextAware {

	private final static Logger LOG = LoggerFactory.getLogger(AbstractController.class);

	private static volatile CacheSerialize cacheSerialize;

	private static volatile String pageNumber = "pageNumber";
	private static volatile String pageSize = "pageSize";

	public AbstractController setCacheSerialize(CacheSerialize cacheSerialize) {
		AbstractController.cacheSerialize = cacheSerialize;
		return this;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (null == cacheSerialize) cacheSerialize = new FstCacheSerialize();
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
		//		try {
		//			if (null != attr) return null;
		//			return (LoginInfoModel) cacheSerialize.decode((byte[]) attr);
		//		} catch (CacheException e) {
		//			LOG.error(null, e);
		//		}
		//		return null;
	}

	/***
	 * 添加用户信息
	 * 
	 * @param request
	 * @param loginInfoModel
	 * @return
	 */
	protected boolean setLoginInfo(HttpServletRequest request, LoginInfoModel loginInfoModel) {
		//			request.getSession().setAttribute(SESSION_LOGIN_INFO, cacheSerialize.encode(loginInfoModel));
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

	protected Map<String, Object> checkPageAndSize(final Map<String, Object> params) {
		if (!params.containsKey(pageNumber)) {
			params.put(pageNumber, -1);
			params.put(pageSize, -1);
		}
		return params;
	}

	protected List<String> uploadFile(HttpServletRequest request, String savePath, boolean isUpdate) throws IllegalStateException, IOException {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		String realPath = request.getServletContext().getRealPath("/");
		List<String> result = null;
		File file;
		if (commonsMultipartResolver.isMultipart(request)) {
			result = new ArrayList<String>();
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multiRequest.getFileNames();
			MultipartFile multipartFile;
			String fileName = null;
			while (it.hasNext()) {
				multipartFile = multiRequest.getFile(it.next());
				if (multipartFile.getSize() < 1) continue;
				if (!isUpdate) {
					fileName = multipartFile.getOriginalFilename();
					fileName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
					fileName = String.format("%s/%d%s", savePath, System.currentTimeMillis(), fileName);
					result.add(fileName);
					file = new File(realPath + "/" + fileName);
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
				} else {
					file = new File(savePath);
				}
				file.createNewFile();
				multipartFile.transferTo(file);
			}
		}
		return result;
	}

	protected JsonMsgModel fail(String msg, Throwable e) {
		if (null != e) LOG.error(null, e);
		return new JsonMsgModel(-1, msg);
	}

	protected JsonMsgModel fail(Throwable e) {
		LOG.error(null, e);
		return new JsonMsgModel(-1, e.getMessage());
	}

	protected JsonMsgModel fail() {
		return new JsonMsgModel(-1, "fail");
	}

	protected JsonMsgModel success(String msg, Object data, Object attachment) {
		return new JsonMsgModel(0, msg, data, attachment);
	}

	protected JsonMsgModel success(Object data) {
		return success("success", data, null);
	}

	protected JsonMsgModel success() {
		return success(null, null, null);
	}

	protected ModelAndView exceptionPage(Throwable e) {
		return exceptionPage("操作失败", e);
	}

	protected ModelAndView exceptionPage(String msg, Throwable e) {
		if (null != e) LOG.error(null, e);
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

}

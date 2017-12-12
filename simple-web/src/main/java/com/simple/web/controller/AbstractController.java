package com.simple.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.cheuks.bin.original.cache.FstCacheSerialize;
import com.cheuks.bin.original.common.cache.CacheSerialize;
import com.cheuks.bin.original.common.dbmanager.LogicStatus;
import com.cheuks.bin.original.common.web.common.model.UploadFileModel;
import com.simple.core.model.LoginInfoModel;
import com.simple.core.util.SimpleConstant;

@SuppressWarnings("unused")
public abstract class AbstractController extends com.cheuks.bin.original.web.common.controller.AbstractController<ModelAndView> implements SimpleConstant, ApplicationContextAware {

	private final static Logger LOG = LoggerFactory.getLogger(AbstractController.class);

	private static volatile CacheSerialize cacheSerialize;

	private static volatile RequestMappingHandlerMapping requestMappingHandlerMapping;

	private static volatile String pageNumber = "pageNumber";

	private static volatile String pageSize = "pageSize";

	private static volatile boolean isInit;

	private static volatile String uploadPath;

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

				if (null == uploadPath)
					uploadPath = (String) applicationContext.getBean("updatePath");
				// requestMappingHandlerMapping =
				// applicationContext.getBean(RequestMappingHandlerMapping.class);
				// Map<RequestMappingInfo, HandlerMethod> mapping =
				// requestMappingHandlerMapping.getHandlerMethods();
				// for (Entry<RequestMappingInfo, HandlerMethod> en :
				// mapping.entrySet()) {
				// System.err.println("key:" + en.getKey() + " value:" +
				// en.getKey());
				// }
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
		if (!params.containsKey("logicStatus")) {
			params.put("logicStatus", LogicStatus.NORMAL);
		}
		return params;
	}

	public String getRealPath(HttpServletRequest request) {
		return request.getServletContext().getRealPath("/");
	}

	protected List<UploadFileModel> uploadFile(HttpServletRequest request, String savePath, boolean isUpdate) throws IllegalStateException, IOException {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		String realPath = null == getUploadPath() ? getRealPath(request) : getUploadPath();
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
				} else if (isUpdate && fileList.size() > 1) {
					throw new RuntimeException("the upload file size more then one.");
				}
				for (MultipartFile multipartFile : fileList) {
					if (multipartFile.getSize() < 1)
						continue;
					if (isUpdate && !savePath.isEmpty()) {
						file = new File(realPath + "/" + savePath);
						if (file.exists())
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

	protected Map<String, Object> checkDateTimeObject(Map<String, Object> params, String dateformat, boolean cleanNull) throws ParseException {
		if (null == params || params.isEmpty())
			return params;
		// final String reg =
		// "^(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])
		// (0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}:([0-5]\\d{1})$";
		// final SimpleDateFormat format = new SimpleDateFormat(null ==
		// dateformat ? "yyyy-MM-dd HH:mm:ss" : dateformat);
		final String reg = "^(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])$";
		final SimpleDateFormat format = new SimpleDateFormat(null == dateformat ? "yyyy-MM-dd" : dateformat);
		return cleanEmptyObject(params, cleanNull, new CheckProcessing() {

			public Entry<String, Object> processing(Entry<String, Object> en) {
				Object tempValue;
				String value;
				value = null == (tempValue = en.getValue()) ? null : tempValue.toString();
				if (value.matches(reg)) {
					try {
						en.setValue(format.parse(value));
						return en;
					} catch (ParseException e) {
						LOG.error(null, e);
					}
				}
				return en;
			}
		});
	}

	protected Map<String, Object> cleanEmptyObject(Map<String, Object> params, boolean cleanNull, CheckProcessing checkProcessing) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null == params || params.isEmpty())
			return result;
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		Entry<String, Object> en;
		Object tempValue;
		String value;
		while (it.hasNext()) {
			en = it.next();
			tempValue = en.getValue();
			if (null != tempValue && (value = tempValue.toString()).length() > 0) {
				if (cleanNull && ("null".equals(value) || null == value)) {
					continue;
				}
				if (null != checkProcessing)
					en = checkProcessing.processing(en);
				if (null != en) {
					result.put(en.getKey(), en.getValue());
				}
			}
		}
		return result;
	}

	interface CheckProcessing {
		Entry<String, Object> processing(Entry<String, Object> en);
	}

	public static String getUploadPath() {
		return uploadPath;
	}

	public AbstractController setUploadPath(String uploadPath) {
		AbstractController.uploadPath = uploadPath;
		return this;
	}

	public static void main(String[] args) {
		String reg = "^(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01]) (0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}:([0-5]\\d{1})$";
		System.out.println("2019-01-03 12:31:33".matches(reg));
	}
}

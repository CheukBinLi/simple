package com.simple.impl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.cheuks.bin.original.common.util.CollectionUtil;
import com.simple.core.dao.ArticleDao;
import com.simple.core.entity.Article;
import com.simple.core.service.ArticleService;

@Component
public class ArticleServiceImpl extends AbstractService<Article, Long> implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Override
	public BaseDao<Article, Long> getDao() {
		return articleDao;
	}

	public void saveOrUpdate(Long tenantId, Map<String, Object> params) throws Throwable {
		params.put("tenantId", tenantId);
		Article article;
		if (params.containsKey("id")) {
			Map<String, Object> tempParams = CollectionUtil.newInstance().toMap(true, new Object[] { "id", params.remove("id"), "tenantId", tenantId });
			List<Article> list = getList(tempParams);
			if (null != list && list.size() == 1) {
				article = list.get(0);
				article = fillObject(article, params);
				articleDao.update(article);
				return;
			}
			throw new RuntimeException("can't found data for id is " + tempParams.get("id") + " and tenantId is " + tenantId);
		}
		articleDao.save(fillObject(new Article().setId(generateId()), params));
	}

	public void update(Long tenantId, Map<String, Object> params) throws Throwable {
		Article article = articleDao.get((Long) params.get("id"));
		if (null != article && article.getTenantId() == tenantId) {
			articleDao.update(fillObject(article, params));
		}
		throw new Throwable("更新失败：没找到信息");
	}

	public void delete(Long tenantId, Map<String, Object> params) throws Throwable {
		if (null != params.get("id") && !deleteLogic(params)) { throw new Throwable("操作失败"); }
	}

}

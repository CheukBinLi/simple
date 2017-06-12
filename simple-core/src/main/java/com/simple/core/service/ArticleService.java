package com.simple.core.service;

import com.simple.core.entity.Article;

import java.util.Map;

import com.cheuks.bin.original.common.dbmanager.service.BaseService;

public interface ArticleService extends BaseService<Article, Long> {

	void update(Long tenantId, Map<String, Object> params) throws Throwable;

	void delete(Long tenantId, Map<String, Object> params) throws Throwable;

	void saveOrUpdate(Long tenantId, Map<String, Object> params) throws Throwable;
}

package com.simple.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.DBAdapter;
import com.cheuks.bin.original.common.dbmanager.dao.AbstractDao;
import com.simple.core.dao.ArticleDao;
import com.simple.core.entity.Article;

@Component
public class ArticleDaoImpl extends AbstractDao<Article, Long> implements ArticleDao {

	@Autowired
	private DBAdapter<?> dBAdapter;

	@Override
	public Class<Article> getEntityClass() {
		return Article.class;
	}

	@Override
	public DBAdapter<?> getDBAdapter() {
		return dBAdapter;
	}

}

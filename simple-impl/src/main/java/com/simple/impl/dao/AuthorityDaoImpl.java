package com.simple.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.DBAdapter;
import com.cheuks.bin.original.common.dbmanager.dao.AbstractDao;
import com.simple.core.dao.AuthorityDao;
import com.simple.core.entity.Authority;

@Component
public class AuthorityDaoImpl extends AbstractDao<Authority, Long> implements AuthorityDao {

	@Autowired
	private DBAdapter<?> dBAdapter;

	@Override
	public Class<Authority> getEntityClass() {
		return Authority.class;
	}

	@Override
	public DBAdapter<?> getDBAdapter() {
		return dBAdapter;
	}

}

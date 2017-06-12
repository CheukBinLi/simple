package com.simple.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.DBAdapter;
import com.cheuks.bin.original.common.dbmanager.dao.AbstractDao;
import com.simple.core.dao.AuthorityGroupDao;
import com.simple.core.entity.AuthorityGroup;

@Component
public class AuthorityGroupDaoImpl extends AbstractDao<AuthorityGroup, Long> implements AuthorityGroupDao {

	@Autowired
	private DBAdapter<?> dBAdapter;

	@Override
	public Class<AuthorityGroup> getEntityClass() {
		return AuthorityGroup.class;
	}

	@Override
	public DBAdapter<?> getDBAdapter() {
		return dBAdapter;
	}

}

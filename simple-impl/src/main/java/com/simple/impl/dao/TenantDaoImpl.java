package com.simple.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.DBAdapter;
import com.cheuks.bin.original.common.dbmanager.dao.AbstractDao;
import com.simple.core.dao.TenantDao;
import com.simple.core.entity.Tenant;

@Component
public class TenantDaoImpl extends AbstractDao<Tenant, Long> implements TenantDao {

	@Autowired
	private DBAdapter<?> dBAdapter;

	@Override
	public Class<Tenant> getEntityClass() {
		return Tenant.class;
	}

	@Override
	public DBAdapter<?> getDBAdapter() {
		return dBAdapter;
	}

}

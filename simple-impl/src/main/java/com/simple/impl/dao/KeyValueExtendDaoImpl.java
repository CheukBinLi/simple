package com.simple.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.DBAdapter;
import com.cheuks.bin.original.common.dbmanager.dao.AbstractDao;
import com.simple.core.dao.KeyValueExtendDao;
import com.simple.core.entity.KeyValueExtend;

@Component
public class KeyValueExtendDaoImpl extends AbstractDao<KeyValueExtend, Long> implements KeyValueExtendDao {

	@Autowired
	private DBAdapter<?> dBAdapter;

	@Override
	public Class<KeyValueExtend> getEntityClass() {
		return KeyValueExtend.class;
	}

	@Override
	public DBAdapter<?> getDBAdapter() {
		return dBAdapter;
	}

}

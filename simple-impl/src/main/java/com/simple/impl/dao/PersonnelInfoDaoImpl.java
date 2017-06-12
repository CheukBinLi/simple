package com.simple.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.DBAdapter;
import com.cheuks.bin.original.common.dbmanager.dao.AbstractDao;
import com.simple.core.dao.PersonnelInfoDao;
import com.simple.core.entity.PersonnelInfo;

@Component
public class PersonnelInfoDaoImpl extends AbstractDao<PersonnelInfo, Long> implements PersonnelInfoDao {

	@Autowired
	private DBAdapter<?> dBAdapter;

	@Override
	public Class<PersonnelInfo> getEntityClass() {
		return PersonnelInfo.class;
	}

	@Override
	public DBAdapter<?> getDBAdapter() {
		return dBAdapter;
	}

}

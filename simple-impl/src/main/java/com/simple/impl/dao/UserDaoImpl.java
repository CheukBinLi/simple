package com.simple.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.DBAdapter;
import com.cheuks.bin.original.common.dbmanager.dao.AbstractDao;
import com.simple.core.dao.UserDao;
import com.simple.core.entity.User;

@Component
public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

	@Autowired
	private DBAdapter<?> dBAdapter;

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	public DBAdapter<?> getDBAdapter() {
		return dBAdapter;
	}

}

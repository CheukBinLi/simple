package com.simple.impl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.simple.core.dao.UserDao;
import com.simple.core.entity.User;
import com.simple.core.service.UserService;

@Component
public class UserServiceImpl extends AbstractService<User, Long> implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public BaseDao<User, Long> getDao() {
		return userDao;
	}

	public void saveOrUpdate(Long tenantId, Map<String, Object> params) throws Throwable {
		params.put("tenantId", tenantId);
		User user;
		if (params.containsKey("id")) ;
		{
			List<User> list = getList(params);
			if (null != list && list.size() == 1) {
				user = list.get(0);
				user = fillObject(user, params);
				userDao.update(user);
			}
		}
		userDao.save(fillObject(new User(), params));
	}

	public void delete(Map<String, Object> params) throws Throwable {
		if (params.containsKey("id")) {
			super.delete(new User((Long) params.get("id")));
		}
		throw new RuntimeException("can't found pk field.");
	}

}

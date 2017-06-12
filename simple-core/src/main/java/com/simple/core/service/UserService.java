package com.simple.core.service;

import java.util.Map;

import com.cheuks.bin.original.common.dbmanager.service.BaseService;
import com.simple.core.entity.User;

public interface UserService extends BaseService<User, Long> {

	void delete(Map<String, Object> params) throws Throwable;

}

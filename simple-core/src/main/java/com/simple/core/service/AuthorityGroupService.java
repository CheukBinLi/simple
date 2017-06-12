package com.simple.core.service;

import com.simple.core.entity.AuthorityGroup;

import java.util.Map;

import com.cheuks.bin.original.common.dbmanager.service.BaseService;

public interface AuthorityGroupService extends BaseService<AuthorityGroup, Long> {

	void delete(Map<String, Object> params) throws Throwable;

}

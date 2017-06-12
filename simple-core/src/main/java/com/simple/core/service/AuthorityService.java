package com.simple.core.service;

import com.simple.core.entity.Authority;

import java.util.Map;

import com.cheuks.bin.original.common.dbmanager.service.BaseService;

public interface AuthorityService extends BaseService<Authority, Long> {

	void delete(Map<String, Object> params) throws Throwable;

}

package com.simple.core.service;

import com.simple.core.entity.PersonnelInfo;

import java.util.Map;

import com.cheuks.bin.original.common.dbmanager.service.BaseService;

public interface PersonnelInfoService extends BaseService<PersonnelInfo, Long> {

	PersonnelInfo getByPk(Long tenantId, Long id) throws Throwable;

	void update(Long tenantId, Map<String, Object> params) throws Throwable;

	void delete(Long tenantId, Map<String, Object> params) throws Throwable;

	void saveOrUpdate(Long tenantId, Map<String, Object> params) throws Throwable;
}

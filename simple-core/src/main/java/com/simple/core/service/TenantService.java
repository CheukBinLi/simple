package com.simple.core.service;

import java.util.Map;

import com.cheuks.bin.original.common.dbmanager.service.BaseService;
import com.simple.core.entity.Tenant;

public interface TenantService extends BaseService<Tenant, Long> {

	void delete(Map<String, Object> params) throws Throwable;

}

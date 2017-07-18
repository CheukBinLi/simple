package com.simple.impl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.cheuks.bin.original.common.util.CollectionUtil;
import com.simple.core.dao.TenantDao;
import com.simple.core.entity.Tenant;
import com.simple.core.service.TenantService;

@Component
public class TenantServiceImpl extends AbstractService<Tenant, Long> implements TenantService {

	@Autowired
	private TenantDao tenantDao;

	@Override
	public BaseDao<Tenant, Long> getDao() {
		return tenantDao;
	}

	public void saveOrUpdate(Long tenantId, Map<String, Object> params) throws Throwable {
		Tenant tenant;
		if (params.containsKey("id")) {
			Map<String, Object> tempParams = CollectionUtil.newInstance().toMap(true, new Object[] { "id", Long.valueOf(params.remove("id").toString()) });
			List<Tenant> list = getList(tempParams);
			if (null != list && list.size() == 1) {
				tenant = list.get(0);
				tenant = fillObject(tenant, params);
				tenantDao.update(tenant);
				return;
			}
			throw new RuntimeException("can't found data for id is " + tempParams.get("id") + " and tenantId is " + tenantId);
		}
		tenantDao.save(fillObject(new Tenant().setId(generateId()), params));
	}

	public void delete(Map<String, Object> params) throws Throwable {
		if (params.containsKey("id")) {
			super.delete(new Tenant(Long.valueOf( params.get("id").toString())));
		}
		throw new RuntimeException("can't found pk field.");
	}
}

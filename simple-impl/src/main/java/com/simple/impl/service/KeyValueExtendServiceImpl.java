package com.simple.impl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.simple.core.dao.KeyValueExtendDao;
import com.simple.core.entity.KeyValueExtend;
import com.simple.core.service.KeyValueExtendService;

@Component
public class KeyValueExtendServiceImpl extends AbstractService<KeyValueExtend, Long> implements KeyValueExtendService {

	@Autowired
	private KeyValueExtendDao keyValueExtendDao;

	@Override
	public BaseDao<KeyValueExtend, Long> getDao() {
		return keyValueExtendDao;
	}

	public void saveOrUpdate(Long tenantId, Map<String, Object> params) throws Throwable {
		params.put("tenantId", tenantId);
		KeyValueExtend keyValueExtend;
		if (params.containsKey("id")) ;
		{
			List<KeyValueExtend> list = getList(params);
			if (null != list && list.size() == 1) {
				keyValueExtend = list.get(0);
				keyValueExtend = fillObject(keyValueExtend, params);
				keyValueExtendDao.update(keyValueExtend);
			}
		}
		keyValueExtendDao.save(fillObject(new KeyValueExtend(), params));
	}

	public void update(Long tenantId, Map<String, Object> params) throws Throwable {
		KeyValueExtend keyValueExtend = keyValueExtendDao.get((Long) params.get("id"));
		if (null != keyValueExtend && keyValueExtend.getTenantId() == tenantId) {
			keyValueExtendDao.update(fillObject(keyValueExtend, params));
		}
		throw new Throwable("更新失败：没找到信息");
	}

	public void delete(Long tenantId, Map<String, Object> params) throws Throwable {
		if (null != params.get("id") && !deleteLogic(params)) { throw new Throwable("操作失败"); }
	}
}

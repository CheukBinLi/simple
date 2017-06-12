package com.simple.impl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.simple.core.dao.PersonnelInfoDao;
import com.simple.core.entity.PersonnelInfo;
import com.simple.core.service.PersonnelInfoService;

@Component
public class PersonnelInfoServiceImpl extends AbstractService<PersonnelInfo, Long> implements PersonnelInfoService {

	@Autowired
	private PersonnelInfoDao personnelInfoDao;

	@Override
	public BaseDao<PersonnelInfo, Long> getDao() {
		return personnelInfoDao;
	}

	public void saveOrUpdate(Long tenantId, Map<String, Object> params) throws Throwable {
		params.put("tenantId", tenantId);
		PersonnelInfo personnelInfo;
		if (params.containsKey("id")) ;
		{
			List<PersonnelInfo> list = getList(params);
			if (null != list && list.size() == 1) {
				personnelInfo = list.get(0);
				personnelInfo = fillObject(personnelInfo, params);
				personnelInfoDao.update(personnelInfo);
			}
		}
		personnelInfoDao.save(fillObject(new PersonnelInfo(), params));
	}

	public void update(Long tenantId, Map<String, Object> params) throws Throwable {
		PersonnelInfo personnelInfo = personnelInfoDao.get((Long) params.get("id"));
		if (null != personnelInfo && personnelInfo.getTenantId() == tenantId) {
			personnelInfoDao.update(fillObject(personnelInfo, params));
		}
		throw new Throwable("更新失败：没找到信息");
	}

	public void delete(Long tenantId, Map<String, Object> params) throws Throwable {
		if (null != params.get("id") && !deleteLogic(params)) { throw new Throwable("操作失败"); }
	}
}

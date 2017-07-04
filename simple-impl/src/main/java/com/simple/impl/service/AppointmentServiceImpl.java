package com.simple.impl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.cheuks.bin.original.common.util.CollectionUtil;
import com.simple.core.dao.AppointmentDao;
import com.simple.core.entity.Appointment;
import com.simple.core.service.AppointmentService;

@Component
public class AppointmentServiceImpl extends AbstractService<Appointment, Long> implements AppointmentService {

	@Autowired
	private AppointmentDao appointmentDao;

	@Override
	public BaseDao<Appointment, Long> getDao() {
		return appointmentDao;
	}

	public void saveOrUpdate(Long tenantId, Map<String, Object> params) throws Throwable {
		params.put("tenantId", tenantId);
		Appointment appointment;
		if (params.containsKey("id")) {
			Map<String, Object> tempParams = CollectionUtil.newInstance().toMap(true, new Object[] { "id", params.remove("id"), "tenantId", tenantId });
			List<Appointment> list = getList(tempParams);
			if (null != list && list.size() == 1) {
				appointment = list.get(0);
				appointment = fillObject(appointment, params);
				appointmentDao.update(appointment);
				return;
			}
			throw new RuntimeException("can't found data for id is " + tempParams.get("id") + " and tenantId is " + tenantId);
		}
		appointmentDao.save(fillObject(new Appointment().setId(generateId()), params));
	}

	public void update(Long tenantId, Map<String, Object> params) throws Throwable {
		Appointment appointment = appointmentDao.get((Long) params.get("id"));
		if (null != appointment && appointment.getTenantId() == tenantId) {
			appointmentDao.update(fillObject(appointment, params));
		}
		throw new Throwable("更新失败：没找到信息");
	}

	public void delete(Long tenantId, Map<String, Object> params) throws Throwable {
		if (null != params.get("id") && !deleteLogic(params)) { throw new Throwable("操作失败"); }
	}

}

package com.simple.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.DBAdapter;
import com.cheuks.bin.original.common.dbmanager.dao.AbstractDao;
import com.simple.core.dao.AppointmentDao;
import com.simple.core.entity.Appointment;

@Component
public class AppointmentDaoImpl extends AbstractDao<Appointment, Long> implements AppointmentDao {

	@Autowired
	private DBAdapter<?> dBAdapter;

	@Override
	public Class<Appointment> getEntityClass() {
		return Appointment.class;
	}

	@Override
	public DBAdapter<?> getDBAdapter() {
		return dBAdapter;
	}

}

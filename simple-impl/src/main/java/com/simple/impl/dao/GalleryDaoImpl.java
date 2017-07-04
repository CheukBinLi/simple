package com.simple.impl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.DBAdapter;
import com.cheuks.bin.original.common.dbmanager.dao.AbstractDao;
import com.simple.core.dao.GalleryDao;
import com.simple.core.entity.Gallery;

@Component
public class GalleryDaoImpl extends AbstractDao<Gallery, Long> implements GalleryDao {

	@Autowired
	private DBAdapter<?> dBAdapter;

	@Override
	public Class<Gallery> getEntityClass() {
		return Gallery.class;
	}

	@Override
	public DBAdapter<?> getDBAdapter() {
		return dBAdapter;
	}

}

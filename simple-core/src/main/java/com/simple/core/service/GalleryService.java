package com.simple.core.service;

import java.util.List;
import java.util.Map;

import com.cheuks.bin.original.common.dbmanager.service.BaseService;
import com.cheuks.bin.original.common.web.common.model.UploadFileModel;
import com.simple.core.entity.Gallery;

public interface GalleryService extends BaseService<Gallery, Long> {

	Gallery getByPk(Long tenantId, Long id) throws Throwable;

	void update(Long tenantId, Map<String, Object> params) throws Throwable;

	void delete(Long tenantId, Map<String, Object> params) throws Throwable;

	void save(Long tenantId, List<UploadFileModel> list) throws Throwable;

}

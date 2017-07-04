package com.simple.impl.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.cheuks.bin.original.common.util.CollectionUtil;
import com.cheuks.bin.original.common.web.common.model.UploadFileModel;
import com.simple.core.dao.GalleryDao;
import com.simple.core.entity.Gallery;
import com.simple.core.service.GalleryService;

@Component
public class GalleryServiceImpl extends AbstractService<Gallery, Long> implements GalleryService {

    @Autowired
    private GalleryDao galleryDao;

    @Override
    public BaseDao<Gallery, Long> getDao() {
        return galleryDao;
    }

    public Gallery getByPk(Long tenantId, Long id) throws Throwable {
        Map<String, Object> params = CollectionUtil.newInstance().toMap(true, new Object[] { "id", id, "Long tenantId", tenantId });
        List<Gallery> list = getList(params);
        return (null != list && list.isEmpty()) ? null : list.get(0);
    }

    public void delete(Long tenantId, Map<String, Object> params) throws Throwable {
        if (params.containsKey("id")) {
            Gallery gallery = getByPk((Long) params.get("id"));
            if (null != gallery) {
                File image = new File(gallery.getPath());
                if (image.exists())
                    image.delete();
                delete(gallery);
            }
        }
        throw new RuntimeException("can't found pk field.");
    }

    public void save(Long tenantId, List<UploadFileModel> list) throws Throwable {
        if (null == list)
            return;
        List<Gallery> galleries = new ArrayList<Gallery>();
        for (UploadFileModel u : list) {
            galleries.add(new Gallery(generateId()).setTenantId(tenantId).setName(u.getName()).setSize(u.getSize()).setPath(u.getPath()));
        }
        getDao().saveList(galleries);
    }

    public void update(Long tenantId, Map<String, Object> params) throws Throwable {
        Long id = (Long) params.remove("id");
        Gallery gallery = getByPk(tenantId, id);
        if (null != gallery) {
            galleryDao.update(fillObject(gallery, params));
            return;
        }
        throw new Throwable("更新失败：没找到信息");
    }

}

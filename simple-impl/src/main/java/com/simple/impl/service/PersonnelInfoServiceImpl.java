package com.simple.impl.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.cheuks.bin.original.common.util.conver.CollectionUtil;
import com.cheuks.bin.original.common.web.common.ContentType;
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

    public PersonnelInfo getByPk(Long tenantId, Long id) throws Throwable {
        Map<String, Object> params = CollectionUtil.newInstance().toMap(true, new Object[] { "id", id, "Long tenantId", tenantId });
        List<PersonnelInfo> list = getList(params);
        return (null != list && list.isEmpty()) ? null : list.get(0);
    }

    public void saveOrUpdate(Long tenantId, Map<String, Object> params) throws Throwable {
        params.put("tenantId", tenantId);
        PersonnelInfo personnelInfo;
        if (params.containsKey("id")) {
            Map<String, Object> tempParams = CollectionUtil.newInstance().toMap(true, new Object[] { "id", Long.valueOf(params.remove("id").toString()), "tenantId", tenantId });
            List<PersonnelInfo> list = getList(tempParams);
            if (null != list && list.size() == 1) {
                personnelInfo = list.get(0);
                personnelInfo = fillObject(personnelInfo, params);
                personnelInfoDao.update(personnelInfo);
                return;
            }
            throw new RuntimeException("can't found data for id is " + tempParams.get("id") + " and tenantId is " + tenantId);
        }
        personnelInfoDao.save(fillObject(new PersonnelInfo().setId(generateId()), params));
    }

    public void update(Long tenantId, Map<String, Object> params) throws Throwable {
        PersonnelInfo personnelInfo = personnelInfoDao.get(Long.valueOf( params.get("id").toString()));
        if (null != personnelInfo && personnelInfo.getTenantId() == tenantId) {
            personnelInfoDao.update(fillObject(personnelInfo, params));
        }
        throw new Throwable("更新失败：没找到信息");
    }

    public void delete(Long tenantId, Map<String, Object> params) throws Throwable {
        if (null == params)
            throw new Throwable("操作失败");
        PersonnelInfo personnelInfo = getByPk(tenantId, Long.valueOf(params.get("id").toString()));
        personnelInfoDao.delete(personnelInfo);
        File file = new File(params.get(ContentType.REAL_PATH) + personnelInfo.getPic());
        if (file.exists())
            file.delete();
    }
}

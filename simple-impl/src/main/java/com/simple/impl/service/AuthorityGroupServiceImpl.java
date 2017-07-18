package com.simple.impl.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.simple.core.dao.AuthorityGroupDao;
import com.simple.core.entity.AuthorityGroup;
import com.simple.core.service.AuthorityGroupService;

@Component
public class AuthorityGroupServiceImpl extends AbstractService<AuthorityGroup, Long> implements AuthorityGroupService {

    @Autowired
    private AuthorityGroupDao authorityGroupDao;

    @Override
    public BaseDao<AuthorityGroup, Long> getDao() {
        return authorityGroupDao;
    }

    public void delete(Map<String, Object> params) throws Throwable {
        if (params.containsKey("id")) {
            super.delete(new AuthorityGroup(Long.valueOf(params.get("id").toString())));
        }
        throw new RuntimeException("can't found pk field.");
    }

}

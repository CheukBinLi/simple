package com.simple.impl.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.original.common.dbmanager.dao.BaseDao;
import com.cheuks.bin.original.common.dbmanager.service.AbstractService;
import com.simple.core.dao.AuthorityDao;
import com.simple.core.entity.Authority;
import com.simple.core.service.AuthorityService;

@Component
public class AuthorityServiceImpl extends AbstractService<Authority, Long> implements AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public BaseDao<Authority, Long> getDao() {
        return authorityDao;
    }

    public void delete(Map<String, Object> params) throws Throwable {
        if (params.containsKey("id")) {
            super.delete(new Authority(Long.valueOf(params.get("id").toString())));
        }
        throw new RuntimeException("can't found pk field.");
    }

}

package com.simple.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;

import com.cheuks.bin.original.db.DefaultBaseEntity;

/***
 * 权限
 * 
 * @Title: simple-core
 * @Description: 权限
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月3日 上午11:04:52
 *
 */
@DynamicInsert(true)
@Entity(name = "simple_authority")
public class Authority extends DefaultBaseEntity {

    private static final long serialVersionUID = 4856571139294637864L;

    @Id
    private Long id;

    /***
     * 权限名称
     */
    @Column(length = 64)
    private String name;

    /***
     * 拦截路径
     */
    @Column(length = 256, name = "filter_path")
    private String filterPath;

    /***
     * 启用开/关
     */
    private Boolean activit;

    public Long getId() {
        return id;
    }

    public Authority setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Authority setName(String name) {
        this.name = name;
        return this;
    }

    public String getFilterPath() {
        return filterPath;
    }

    public Authority setFilterPath(String filterPath) {
        this.filterPath = filterPath;
        return this;
    }

    public Boolean getActivit() {
        return activit;
    }

    public Authority setActivit(Boolean activit) {
        this.activit = activit;
        return this;
    }

    public Authority() {
        super();
    }

    public Authority(Long id) {
        super();
        this.id = id;
    }

}

package com.simple.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;

import com.cheuks.bin.original.db.DefaultBaseEntity;

/***
 * 图库存
 * 
 * @Title: simple-coreTODO
 * @Description: 图库存
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年7月3日 下午2:53:48
 *
 */
@DynamicInsert(true)
@Entity(name = "simple_gallery")
public class Gallery extends DefaultBaseEntity {

    private static final long serialVersionUID = 829272477771487335L;

    @Id
    private Long id;

    @Column(nullable = false, name = "tenant_id")
    private Long tenantId;

    private String name;// 文件名

    private Long size;// 文件大小

    private String path;// 文件路径

    public Long getId() {
        return id;
    }

    public Gallery setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Gallery setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Gallery setName(String name) {
        this.name = name;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public Gallery setSize(Long size) {
        this.size = size;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Gallery setPath(String path) {
        this.path = path;
        return this;
    }

    public Gallery() {
        super();
    }

    public Gallery(Long id) {
        super();
        this.id = id;
    }

}

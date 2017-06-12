package com.simple.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.cheuks.bin.original.common.dbmanager.BaseEntity;

/***
 * 
 * @Title: simple-core
 * @Description:K/V扩展对象表
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月3日 上午11:07:56
 *
 */
@Entity(name = "simple_key_value_extend")
//@Table(name = "simple_key_value_extend", indexes = {
//		@Index(columnList = "core", name = "key_value_extend_core_index")
//})
public class KeyValueExtend extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3783652489076423934L;
	@Id
	private Long id;
	@Column(nullable = false, name = "tenant_id")
	private Long tenantId;//租户ID
	@Column(length = 32)
	private String name;//名称
	private String core;//key/类型
	@Column(length = 102400)
	private String content;//value/内容

	public Long getId() {
		return id;
	}

	public KeyValueExtend setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public KeyValueExtend setTenantId(Long tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	public String getName() {
		return name;
	}

	public KeyValueExtend setName(String name) {
		this.name = name;
		return this;
	}

	public String getCore() {
		return core;
	}

	public KeyValueExtend setCore(String core) {
		this.core = core;
		return this;
	}

	public String getContent() {
		return content;
	}

	public KeyValueExtend setContent(String content) {
		this.content = content;
		return this;
	}

}

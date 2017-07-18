package com.simple.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;

import com.cheuks.bin.original.db.DefaultBaseEntity;

/***
 * 租户信息
 * 
 * @Title: simple-core
 * @Description: 租户信息
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月3日 上午11:40:35
 *
 */
@DynamicInsert(true)
@Entity(name = "simple_tenant")
public class Tenant extends DefaultBaseEntity {

	private static final long serialVersionUID = -1397924514367022211L;

	@Id
	private Long id;
	/***
	 * 公司名
	 */
	@Column(length = 64, name = "tenant_name")
	private String tenantName;
	/***
	 * 联系人
	 */
	@Column(length = 32)
	private String contact;
	/***
	 * 电话
	 */
	@Column(length = 32)
	private String phone;
	/***
	 * 备注
	 */
	@Column(length = 512)
	private String remark;

	public Long getId() {
		return id;
	}

	public Tenant setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTenantName() {
		return tenantName;
	}

	public Tenant setTenantName(String tenantName) {
		this.tenantName = tenantName;
		return this;
	}

	public String getContact() {
		return contact;
	}

	public Tenant setContact(String contact) {
		this.contact = contact;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Tenant setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public Tenant setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public Tenant() {
		super();
	}

	public Tenant(Long id) {
		super();
		this.id = id;
	}

}

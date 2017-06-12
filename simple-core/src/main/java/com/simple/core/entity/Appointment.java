package com.simple.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.cheuks.bin.original.common.dbmanager.BaseEntity;

/***
 * 
 * @Title: simple-core
 * @Description: 预约记录表
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月4日 上午7:19:02
 *
 */
@Entity(name = "simple_appointment")
public class Appointment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1773838362699935647L;
	@Id
	private Long id;
	@Column(nullable = false, name = "tenant_id")
	private Long tenantId;//租户ID
	@Column(name = "personnel_info_id")
	private Long personnelInfoId;//人员表ID
	@Column(length = 32)
	private String employer;//雇主
	@Column(length = 32)
	private String phone;
	@Column(length = 32)
	private String qq;
	@Column(length = 64)
	private String email;
	@Column(length = 256)
	private String address;
	@Column(length = 1, name = "service_type")
	private Integer serviceType = 0;//服务类型
	@Column(length = 1, name = "serviceStatus")
	private Integer serviceStatus;//服务状态
	@Column(length = 512) //备注，要求
	private String remark;
	@Column(length = 512)
	private String extend;//扩展

	public Long getId() {
		return id;
	}

	public Appointment setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public Appointment setTenantId(Long tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	public Long getPersonnelInfoId() {
		return personnelInfoId;
	}

	public Appointment setPersonnelInfoId(Long personnelInfoId) {
		this.personnelInfoId = personnelInfoId;
		return this;
	}

	public String getEmployer() {
		return employer;
	}

	public Appointment setEmployer(String employer) {
		this.employer = employer;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Appointment setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getQq() {
		return qq;
	}

	public Appointment setQq(String qq) {
		this.qq = qq;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Appointment setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Appointment setAddress(String address) {
		this.address = address;
		return this;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public Appointment setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
		return this;
	}

	public Integer getServiceStatus() {
		return serviceStatus;
	}

	public Appointment setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public Appointment setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public String getExtend() {
		return extend;
	}

	public Appointment setExtend(String extend) {
		this.extend = extend;
		return this;
	}

}

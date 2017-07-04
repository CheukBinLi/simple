package com.simple.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.cheuks.bin.original.db.DefaultBaseEntity;

/***
 * 登录用户信息表
 * 
 * @Title: simple-core
 * @Description: 登录用户信息表
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月3日 上午11:05:04
 *
 */
@Entity(name = "simple_user")
public class User extends DefaultBaseEntity {

	private static final long serialVersionUID = -668934565870548312L;

	@Id
	private Long id;
	/***
	 * 租户ID
	 */
	@Column(name = "tenant_id")
	private Long tenantId;
	/***
	 * 权限组ID
	 */
	@Column(name = "authority_group_id")
	private Long authorityGroupId;
	/***
	 * 用户信息关联
	 */
	@Column(name = "personnel_info_id")
	private Long personnelInfoId;
	/***
	 * 登录名
	 */
	@Column(name = "user_name")
	private String userName;
	/***
	 * 密码(转加密码)
	 */
	@Column(length = 64)
	private String password;
	/***
	 * 用户状态(0:使用,1:停用)' default 0
	 */
	@Column(length = 1)
	private Integer status = 0;

	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public User setTenantId(Long tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	public Long getAuthorityGroupId() {
		return authorityGroupId;
	}

	public User setAuthorityGroupId(Long authorityGroupId) {
		this.authorityGroupId = authorityGroupId;
		return this;
	}

	public Long getPersonnelInfoId() {
		return personnelInfoId;
	}

	public User setPersonnelInfoId(Long personnelInfoId) {
		this.personnelInfoId = personnelInfoId;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public Integer getStatus() {
		return status;
	}

	public User setStatus(Integer status) {
		this.status = status;
		return this;
	}

	public User() {
		super();
	}

	public User(Long id) {
		super();
		this.id = id;
	}

}

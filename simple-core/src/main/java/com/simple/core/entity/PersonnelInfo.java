package com.simple.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.cheuks.bin.original.db.DefaultBaseEntity;

/***
 * 人员信息表
 * 
 * @Title: simple-core
 * @Description: 人员信息表
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月3日 上午11:06:37
 *
 */
@Entity(name = "simple_personnel_info")
public class PersonnelInfo extends DefaultBaseEntity {

	private static final long serialVersionUID = 6880608434674103212L;
	@Id
	private Long id;
	/***
	 * 租户ID
	 */
	@Column(nullable = false, name = "tenant_id")
	private Long tenantId;
	/***
	 * 姓名
	 */
	@Column(length = 64)
	private String name;
	/***
	 * 姓别
	 */
	@Column(length = 1)
	private Integer sex = 0;
	/***
	 * 图片路径
	 */
	@Column(length = 256)
	private String pic;
	@Column(length = 64)
	private String phone;
	@Column(length = 64)
	private String address;
	/***
	 * 身份证号
	 */
	@Column(length = 20, name = "id_card")
	private String idCard;
	/**
	 * 籍贯
	 */
	@Column(length = 32)
	private String origin;
	/***
	 * 职位
	 */
	@Column(length = 1)
	private Integer position;
	/***
	 * 备注,描述
	 */
	@Column(length = 512)
	private String remark;
	/***
	 * 扩展
	 */
	@Column(length = 512)
	private String extend;

	public Long getId() {
		return id;
	}

	public PersonnelInfo setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public PersonnelInfo setTenantId(Long tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	public String getName() {
		return name;
	}

	public PersonnelInfo setName(String name) {
		this.name = name;
		return this;
	}

	public int getSex() {
		return sex;
	}

	public PersonnelInfo setSex(int sex) {
		this.sex = sex;
		return this;
	}

	public String getPic() {
		return pic;
	}

	public PersonnelInfo setPic(String pic) {
		this.pic = pic;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public PersonnelInfo setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public PersonnelInfo setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getIdCard() {
		return idCard;
	}

	public PersonnelInfo setIdCard(String idCard) {
		this.idCard = idCard;
		return this;
	}

	public String getOrigin() {
		return origin;
	}

	public PersonnelInfo setOrigin(String origin) {
		this.origin = origin;
		return this;
	}

	public int getPosition() {
		return position;
	}

	public PersonnelInfo setPosition(int position) {
		this.position = position;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public PersonnelInfo setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public String getExtend() {
		return extend;
	}

	public PersonnelInfo setExtend(String extend) {
		this.extend = extend;
		return this;
	}

}

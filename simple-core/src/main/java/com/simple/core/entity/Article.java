package com.simple.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.cheuks.bin.original.db.DefaultBaseEntity;

/***
 * 文章信息表
 * 
 * @Title: simple-core
 * @Description: 文章信息表
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月4日 下午1:19:24
 *
 */
@Entity(name = "simple_article")
public class Article extends DefaultBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5038895292138373042L;

	@Id
	private Long id;
	/***
	 * 租户ID
	 */
	@Column(nullable = false, name = "tenant_id")
	private Long tenantId;
	/***
	 * 标题
	 */
	@Column(length = 64)
	private String title;
	/***
	 * 文章类型
	 */
	@Column(length = 1, name = "article_type")
	private Integer articleType;
	/***
	 * 查看次数
	 */
	@Column(name = "viewed_count")
	private Long viewedCount;
	/***
	 * 文章内容
	 */
	@Column(length = 102400)
	private String content;
	/***
	 * 扩展参数
	 */
	@Column(length = 1024)
	private String extend;

	public Long getId() {
		return id;
	}

	public Article setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public Article setTenantId(Long tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Article setTitle(String title) {
		this.title = title;
		return this;
	}

	public Integer getArticleType() {
		return articleType;
	}

	public Article setArticleType(Integer articleType) {
		this.articleType = articleType;
		return this;
	}

	public Long getViewedCount() {
		return viewedCount;
	}

	public Article setViewedCount(Long viewedCount) {
		this.viewedCount = viewedCount;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Article setContent(String content) {
		this.content = content;
		return this;
	}

	public String getExtend() {
		return extend;
	}

	public Article setExtend(String extend) {
		this.extend = extend;
		return this;
	}

}

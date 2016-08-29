package com.jspxcms.core.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.jspxcms.common.web.Anchor;

/**
 * 专题评论
 * 
 * @author lvbin
 * 
 */
@Entity
@DiscriminatorValue("Special")
public class SpecialComment extends Comment {
	private static final long serialVersionUID = 1L;
	
	@Override
	@Transient
	public Anchor getAnchor() {
		return special;
	}

	private Special special;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_fid", nullable = false, insertable = false, updatable = false)
	public Special getSpecial() {
		return special;
	}

	public void setSpecial(Special special) {
		this.special = special;
	}
}

package com.jspxcms.plug.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Table(name="cms_browse")
public class Browse {
	private Integer id;
	private Integer userId;
	private Integer courseId;
	private String courseName;
	private String courseSmallImage;
	private String courseTag;
	private Integer courseBuyerCount;
	private Date gmtCreate;
	private Date gmtModify;
	private Integer version;

	@Id
	@Column(name = "browse_id",unique=true,nullable=false)
	@TableGenerator(name = "tg_cms_browse", pkColumnValue = "cms_browse", 
		table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_browse")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id") 
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "course_id")
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	@Column(name = "GMT_CREATE")
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Column(name = "GMT_MODIFY")
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	@Column(name = "VERSION")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Column(name = "course_name")
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Column(name = "course_smallImage")
	public String getCourseSmallImage() {
		return courseSmallImage;
	}
	public void setCourseSmallImage(String courseSmallImage) {
		this.courseSmallImage = courseSmallImage;
	}
	@Column(name = "course_tag")
	public String getCourseTag() {
		return courseTag;
	}
	public void setCourseTag(String courseTag) {
		this.courseTag = courseTag;
	}
	@Column(name = "course_buyer_count")
	public Integer getCourseBuyerCount() {
		return courseBuyerCount;
	}
	public void setCourseBuyerCount(Integer courseBuyerCount) {
		this.courseBuyerCount = courseBuyerCount;
	}
	

}

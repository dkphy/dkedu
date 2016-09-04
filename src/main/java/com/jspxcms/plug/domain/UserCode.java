package com.jspxcms.plug.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name="user_code")
public class UserCode {
	
	private Integer id;
	private String name;
	private Double score; 
	private String idCard;
	private String scoreStatus;
	private Date gmtCreate;
	private Date gmtModify;
	private Integer version;
	
	@Id
	@Column(name = "ID",unique=true,nullable=false)
	@TableGenerator(name = "tg_user_code", pkColumnValue = "user_code", 
		table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_user_code")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@NotBlank
	@Column(name = "name", nullable = false, length = 20)
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	@Column(name = "score", nullable = false)
	public Double getScore(){
		return this.score;
	}
	public void setScore(Double score){
		this.score = score;
	}

	@Column(name = "ID_card", nullable = false)
	public String getIdCard(){
		return this.idCard;
	}
	public void setIdCard(String idCard){
		this.idCard = idCard;
	}
	
	@Column(name = "score_status", nullable = false, length = 10)
	public String getScoreStatus(){
		return this.scoreStatus;
	}
	public void setScoreStatus(String scoreStatus){
		this.scoreStatus = scoreStatus;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "GMT_CREATE", nullable = false)
	public Date getGmtCreate(){
		return this.gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "GMT_MODIFY", nullable = false)
	public Date getGmtModify(){
		return this.gmtModify;
	}
	public void setGmtModify(Date gmtModify){
		this.gmtModify = gmtModify;
	}
	
	@Column(name = "VERSION", nullable = false)
	public Integer getVersion(){
		return this.version;
	}
	public void setVersion(Integer version){
		this.version = version;
	}

	@Transient
	public void applyDefaultValue() {
		if (getGmtCreate() == null) {
			setGmtCreate(new Timestamp(System.currentTimeMillis()));
		}
		if (getGmtModify() == null) {
			setGmtModify(new Timestamp(System.currentTimeMillis()));
		}
		if (StringUtils.isBlank(getScoreStatus())) {
			setScoreStatus(ScoreStatus.SCORE_USEABLE);
		}
	}
}

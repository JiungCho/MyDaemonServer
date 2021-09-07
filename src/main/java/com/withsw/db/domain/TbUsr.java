package com.withsw.db.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "TB_USR")
@Data
public class TbUsr {
	
	@Id
	@Column(name = "USR_IDX")
	private int userIdx;

	@Column(name = "USR_ID")
	private String userId;
	
	@Column(name = "USR_PW")
	private String userPw;
	
	@Column(name = "USR_NM")
	private String userNm;
	
	@Column(name = "USR_PERM")
	private int userPerm;
	
	@Column(name = "PHN_NO")
	private String phoneNo;
	
	@Column(name = "SMS_SND_YN")
	private String smsSendYn;
	
	@Column(name = "MAIL")
	private String mail;
	
	@Column(name = "MAIL_SND_YN")
	private String mailSendYn;
	
	@Column(name = "REG_DT")
	@CreationTimestamp
	private LocalDateTime regDt;
	
	@Column(name = "REG_USR_ID")
	private String regUserId;
	
	@Column(name = "UPT_DT")
	@UpdateTimestamp
	private LocalDateTime uptDt;
	
	@Column(name = "UPT_USR_ID")
	private String uptUserId;
	
	@Column(name = "DEL")
	private String del;
	
	
}

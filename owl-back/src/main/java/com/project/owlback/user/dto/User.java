package com.project.owlback.user.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
	@Column(name="email", length=50, nullable=false)
    private String email;
//    private String nickname;
//    private String name;
//    private String imgFile;
//    private String introduction;
	@Column(name="password", length=1000, nullable=false)
    private String password;
//    private Date createdDate;
//    private Date modifiedDate;
//    private int status;

}

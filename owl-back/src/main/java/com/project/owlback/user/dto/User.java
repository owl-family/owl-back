package com.project.owlback.user.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition="int")
    private long userId;
	
	@Column(name="email", length=50, nullable=false)
    private String email;
	
	@Column(name="nickname", length=10, nullable=false)
    private String nickname;
	
	@Column(name="name", length=10, nullable=false)
    private String name;
	
	@Column(name="img_file", columnDefinition="mediumblob")
    private String imgFile;
	
	@Column(name="introduction", length=1000, nullable=false)
	@ColumnDefault("'안녕하세요!'")
    private String introduction;
	
	@Column(name="password", length=1000, nullable=false)
    private String password;
	
	@Column(name="created_date", columnDefinition="datetime DEFAULT CURRENT_TIMESTAMP", nullable=false)
    private Date createdDate;
	
	@Column(name="modified_date", columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable=false)
    private Date modifiedDate;
	
	@Column(name="status", columnDefinition="int", nullable=false)
	@ColumnDefault("2")
    private int status;
	
    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

	@Override
	public String getUsername() {
		return email;
	}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

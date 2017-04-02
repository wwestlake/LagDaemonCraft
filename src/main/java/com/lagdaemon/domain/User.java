package com.lagdaemon.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Transient;

import com.lagdaemon.interfaces.AuthenticationSource;

@Entity
@Table(name = "Users")
public class User extends RecaptchaForm {

	public User() {}
	
	public User(AuthenticationSource authSource, String email, Boolean emailValidated, String passwordHash) {
		this.authSource = authSource;
		this.email = email;
		this.emailValidated = emailValidated;
		this.lastLoginDateTime = LocalDateTime.now();
		this.passwordHash = passwordHash;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long id;
	public long getId() { return id; }
	
	
	@Enumerated(EnumType.STRING)
	@Column(updatable = true, nullable = false, length=50)
	private AuthenticationSource authSource;
	public AuthenticationSource getAuthSource() { return authSource; }
	public void setAuthSource(AuthenticationSource value) { authSource = value; }
	
	@Column(updatable = true, nullable = false, length=50)
	private String email;
	public String getEmail() { return email; }
	public void setEmail(String value) { email = value; }

	@Column(updatable = true, nullable = true, length=50)
	private String firstName;
	public String getFirstName() { return firstName; }
	public void setFirstName(String value) { firstName = value; }

	@Column(updatable = true, nullable = true, length=50)
	private String lastName;
	public String getLastName() { return lastName; }
	public void setLastName(String value) { lastName = value; }

	@Column(updatable = true, nullable = true, length=50)
	private String displayName;
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String value) { displayName = value; }
	
	@Column(updatable = true, nullable = true, length=50)
	private String minecraftId;
	public String getMinecraftId() { return minecraftId; }
	public void setMinecraftId(String value) { minecraftId = value; }
	
	@Column(updatable = true, nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean emailValidated;
	public Boolean getEmailValidated() { return emailValidated; }
	public void setEmailValidated(Boolean value) { emailValidated = value; }

	@Column(updatable = true, nullable = true, columnDefinition = "TINYINT(1)")
	private Boolean locked;
	public Boolean getLocked() { return locked; }
	public void setLocked(Boolean value) { locked = value; }
	
	@Column(updatable = true, nullable = false)
	private LocalDateTime lastLoginDateTime;
	public LocalDateTime getLastLoginDateTime() { return lastLoginDateTime; }
	public void setLastLoginDateTime(LocalDateTime value) { lastLoginDateTime = value; }
	
	@Column(updatable = true, nullable = false)
	private String passwordHash;
	public String getPasswordHash() { return passwordHash; }
	public void setPasswordHash(String value) { passwordHash = value; }
	
    @ManyToMany(fetch=FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "users_roles",
          joinColumns = {@JoinColumn(name = "user_id")}, 
          inverseJoinColumns = @JoinColumn(name = "role_id"))	
	@Column(updatable = true, nullable = false)
	private Set<Role> roles;
    
    @Column(updatable = true, nullable = true, length=50)
	private String emailValidationCode;
	public String getEmailValidationCode() { return this.emailValidationCode; }
	public void setEmailValidationCode(String value) { this.emailValidationCode = value; }
	
	@Column(updatable = true, nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean agreeTermsAndConditions;
	public Boolean getAgreeTermsAndConditions() { return this.agreeTermsAndConditions; }
	public void setAgreeTermsAndConditions(Boolean value) { this.agreeTermsAndConditions = value; }

	@Column(updatable = true, nullable = true)
    private String login;
	public String getLogin() { return login; }
	public void setLogin(String value) { this.login = value; }


	
    @ManyToMany(fetch=FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
	public Set<Role> getRoles() { return this.roles; }
	public void setRoles(Set<Role> value) { this.roles = value; }
	
	public void addRole(Role role) {
		if (roles == null) roles = new HashSet<Role>();
		roles.add(role);
	}

	public void removeRole(Role role) {
		if (roles == null) return;
		if (! roles.contains(role)) return;
		roles.remove(role);
	}
	
	// not stored in repository
	
	@Transient
	private String passwordConfirm;
	
	@Transient
	public String getPasswordConfirm() { return this.passwordConfirm; }
	
	@Transient
	public void setPasswordConfirm(String value) { this.passwordConfirm = value; }


	
	
}

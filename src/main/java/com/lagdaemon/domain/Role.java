package com.lagdaemon.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Role {
	public Role() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long role_id;

	@Column(updatable = true, nullable = true, length=50)
	private String role;

	public String getRole() { return role; }
	public void setRole(String value) { this.role = value; }
	
    @ManyToMany(fetch=FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "users_roles",
          joinColumns = {@JoinColumn(name = "role_id")}, 
          inverseJoinColumns = @JoinColumn(name = "user_id"))	
    private Set<User> users;
    
    public Set<User> getUsers() { return users; }

	public void addUser(User user) {
		if (users == null) users = new HashSet<User>();
		users.add(user);
	}
	
	public void removeUser(User user) {
		if (users == null) return;
		if (! users.contains(user)) return;
		users.remove(user);
	}
	
	
}

package com.lagdaemon.domain.forum;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lagdaemon.domain.User;
import com.lagdaemon.domain.common.UserMetrics;

@Entity
@Table(name = "Forums")
public class Forum extends UserMetrics {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long forumId;
	public long getForumId() {
		return forumId;
	}

	@Column(updatable = true, nullable = false, length=512)
	private String topic;
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	@Column(updatable = true, nullable = true, length=512)
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToOne()
	@JoinColumn(name = "userId")
	private User owner;
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
	private List<ForumPost> posts;
	public List<ForumPost> getPosts() {
		return posts;
	}
	public void setPosts(List<ForumPost> posts) {
		this.posts = posts;
	}
	
}

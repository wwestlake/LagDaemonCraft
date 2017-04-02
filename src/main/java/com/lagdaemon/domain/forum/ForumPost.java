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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lagdaemon.domain.User;
import com.lagdaemon.domain.common.UserMetrics;

@Entity
@Table(name = "forum_posts")
public class ForumPost extends UserMetrics {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long postId;
	public long getPostId() {
		return postId;
	}

	@Column(updatable = true, nullable = false, length=512)
	private String subject;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(updatable = true, nullable = false, length=512)
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@OneToMany(fetch = FetchType.LAZY)
	private List<ForumPost> replies;
	public List<ForumPost> getReplies() {
		return replies;
	}
	public void setReplies(List<ForumPost> replies) {
		this.replies = replies;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forumPostId")
	private ForumPost parentPost;
	public ForumPost getParentPost() {
		return parentPost;
	}
	public void setParentPost(ForumPost parentPost) {
		this.parentPost = parentPost;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forumId")
	private Forum owner;
	public Forum getOwner() {
		return owner;
	}
	public void setOwner(Forum owner) {
		this.owner = owner;
	}


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User postedBy;
	public User getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}
	
	
}

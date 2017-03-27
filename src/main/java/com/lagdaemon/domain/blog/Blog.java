package com.lagdaemon.domain.blog;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

import com.lagdaemon.domain.RecaptchaForm;
import com.lagdaemon.domain.User;


@Entity
@Table(name = "Blogs")
public class Blog extends RecaptchaForm {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long blogId;
	public long getBlogId() { return blogId; }
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User author;
	public User getAuthor() { return author; }
	public void setAuthor(User value) { this.author = value; }
	
	@Column(updatable = true, nullable = false, length=512)
	private String title;
	public String getTitle() { return title; }
	public void setTitle(String value) { this.title = value; }
	
	@Column(updatable = true, nullable = false)
	private LocalDateTime createdOn;
	public LocalDateTime getCreatedOn() { return createdOn; }
	public void setTitle(LocalDateTime value) { this.createdOn = value; }
	
	@Column(updatable = true, nullable = false)
	private LocalDateTime publishedOn;
	public LocalDateTime getPublishedOn() { return publishedOn; }
	public void setPublishedOn(LocalDateTime value) { this.publishedOn = value; }
	
	@Column(updatable = true, nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean isPublished;
	public Boolean getIsPublished() { return this.isPublished; }
	public void setIsPublished(Boolean value) { this.isPublished = value; }
	
	@Column(updatable = true, nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean allowComments;
	public Boolean getAllowComments() { return this.allowComments; }
	public void setAllowComments(Boolean value) { this.allowComments = value; }

	@Column(updatable = true, nullable = false)
	private long likes;
	public long getLikes() { return likes; }
	public void setLikes(long value) { this.likes = value; }
	
	@Column(updatable = true, nullable = false)
	private long dislikes;
	public long getDislikes() { return dislikes; }
	public void setDislikes(long value) { this.dislikes = value; }

	@Column(updatable = true, nullable = false)
	private long blogReads;
	public long getReads() { return blogReads; }
	public void setReads(long value) { this.blogReads = value; }
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blog", cascade = CascadeType.ALL)
	private Set<BlogPage> blogPages = new HashSet<BlogPage>(0);
	public Set<BlogPage> getBlogPages() { return blogPages; }
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blog", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>(0);
	public Set<Comment> getComments() { return comments; }

	
	public void addBlogPage(BlogPage page) {
		blogPages.add(page);
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public void IncrementLike() {
		likes++;
	}
	
	public void IncrementDislike() {
		dislikes++;
	}
	
	public void IncrementReads() {
		blogReads++;
	}
	
}

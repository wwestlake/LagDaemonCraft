package com.lagdaemon.domain.blog;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lagdaemon.domain.User;

@Entity
@Table(name = "Comments")
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long commentId;
	public long getCommentId() { return commentId; }

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User author;
	public User getAuthor() { return author; }
	public void setAuthor(User value) { this.author = value; }
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blogId")
	private Blog blog;
	public Blog getBlog() { return this.blog; }
	public void setBlog(Blog value) { this.blog = value; }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blogPageId")
	private BlogPage blogPage;
	public BlogPage getBlogPage() { return this.blogPage; }
	public void setBlog(BlogPage value) { this.blogPage = value; }
	
	
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
	
	
	@Column(columnDefinition="TEXT", updatable = true, nullable = true)
	private String text;
	public String getText() { return this.text; }
	public void setText(String value) { this.text = value; }


	
	
}

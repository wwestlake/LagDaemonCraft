package com.lagdaemon.domain.blog;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lagdaemon.domain.User;
import com.lagdaemon.domain.common.UserMetrics;


@Entity
@Table(name = "Blogs")
public class Blog extends UserMetrics {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long blogId;
	public long getBlogId() { return blogId; }
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "userId")
	private User author;
	public User getAuthor() { return author; }
	public void setAuthor(User value) { this.author = value; }
	
	@Column(updatable = true, nullable = false, length=512)
	private String title;
	public String getTitle() { return title; }
	public void setTitle(String value) { this.title = value; }
	
	@Column(updatable = true, nullable = false, length=1024)
	private String description;
	public String getDescription() { return description; }
	public void setDescription(String value) { this.description = value; }
	
	
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
	
	
}

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
import javax.persistence.Table;

import com.lagdaemon.domain.RecaptchaForm;

@Entity
@Table(name = "BlogPages")
public class BlogPage extends RecaptchaForm  {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long blogPageId;
	public long getblogPageId() { return blogPageId; }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blogId")
	private Blog blog;
	public Blog getBlog() { return this.blog; }
	public void setBlog(Blog value) { this.blog = value; }
	
	@Column(updatable = true, nullable = false)
	private long pageNumber;
	public long getPageNumber() { return this.pageNumber; }
	public void setPageNumber(long value) { this.pageNumber = value; }
	
	@Column(columnDefinition="TEXT", updatable = true, nullable = true)
	private String text;
	public String getText() { return this.text; }
	public void setText(String value) { this.text = value; }

	@Column(updatable = true, nullable = false)
	private long likes;
	public long getLikes() { return likes; }
	public void setLikes(long value) { this.likes = value; }
	
	@Column(updatable = true, nullable = false)
	private long dislikes;
	public long getDislikes() { return dislikes; }
	public void setDislikes(long value) { this.dislikes = value; }

	@Column(updatable = true, nullable = false)
	private long pageReads;
	public long getReads() { return pageReads; }
	public void setReads(long value) { this.pageReads = value; }
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blogPage", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>(0);
	public Set<Comment> getComments() { return comments; }

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
		pageReads++;
	}
	
}

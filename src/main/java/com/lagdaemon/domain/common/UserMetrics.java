package com.lagdaemon.domain.common;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import com.lagdaemon.domain.RecaptchaForm;

@MappedSuperclass
public class UserMetrics extends RecaptchaForm {
	
	@Column(updatable = true, nullable = false)
	private LocalDateTime createdOn;
	public LocalDateTime getCreatedOn() { return createdOn; }
	public void setCreatedOn(LocalDateTime value) { this.createdOn = value; }
	
	@Column(updatable = true, nullable = true)
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
	private long userReads;
	public long getUserReads() { return userReads; }
	public void setUserReads(long value) { this.userReads = value; }
	
	
	public void IncrementLike() {
		likes++;
	}
	
	public void IncrementDislike() {
		dislikes++;
	}
	
	public void IncrementUserReads() {
		userReads++;
	}

	
	
}

package com.lagdaemon.domain.blog;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Bibliographies")
public class Bibliography {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long bibliographyId;
	public long getBlogId() { return bibliographyId; }

	
	
	@Column(updatable = true, nullable = false)
	private LocalDateTime publicationDate;
	public LocalDateTime getPublicationDate() { return publicationDate; }
	public void setpublicationDate(LocalDateTime value) { this.publicationDate = value; }

	
	
}

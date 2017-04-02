package com.lagdaemon.domain.blog;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
	private List<ReferenceAuthor> authors;
	public List<ReferenceAuthor> getAuthors() { return authors; }
	public void setAuthors(List<ReferenceAuthor> authors) { this.authors = authors; }

	@Column(updatable = true, nullable = false, length=512)
	private String title;
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	@Column(updatable = true, nullable = true, length=512)
	private String publisher;
	public String getPublisher() { return publisher;}
	public void setPublisher(String publisher) { this.publisher = publisher; }
	
	@Column(updatable = true, nullable = true, length=512)
	private String publisherAddress;
	public String getPublisherAddress() { return publisherAddress; }
	public void setPublisherAddress(String publisherAddress) { this.publisherAddress = publisherAddress;}

	@Column(updatable = true, nullable = true, length=512)
	private String webLocation;
	public String getWebLocation() { return webLocation; }
	public void setWebLocation(String webLocation) { this.webLocation = webLocation; }
	
}

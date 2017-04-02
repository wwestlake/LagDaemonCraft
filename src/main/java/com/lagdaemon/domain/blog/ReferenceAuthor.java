package com.lagdaemon.domain.blog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ReferenceAuthors")
public class ReferenceAuthor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long referenceAuthorId;
	public long getReferenceAuthorId() { return referenceAuthorId; }

	@Column(updatable = true, nullable = true, length=50)
	private String firstName;
	public String getFirstName() { return this.firstName; }
	public void setFirstName(String value) { this.firstName = value; }
	
	@Column(updatable = true, nullable = true, length=50)
	private String middleName;
	public String getMiddleName() { return this.middleName; }
	public void setMiddleName(String value) { this.middleName = value; }

	@Column(updatable = true, nullable = true, length=50)
	private String lastName;
	public String getLastName() { return this.lastName; }
	public void setLastName(String value) { this.lastName = value; }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bibliographyId")
	private Bibliography parent;
	public Bibliography getParent() { return parent; }
	public void setParent(Bibliography parent) { this.parent = parent; }

	
	
}

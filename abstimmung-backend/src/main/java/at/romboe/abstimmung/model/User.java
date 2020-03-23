package at.romboe.abstimmung.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue
	private UUID id;
	@CreationTimestamp
	private Date creationDateTime;
	private String name;
	private String email;

	public User() {
		super();
	}

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public User(String email) {
		this("Name", email);
	}
}

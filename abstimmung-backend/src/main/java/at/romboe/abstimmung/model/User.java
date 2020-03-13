package at.romboe.abstimmung.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;
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

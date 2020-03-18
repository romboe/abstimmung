package at.romboe.abstimmung.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Option {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@OneToMany
	private Set<User> voters = new HashSet<>();

	public Option() {
		super();
	}

	public Option(String name) {
		this.name = name;
	}
}

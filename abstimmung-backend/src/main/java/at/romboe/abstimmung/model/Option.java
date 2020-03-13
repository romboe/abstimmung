package at.romboe.abstimmung.model;

import java.util.ArrayList;
import java.util.List;

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
	private List<User> voters = new ArrayList<>();

	public Option() {
		super();
	}

	public Option(String name) {
		this.name = name;
	}
}

package at.romboe.abstimmung.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Voting {

	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String description;
	@OneToOne
	private User creator;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Option> options;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Map<String, User> voters = new LinkedHashMap<>(); // LinkedHashMap keeps order of insertion


	public Voting() {
		super();
	}

	public Voting(String name) {
		this.name = name;
	}

	public String dump() {
		StringBuilder sb = new StringBuilder();
		sb.append("=============================================").append("\n");
		sb.append(id + " " + name).append("\n");
		sb.append("- Description -------------------------------").append("\n");
		sb.append(description).append("\n");
		sb.append("- Options -----------------------------------").append("\n");
		for (Option o:options) {
			sb.append(o.getName()).append("\n");
		}
		sb.append("- Voters ------------------------------------").append("\n");
		for (Map.Entry<String, User> entry:voters.entrySet()) {
			User user = entry.getValue();
			sb.append(entry.getKey()).append(" | ").append(user.getId()).append(" | ").append(user.getEmail()).append("\n");
		}
		sb.append("=============================================").append("\n");
		return sb.toString();
	}
}

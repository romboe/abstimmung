package at.romboe.abstimmung.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
	private Voter creator;
	// CascadeType.ALL alle Operationen, die auf Voting angewandt werden, werden auch auf options angewendet
	@OneToMany(cascade=CascadeType.ALL)
	private List<Option> options;
	// CascadeType.ALL wenn Voting gespeichert wird, dann werden auch die User in der Voters Liste gespeichert,
	// ansonsten müsste man MANUELL zuerst den User speichern u danach das Voting
	@OneToMany(cascade=CascadeType.ALL)
	private List<Voter> voters;


	public Voting() {
		super();
	}

	public Voting(String name) {
		this.name = name;
	}
}

package at.romboe.abstimmung.model.client;

import lombok.Data;

@Data
public class AddVoterInput {

	private String votingId;
	private String name;
	private String email;
}

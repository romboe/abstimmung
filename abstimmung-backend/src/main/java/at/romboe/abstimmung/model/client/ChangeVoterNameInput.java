package at.romboe.abstimmung.model.client;

import lombok.Data;

@Data
public class ChangeVoterNameInput {

	private String votingId;
	private String voterId;
	private String name;
}

package at.romboe.abstimmung.model.client;

import java.util.List;

import lombok.Data;

@Data
public class CreateVotingInput {

	private String votingName;
	private String description;
	private String creatorName;
	private String creatorEmail;
	private List<String> options;
}

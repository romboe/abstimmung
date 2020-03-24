package at.romboe.abstimmung.model.client;

import lombok.Data;

@Data
public class AddUserInput {

	private String votingId;
	private String name;
	private String email;
}

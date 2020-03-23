package at.romboe.abstimmung.model.client;

import lombok.Data;

@Data
public class ChangeUserNameInput {

	private String votingId;
	private String userId;
	private String name;
}

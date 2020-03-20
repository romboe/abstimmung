package at.romboe.abstimmung.model.client;

import lombok.Data;

@Data
public class VoteInput {
	private String votingId;
	private String voterId;
	private Integer optionIndex;
	private Boolean value;
}

package at.romboe.abstimmung.model.client;

import lombok.Data;

@Data
public class Vote {
	private String votingId;
	private String userId;
	private Integer optionIndex;
	private Boolean value;
}

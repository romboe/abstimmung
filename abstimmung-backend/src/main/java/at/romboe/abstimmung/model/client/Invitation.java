package at.romboe.abstimmung.model.client;

import java.util.List;

import lombok.Data;

@Data
public class Invitation {

	private String votingId;
	private List<String> emails;
}

package at.romboe.abstimmung.model.client;

import java.util.List;

import lombok.Data;

@Data
public class Response {

	private String name;
	private int enabledRow;
	private List<List<String>> rows;
}

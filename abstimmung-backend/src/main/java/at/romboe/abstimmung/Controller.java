package at.romboe.abstimmung;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.romboe.abstimmung.model.Option;
import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;
import at.romboe.abstimmung.model.client.Invitation;
import at.romboe.abstimmung.model.client.Response;
import at.romboe.abstimmung.model.client.Vote;

@CrossOrigin(origins="http://localhost:3002")
@RestController
public class Controller {

	@Autowired
	private Service service;


	@GetMapping(value="/{id}")
	public ResponseEntity<Response> getVoting(@PathVariable String id) throws IOException {
		Response response = new Response();
		response.setName("" + id);

		String[] p = id.split(":");
		String votingId = p[0];
		String userId = p[1];

		Voting voting = service.getVoting(votingId);

		List<List<String>> rows = new ArrayList<>();

		List<String> row = new ArrayList<>();
		row.add("");
		for (Option o:voting.getOptions()) {
			row.add(o.getName());
		}
		rows.add(row);

		int i = 0;
		int enabledRow = -1;
		for (Map.Entry<String, User> e:voting.getVoters().entrySet()) {
			i++; // we start with 1 as the first row (index=0) are the option names

			String uuid = e.getKey();
			if (uuid.equals(userId)) {
				enabledRow = i;
			}

			row = new ArrayList<>();
			User user = e.getValue();
			row.add(user.getName());
			for (Option o:voting.getOptions()) {
				row.add(Boolean.toString( o.getVoters().contains(user) ));
			}
			rows.add(row);
		}

		response.setName(voting.getName());
		response.setRows(rows);
		response.setEnabledRow(enabledRow);

		return ResponseEntity.ok(response);
	}


	@PutMapping(value="/votes")
	public ResponseEntity<String> vote(@RequestBody Vote vote) throws IOException {
		String votingId = vote.getVotingId();
		String userId = vote.getUserId();
		int optionIndex = vote.getOptionIndex();
		boolean value = vote.getValue();

		Voting voting = service.getVoting(votingId);

		User user = voting.getVoters().get(userId);

		// Option option = voting.getOptions().stream().filter(o -> o.getId().equals(optionId)).findFirst().orElse(null);
		Option option = null;
		for (int i=0; i<voting.getOptions().size(); i++) {
			if (i == optionIndex) {
				option = voting.getOptions().get(i);
				break;
			}
		}

		if (value) {
			option.getVoters().add(user);
		}
		else {
			option.getVoters().remove(user);
		}

		service.saveVoting(voting);

		return ResponseEntity.ok().build();
	}


	public ResponseEntity<String> invite(@RequestBody Invitation invitation) throws IOException {

		service.invite(invitation);

		return ResponseEntity.ok().build();
	}
}

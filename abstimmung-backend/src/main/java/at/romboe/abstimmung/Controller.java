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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.romboe.abstimmung.model.Option;
import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;
import at.romboe.abstimmung.model.client.ChangeUserNameInput;
import at.romboe.abstimmung.model.client.Invitation;
import at.romboe.abstimmung.model.client.Response;
import at.romboe.abstimmung.model.client.VoteInput;

@CrossOrigin(origins="http://localhost:3002")
@RestController
public class Controller {

	@Autowired
	private Service service;


	@GetMapping(value="/admin/votings")
	public ResponseEntity<List<Voting>> getAllVotings() throws IOException {
		List<Voting> votings = service.findAllVotings();
		return ResponseEntity.ok(votings);
	}


	@GetMapping(value="/{id}")
	public ResponseEntity<Response> getVoting(@PathVariable String id) throws IOException {
		Response response = new Response();
		response.setName("" + id);

		String votingId = null;
		String voterId = null;
		try {
			String[] p = id.split(":");
			votingId = p[0];
			voterId = p[1];
		}
		catch(Exception e) {
			throw new IllegalArgumentException();
		}

		Voting voting = service.findVoting(votingId);

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
			if (uuid.equals(voterId)) {
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
	public ResponseEntity<String> vote(@RequestBody VoteInput input) throws IOException {
		service.vote(input);
		return ResponseEntity.ok().build();
	}


	public ResponseEntity<String> invite(@RequestBody Invitation invitation) throws IOException {

		service.invite(invitation);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/users")
	public ResponseEntity<String> changeUserName(@RequestBody ChangeUserNameInput input) throws IOException {

		service.changeUserName(input);

		return ResponseEntity.ok().build();
	}
}

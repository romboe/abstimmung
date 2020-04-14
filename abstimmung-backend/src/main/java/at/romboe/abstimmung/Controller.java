package at.romboe.abstimmung;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;
import at.romboe.abstimmung.model.client.AddUserInput;
import at.romboe.abstimmung.model.client.ChangeUserNameInput;
import at.romboe.abstimmung.model.client.CreateVotingInput;
import at.romboe.abstimmung.model.client.Invitation;
import at.romboe.abstimmung.model.client.Response;
import at.romboe.abstimmung.model.client.VoteInput;

@CrossOrigin(origins= {"http://localhost:3000","http://localhost:3002"})
@RestController
public class Controller {

	@Autowired
	private Service service;


	@GetMapping(value="/admin/votings")
	public ResponseEntity<List<Voting>> getAllVotings() throws IOException {
		List<Voting> votings = service.findAllVotings();
		return ResponseEntity.ok(votings);
	}

	@GetMapping(value="/api/{id}")
	public ResponseEntity<Response> getVoting(@PathVariable String id) throws IOException {
		Response response = service.getVoting(id);
		return ResponseEntity.ok(response);
	}

	// @ApiOperation(value = "Votes.", notes = "Notes for Votes.")
	@PutMapping(value="/api/votes")
	public ResponseEntity<String> vote(@RequestBody VoteInput input) throws IOException {
		service.vote(input);
		return ResponseEntity.ok().build();
	}

	@PostMapping(value="/api/invite")
	public ResponseEntity<String> invite(@RequestBody Invitation invitation) throws IOException {
		service.invite(invitation);
		return ResponseEntity.ok().build();
	}

	@PutMapping(value="/api/users")
	public ResponseEntity<User> addUser(@RequestBody AddUserInput input) throws IOException, EntityExistsException {
		User user = service.addUser(input);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/api/users")
	public ResponseEntity<String> changeUserName(@RequestBody ChangeUserNameInput input) throws IOException {

		service.changeUserName(input);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/api")
	public ResponseEntity<String> createVoting(@RequestBody CreateVotingInput input) throws IOException {
		Voting voting = service.createVoting(input);
		return ResponseEntity.ok(voting.getId().toString() + ":" + voting.getCreator().getId().toString());
	}
}

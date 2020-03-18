package at.romboe.abstimmung;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;
import at.romboe.abstimmung.repos.UserRepository;
import at.romboe.abstimmung.repos.VotingRepository;

@Controller
public class Service {

	@Autowired
	private VotingRepository votingRepo;
	@Autowired
	private UserRepository userRepo;


	public Voting getVoting(String uuid) {
		return votingRepo.findById(UUID.fromString(uuid));
	}

	public List<Voting> getAllVotings() {
		return votingRepo.findAll();
	}

	public Voting saveVoting(Voting v) {
		return votingRepo.save(v);
	}

	public User getUser(long id) {
		Optional<User> o = userRepo.findById(id);
		return o.isPresent() ? o.get() : null;
	}
	

	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}
}

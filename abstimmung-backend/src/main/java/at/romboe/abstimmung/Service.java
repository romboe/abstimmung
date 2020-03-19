package at.romboe.abstimmung;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;
import at.romboe.abstimmung.model.client.Invitation;
import at.romboe.abstimmung.repos.UserRepository;
import at.romboe.abstimmung.repos.VotingRepository;

@Component
public class Service {

	@Autowired
	private VotingRepository votingRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private MailService mailService;


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

	public void invite(Invitation invitation) {
		Voting voting = getVoting(invitation.getVotingId());

		for (String email:invitation.getEmails()) {
			User user = findUserByEmail(email);
			if (null == user) {
				user = new User(email);
			}

			mailService.sendSimpleMessage(email, "Hallo", "Guten Tag");

			// if sending was successful put user into voter list
			if (!voting.getVoters().containsValue(user)) {
				voting.getVoters().put(UUID.randomUUID().toString(), user);
			}
		}
	}
}

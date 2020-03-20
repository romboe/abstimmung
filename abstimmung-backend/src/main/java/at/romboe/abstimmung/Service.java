package at.romboe.abstimmung;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.romboe.abstimmung.model.Option;
import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;
import at.romboe.abstimmung.model.client.ChangeUserNameInput;
import at.romboe.abstimmung.model.client.Invitation;
import at.romboe.abstimmung.model.client.VoteInput;
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


	public Voting findVoting(String uuid) {
		return votingRepo.findById(UUID.fromString(uuid));
	}

	public List<Voting> findAllVotings() {
		return votingRepo.findAll();
	}

	public Voting saveVoting(Voting v) {
		return votingRepo.save(v);
	}

	public User findUser(long id) {
		Optional<User> o = userRepo.findById(id);
		return o.isPresent() ? o.get() : null;
	}

	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public void vote(VoteInput input) {
		String votingId = input.getVotingId();
		String voterId = input.getVoterId();
		int optionIndex = input.getOptionIndex();
		boolean value = input.getValue();

		Voting voting = findVoting(votingId);

		User user = voting.getVoters().get(voterId);

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

		saveVoting(voting);
	}

	public void invite(Invitation invitation) {
		Voting voting = findVoting(invitation.getVotingId());

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

	public void changeUserName(ChangeUserNameInput input) {
		String votingId = input.getVotingId();
		String voterId = input.getVoterId();

		Voting v = findVoting(votingId);
		User user = v.getVoters().get(voterId);

		user.setName(input.getName());

		userRepo.save(user);
	}
}

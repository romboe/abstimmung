package at.romboe.abstimmung;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import at.romboe.abstimmung.model.Option;
import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;
import at.romboe.abstimmung.model.client.AddUserInput;
import at.romboe.abstimmung.model.client.ChangeUserNameInput;
import at.romboe.abstimmung.model.client.CreateVotingInput;
import at.romboe.abstimmung.model.client.Invitation;
import at.romboe.abstimmung.model.client.Response;
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

	public User saveUser(User u) {
		return userRepo.save(u);
	}

	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public Response getVoting(String id) {
		Response response = new Response();
		response.setName("" + id);

		String votingId = null;
		String voterId = null;
		try {
			if (id.indexOf(':') > 0) {
				String[] p = id.split(":");
				votingId = p[0];
				voterId = p[1];
			}
			else {
				votingId = id;
			}
		}
		catch(Exception e) {
			throw new IllegalArgumentException();
		}

		Voting voting = findVoting(votingId);

		List<List<String>> rows = new ArrayList<>();

		List<String> row = new ArrayList<>();
		row.add("");
		for (Option o:voting.getOptions()) {
			row.add(o.getName());
		}
		rows.add(row);

		int i = 0;
		int enabledRow = -1;
		for (User user:voting.getVoters()) {
			i++; // we start with 1 as the first row (index=0) are the option names

			String uuid = user.getId().toString();
			if (uuid.equals(voterId)) {
				enabledRow = i;
			}

			row = new ArrayList<>();
			row.add(user.getName());
			for (Option o:voting.getOptions()) {
				row.add(Boolean.toString( o.getVoters().contains(user) ));
			}
			rows.add(row);
		}

		response.setName(voting.getName());
		response.setDescription(voting.getDescription());
		response.setAdmin(voting.getCreator().getId().toString().equals(voterId));
		response.setRows(rows);
		response.setEnabledRow(enabledRow);

		return response;
	}

	public void vote(VoteInput input) {
		String votingId = input.getVotingId();
		String voterId = input.getVoterId();
		int optionIndex = input.getOptionIndex();
		boolean value = input.getValue();

		Voting voting = findVoting(votingId);

		User user = findUserInVoting(voting, voterId);

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
			voting.getVoters().add(user);
		}
	}

	public User addUser(AddUserInput input) throws EntityExistsException {
		Voting voting = findVoting(input.getVotingId());
		boolean emailExists = voting.getVoters().stream().anyMatch(v -> v.getEmail().equals(input.getEmail()));
		if (emailExists) {
			throw new EntityExistsException();
		}
		User user = new User(input.getName(), input.getEmail());
		userRepo.save(user);
		voting.getVoters().add(user);
		votingRepo.save(voting);
		return user;
	}

	public void changeUserName(ChangeUserNameInput input) {
		Voting voting = findVoting(input.getVotingId());
		// we are only allowed to change the name if the user belongs to the voting
		User user = findUserInVoting(voting, input.getUserId());
		user.setName(input.getName());
		userRepo.save(user);
	}

	// https://www.baeldung.com/transaction-configuration-with-jpa-and-spring
	@Transactional
	public Voting createVoting(CreateVotingInput input) {
		Voting v = new Voting();
		User user = new User(input.getCreatorName(), input.getCreatorEmail());
		userRepo.save(user);
		v.setCreator(user);
		v.setName(input.getVotingName());
		v.setDescription(input.getDescription());
		List<Option> options = new ArrayList<>();
		for (String o:input.getOptions()) {
			options.add(new Option(o));
		}
		v.setOptions(options);
		saveVoting(v);
		return v;
	}

	private User findUserInVoting(Voting voting, String userId) {
		return voting.getVoters().stream().filter(x -> x.getId().toString().equals(userId)).findFirst().get();
	}

	@Transactional
	public String dump(UUID votingId) {
		Voting v = votingRepo.findById(votingId);
		StringBuilder sb = new StringBuilder();
		sb.append("=============================================").append("\n");
		sb.append(v.getId()+ " " + v.getName()).append("\n");
		sb.append("- Description -------------------------------").append("\n");
		sb.append(v.getDescription()).append("\n");
		sb.append("- Options -----------------------------------").append("\n");
		for (Option o:v.getOptions()) {
			sb.append(o.getName()).append("\n");
		}
		sb.append("- Voters ------------------------------------").append("\n");
		for (User voter:v.getVoters()) {
			User user = userRepo.findOne(Example.of(voter)).get();
			sb.append(user.getId()).append(" | ")
			.append(user.getCreationDateTime()).append(" | ")
			.append(user.getName()).append(" | ")
			.append(user.getEmail()).append("\n");
		}
		sb.append("=============================================").append("\n");
		return sb.toString();
	}
}

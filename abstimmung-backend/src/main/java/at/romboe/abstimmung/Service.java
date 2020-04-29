package at.romboe.abstimmung;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import at.romboe.abstimmung.config.ConfigProperties;
import at.romboe.abstimmung.model.Option;
import at.romboe.abstimmung.model.Voter;
import at.romboe.abstimmung.model.Voting;
import at.romboe.abstimmung.model.client.AddVoterInput;
import at.romboe.abstimmung.model.client.ChangeVoterNameInput;
import at.romboe.abstimmung.model.client.CreateVotingInput;
import at.romboe.abstimmung.model.client.Invitation;
import at.romboe.abstimmung.model.client.Response;
import at.romboe.abstimmung.model.client.VoteInput;
import at.romboe.abstimmung.repos.VoterRepository;
import at.romboe.abstimmung.repos.VotingRepository;

@Component
public class Service {

	@Autowired
	private VotingRepository votingRepo;
	@Autowired
	private VoterRepository voterRepo;
	@Autowired
	private MailService mailService;
	@Autowired
	private ConfigProperties props;


	public Voting findVoting(String uuid) {
		return votingRepo.findById(UUID.fromString(uuid));
	}

	public List<Voting> findAllVotings() {
		return votingRepo.findAll();
	}

	public Voting saveVoting(Voting v) {
		return votingRepo.save(v);
	}

	public Voter saveVoter(Voter u) {
		return voterRepo.save(u);
	}

	public Voter findVoterByEmail(String email) {
		return voterRepo.findByEmail(email);
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
		for (Voter voter:voting.getVoters()) {
			i++; // we start with 1 as the first row (index=0) are the option names

			String uuid = voter.getId().toString();
			if (uuid.equals(voterId)) {
				enabledRow = i;
			}

			row = new ArrayList<>();
			row.add(voter.getName());
			for (Option o:voting.getOptions()) {
				row.add(Boolean.toString( o.getVoters().contains(voter) ));
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

		Voter voter = findVoterInVoting(voting, voterId);

		Option option = null;
		for (int i=0; i<voting.getOptions().size(); i++) {
			if (i == optionIndex) {
				option = voting.getOptions().get(i);
				break;
			}
		}

		if (value) {
			option.getVoters().add(voter);
		}
		else {
			option.getVoters().remove(voter);
		}

		saveVoting(voting);
	}

	public void invite(Invitation invitation) {
		Voting voting = findVoting(invitation.getVotingId());

		for (String email:invitation.getEmails()) {
			Voter voter = findVoterByEmail(email);
			if (null == voter) {
				voter = new Voter(email);
				saveVoter(voter);
				voting.getVoters().add(voter);
				saveVoting(voting);
			}

			String text = "Hallöle!\nDu wurdest zu einer Abstimmung eingeladen: ";
			text += buildUrl(invitation.getVotingId(), voter.getId().toString());
			mailService.sendSimpleMessage(email, "Abstimmung Einladung", text);
		}
	}
	
	private String buildUrl(String votingId, String voterId) {
		return props.getClient().getUrl() + ":" + props.getClient().getPort() + "/votings/" + votingId + voterId; 
	}

	public Voter addVoter(AddVoterInput input) throws EntityExistsException {
		Voting voting = findVoting(input.getVotingId());
		boolean emailExists = voting.getVoters().stream().anyMatch(v -> v.getEmail().equals(input.getEmail()));
		if (emailExists) {
			throw new EntityExistsException();
		}
		Voter voter = new Voter(input.getName(), input.getEmail());
		voterRepo.save(voter);
		voting.getVoters().add(voter);
		votingRepo.save(voting);
		return voter;
	}

	public void changeVoterName(ChangeVoterNameInput input) {
		Voting voting = findVoting(input.getVotingId());
		// we are only allowed to change the name if the voter belongs to the voting
		Voter voter = findVoterInVoting(voting, input.getVoterId());
		voter.setName(input.getName());
		voterRepo.save(voter);
	}

	// https://www.baeldung.com/transaction-configuration-with-jpa-and-spring
	@Transactional
	public Voting createVoting(CreateVotingInput input) {
		Voting v = new Voting();
		Voter voter = new Voter(input.getCreatorName(), input.getCreatorEmail());
		voterRepo.save(voter);
		v.setCreator(voter);
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

	private Voter findVoterInVoting(Voting voting, String voterId) {
		return voting.getVoters().stream().filter(x -> x.getId().toString().equals(voterId)).findFirst().get();
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
		for (Voter voter:v.getVoters()) {
			Voter voter2 = voterRepo.findOne(Example.of(voter)).get();
			sb.append(voter2.getId()).append(" | ")
			.append(voter2.getCreationDateTime()).append(" | ")
			.append(voter2.getName()).append(" | ")
			.append(voter2.getEmail()).append("\n");
		}
		sb.append("=============================================").append("\n");
		return sb.toString();
	}
}

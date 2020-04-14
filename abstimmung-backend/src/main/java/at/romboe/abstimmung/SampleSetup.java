package at.romboe.abstimmung;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import at.romboe.abstimmung.model.Option;
import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;

@Component
public class SampleSetup {

	@Autowired
	private Service service;


	public void run() {
		Voting v = new Voting("badheizkoerper");
		v.setDescription("eine erste Umfrage");
		User creator = service.saveUser(new User("Pharao", "tut@amun"));v.setCreator(creator);service.saveVoting(v);

		// add options
		List<Option> options = new ArrayList<>();options.add(new Option("Eins"));options.add(new Option("Zwei"));v.setOptions(options);
		
		// send invitations
		List<User> voters = new ArrayList<>();voters.add(new User("hallo@servus.at"));voters.add(new User("Kasperl","petzi@baer.at"));voters.add(new User("Jane","jane@tarzan.at"));v.setVoters(voters);v.getVoters().add(v.getCreator());v=service.saveVoting(v);

		for(Voting v1:service.findAllVotings())
		{
			System.out.println(service.dump(v1.getId()));
		}

		// user calls his link
		// 1. Voting holen
		printVotes(v);

		// User gibt Stimme ab
		long optionId = 1;
		User user = service.findUserByEmail("hallo@servus.at");
		Option op = v.getOptions().stream().filter(o -> o.getId().equals(optionId)).findFirst().get();
		op.getVoters().add(user);
	
		v = service.saveVoting(v);
		printVotes(v);
	}

	private static void printVotes(Voting v) {
		// Options zeilenweise für jeden User aufbereiten
		for (User u:v.getVoters()) {
			StringBuilder sb = new StringBuilder();
			sb.append(u.getName());
			sb.append(" ");
			for (Option o:v.getOptions()) {
				sb.append(o.getName());
				sb.append("=");
				sb.append(o.getVoters().contains(u));
				sb.append(", ");
			}
			sb.append("\n");
			System.out.println(sb.toString());
		}
	}
}

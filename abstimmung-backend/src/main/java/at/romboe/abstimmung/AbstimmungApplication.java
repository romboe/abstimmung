package at.romboe.abstimmung;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import at.romboe.abstimmung.model.Option;
import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;

@SpringBootApplication
public class AbstimmungApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AbstimmungApplication.class, args);
		Service service = context.getBean(Service.class);
		Voting v = new Voting("badheizkoerper");
		v.setDescription("eine erste Umfrage");

		// add options
		List<Option> options = new ArrayList<>();
		options.add(new Option("Eins"));
		options.add(new Option("Zwei"));
		v.setOptions(options);

		// send invitations
		Map<String,User> map = new LinkedHashMap<>();
		map.put(UUID.randomUUID().toString(), new User("hallo@servus.at"));
		map.put(UUID.randomUUID().toString(), new User("Kasperl", "petzi@baer.at"));
		map.put(UUID.randomUUID().toString(), new User("Jane", "jane@tarzan.at"));
		v.setVoters(map);
		v = service.saveVoting(v);

		for (Voting v1:service.findAllVotings()) {
			System.out.println(v1.dump());
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

		// Client
		// * Users Stimme editierbar darstellen, dazu in den Option-Votings nach seiner Id suchen
	}


	private static void printVotes(Voting v) {
		// Options zeilenweise für jeden User aufbereiten
		for (Map.Entry<String, User> u:v.getVoters().entrySet()) {
			StringBuilder sb = new StringBuilder();
			sb.append(u.getValue().getName());
			sb.append(" ");
			for (Option o:v.getOptions()) {
				sb.append(o.getName());
				sb.append("=");
				sb.append(o.getVoters().contains(u.getValue()));
				sb.append(", ");
			}
			sb.append("\n");
			System.out.println(sb.toString());
		}
	}

//	@Bean
//	public CommandLineRunner demo(VotingRepository repo) {
//		return (args) -> {
//			repo.save(new Voting("Jack", "Bauer"));
//			repository.save(new Customer("Chloe", "O'Brian"));
//			repository.save(new Customer("Kim", "Bauer"));
//			repository.save(new Customer("David", "Palmer"));
//			repository.save(new Customer("Michelle", "Dessler"));
//
//			// fetch all customers
//			log.info("Customers found with findAll():");
//			log.info("-------------------------------");
//			for (Customer customer : repository.findAll()) {
//				log.info(customer.toString());
//			}
//			log.info("");
//
//			// fetch an individual customer by ID
//			Customer customer = repository.findById(1L);
//			log.info("Customer found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(customer.toString());
//			log.info("");
//
//			// fetch customers by last name
//			log.info("Customer found with findByLastName('Bauer'):");
//			log.info("--------------------------------------------");
//			repository.findByLastName("Bauer").forEach(bauer -> {
//				log.info(bauer.toString());
//			});
//			// for (Customer bauer : repository.findByLastName("Bauer")) {
//			// log.info(bauer.toString());
//			// }
//			log.info("");
//		};
//	}
}

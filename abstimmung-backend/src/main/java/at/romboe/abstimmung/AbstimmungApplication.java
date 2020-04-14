package at.romboe.abstimmung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import at.romboe.abstimmung.config.ConfigInfo;

@SpringBootApplication
public class AbstimmungApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AbstimmungApplication.class, args);
		SampleSetup setup = context.getBean(SampleSetup.class);
		setup.run();
		
		ConfigInfo info = context.getBean(ConfigInfo.class);
		info.print();
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

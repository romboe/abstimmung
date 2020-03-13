package at.romboe.abstimmung.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.romboe.abstimmung.model.User;
import at.romboe.abstimmung.model.Voting;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}

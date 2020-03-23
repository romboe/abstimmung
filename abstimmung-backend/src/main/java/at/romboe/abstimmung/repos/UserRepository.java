package at.romboe.abstimmung.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.romboe.abstimmung.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByEmail(String email);
}

package at.romboe.abstimmung.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.romboe.abstimmung.model.Voter;

@Repository
public interface VoterRepository extends JpaRepository<Voter, String> {
	Voter findByEmail(String email);
}

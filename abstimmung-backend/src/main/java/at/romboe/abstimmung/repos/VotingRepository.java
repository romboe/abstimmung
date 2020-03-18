package at.romboe.abstimmung.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.romboe.abstimmung.model.Voting;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {
	Voting findById(UUID uuid);
}

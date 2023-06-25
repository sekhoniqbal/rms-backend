package ca.ehealthsask.rmsdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.ehealthsask.rmsdemo.entities.Referral;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {
}

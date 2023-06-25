package ca.ehealthsask.rmsdemo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.ehealthsask.rmsdemo.entities.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    public Optional<Speciality> findFirstByName(String name);
}

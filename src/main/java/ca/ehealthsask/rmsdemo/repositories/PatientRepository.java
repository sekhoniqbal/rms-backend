package ca.ehealthsask.rmsdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.ehealthsask.rmsdemo.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Patient find(Patient patient);
}

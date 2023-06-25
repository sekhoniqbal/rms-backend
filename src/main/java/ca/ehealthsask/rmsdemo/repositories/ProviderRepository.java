package ca.ehealthsask.rmsdemo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.ehealthsask.rmsdemo.entities.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    public List<Provider> findAllBySpecialityId(Long id);
}

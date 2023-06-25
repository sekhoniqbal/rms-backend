package ca.ehealthsask.rmsdemo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ca.ehealthsask.rmsdemo.dtos.ProviderDto;
import ca.ehealthsask.rmsdemo.entities.Provider;
import ca.ehealthsask.rmsdemo.entities.Speciality;
import ca.ehealthsask.rmsdemo.repositories.ProviderRepository;
import ca.ehealthsask.rmsdemo.repositories.SpecialityRepository;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    SpecialityRepository specialityRepository;

    @GetMapping("")
    public List<Provider> getProvider() {
        return providerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Provider getProvider(@PathVariable("id") Long id) {
        return findProviderByIdOrThrow(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Provider createProvider(@Validated @RequestBody ProviderDto providerDto) {
        return updateAndSaveProvider(new Provider(), providerDto);
    }

    @PutMapping("/{id}")
    public Provider updateProvider(@Validated @RequestBody ProviderDto providerDto, @PathVariable Long id) {
        return updateAndSaveProvider(findProviderByIdOrThrow(id), providerDto);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProvider(@PathVariable("id") Long id) {
        providerRepository.delete(findProviderByIdOrThrow(id));
    }

    private Speciality findSpecialityByIdOrThrow(Long id) {
        return specialityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Speciality Not Found"));
    }

    private Provider findProviderByIdOrThrow(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider Not Found"));
    }

    private Provider updateAndSaveProvider(Provider provider, ProviderDto providerDto) {
        Speciality speciality = findSpecialityByIdOrThrow(providerDto.getSpecialityId());
        provider.setSpeciality(speciality);
        provider.setName(providerDto.getName());
        provider.setIsAcceptingPatients(providerDto.getIsAcceptingPatients());
        System.out.println(providerDto.getIsAcceptingPatients());
        return providerRepository.save(provider);

    }

}

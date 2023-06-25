package ca.ehealthsask.rmsdemo.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.sound.midi.Soundbank;

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

import ca.ehealthsask.rmsdemo.dtos.SpecialityDto;
import ca.ehealthsask.rmsdemo.entities.Provider;
import ca.ehealthsask.rmsdemo.entities.Speciality;
import ca.ehealthsask.rmsdemo.repositories.ProviderRepository;
import ca.ehealthsask.rmsdemo.repositories.SpecialityRepository;

@RestController
@RequestMapping("/api/specialities")
public class SpecialityController {

    @Autowired
    SpecialityRepository specialityRepository;

    @Autowired
    ProviderRepository providerRepository;

    @GetMapping("")
    public List<Speciality> getSpeciality() {
        return specialityRepository.findAll();
    }

    @GetMapping("/{id}")
    public Speciality getSpeciality(@PathVariable("id") Long id) {
        return findSpecialityByIdOrThrow(id);
    }

    @GetMapping("/{id}/suggestProvider")
    public List<Provider> getsuggestProviders(@PathVariable("id") Long id) {
        Speciality speciality = findSpecialityByIdOrThrow(id);
        List<Provider> providers = providerRepository.findAllBySpecialityId(speciality.getId())
                .stream()
                .filter(provider -> provider.getIsAcceptingPatients() != null)
                .filter(provider -> provider.getIsAcceptingPatients() == true)
                .sorted((provider1, provider2) -> provider1.getReferrals().size() - provider2.getReferrals().size())
                .limit(3).collect(Collectors.toList());
        providers.forEach(p -> System.out.println(p.getReferrals().size()));
        return providers;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Speciality createSpeciality(@Validated @RequestBody SpecialityDto specialityDto) {
        return updateAndSave(new Speciality(), specialityDto);
    }

    @PutMapping("/{id}")
    public Speciality updateSpeciality(@Validated @RequestBody SpecialityDto specialityDto, @PathVariable Long id) {
        return updateAndSave(findSpecialityByIdOrThrow(id), specialityDto);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteSpeciality(@PathVariable("id") Long id) {
        specialityRepository.delete(findSpecialityByIdOrThrow(id));
    }

    // helpers
    private Speciality updateAndSave(Speciality speciality, SpecialityDto specialityDto) {
        Speciality specialityWithSameName = specialityRepository.findFirstByName(specialityDto.getName().trim())
                .orElse(null);

        if (specialityWithSameName != null && specialityWithSameName.getId() != speciality.getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Another Speciality with same name already exists");

        speciality.setName(specialityDto.getName().trim());
        return specialityRepository.save(speciality);
    }

    private Speciality findSpecialityByIdOrThrow(Long id) {
        return specialityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Speciality Not Found"));
    }

}

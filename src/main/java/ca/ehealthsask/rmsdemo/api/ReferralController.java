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

import ca.ehealthsask.rmsdemo.dtos.ReferralDto;
import ca.ehealthsask.rmsdemo.entities.Patient;
import ca.ehealthsask.rmsdemo.entities.Provider;
import ca.ehealthsask.rmsdemo.entities.Referral;
import ca.ehealthsask.rmsdemo.entities.Speciality;
import ca.ehealthsask.rmsdemo.repositories.PatientRepository;
import ca.ehealthsask.rmsdemo.repositories.ProviderRepository;
import ca.ehealthsask.rmsdemo.repositories.ReferralRepository;
import ca.ehealthsask.rmsdemo.repositories.SpecialityRepository;

@RestController
@RequestMapping("/api/referrals")
public class ReferralController {

        @Autowired
        ReferralRepository referralRepository;
        @Autowired
        ProviderRepository providerRepository;
        @Autowired
        PatientRepository patientRepository;
        @Autowired
        SpecialityRepository specialityRepository;

        @GetMapping("")
        public List<Referral> getReferral() {
                return referralRepository.findAll();
        }

        @GetMapping("/{id}")
        public Referral getReferral(@PathVariable("id") Long id) {
                return referralRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Referral Not Found"));
        }

        @PostMapping("")
        @ResponseStatus(HttpStatus.CREATED)
        public Referral createReferral(@Validated @RequestBody ReferralDto referralDto) {
                return updateAndSave(new Referral(), referralDto);
        }

        @PutMapping("/{id}")
        public Referral updateReferral(@Validated @RequestBody ReferralDto referralDto, @PathVariable Long id) {
                Referral referral = referralRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Referral Not Found"));
                return updateAndSave(referral, referralDto);
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        public void deleteReferral(@PathVariable("id") Long id) {
                Referral referral = referralRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Referral Not Found"));
                referralRepository.delete(referral);
        }

        // helpers
        private Referral updateAndSave(Referral referral, ReferralDto referralDto) {
                // throw error if patient not found
                Patient patient = patientRepository.findById(referralDto.getPatientId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Patient Not Found"));
                // throw error if patient already has another referral for the same speciality
                Referral existingReferral = patient.getReferrals().stream()
                                .filter(r -> r.getSpeciality().getId() == referralDto.getSpecialityId())
                                .findFirst().orElse(null);
                if (existingReferral != null && existingReferral.getId() != referral.getId()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Patient already has an existing refferal for this speciality");
                }
                // throw error if provider does not exist
                Provider provider = providerRepository.findById(referralDto.getProviderId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Provider Not Found"));
                // throw error if provider is not accepting patients
                if (provider.getIsAcceptingPatients() == null || provider.getIsAcceptingPatients() == false) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Selected provider is not selecting new patients");
                }

                // thorw error if speciality does not exist
                Speciality speciality = specialityRepository.findById(referralDto.getSpecialityId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Speciality Not Found"));
                // throw error if patient and speciality miss match
                if (provider.getSpeciality().getId() != speciality.getId()) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Provider and Referral speciality miss match");
                }
                referral.setPatient(patient);
                referral.setProvider(provider);
                referral.setSpeciality(speciality);
                return referralRepository.save(referral);
        }

}

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

import ca.ehealthsask.rmsdemo.dtos.PatientDto;
import ca.ehealthsask.rmsdemo.entities.Patient;
import ca.ehealthsask.rmsdemo.entities.Referral;
import ca.ehealthsask.rmsdemo.entities.Speciality;
import ca.ehealthsask.rmsdemo.repositories.PatientRepository;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("")
    public List<Patient> getPatient() {
        return patientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable("id") Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));
    }

    @GetMapping("/{id}/referrals")
    public List<Referral> getPatientReferrals(@PathVariable("id") Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"))
                .getReferrals();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient createPatient(@Validated @RequestBody PatientDto patient) {
        Patient newPatient = new Patient();
        newPatient.setName(patient.getName());
        return patientRepository.save(newPatient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@Validated @RequestBody PatientDto patientDto, @PathVariable Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));
        patient.setName(patientDto.getName());
        return patientRepository.save(patient);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable("id") Long id) {
        Patient patient = findPatientByIdOrThrow(id);
        if (!patient.getReferrals().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot delete Patient: Patient has " + patient.getReferrals().size() + "referrals");
        }
        patientRepository.delete(patient);
    }

    // helper
    private Patient findPatientByIdOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));
    }

}

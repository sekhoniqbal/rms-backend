package ca.ehealthsask.rmsdemo.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReferralDto {
    @NotNull(message = "Patient cannot be blank")
    private Long patientId;

    @NotNull(message = "Provider cannot be blank")
    private Long providerId;

    @NotNull(message = "Speciality cannot be blank")
    private Long specialityId;
}

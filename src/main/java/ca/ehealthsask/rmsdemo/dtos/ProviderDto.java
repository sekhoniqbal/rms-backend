package ca.ehealthsask.rmsdemo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ProviderDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Speciality cannot be blank")
    private Long specialityId;
    @NotNull(message = "Please specify if provider is accepting new patients")
    private Boolean isAcceptingPatients;
}

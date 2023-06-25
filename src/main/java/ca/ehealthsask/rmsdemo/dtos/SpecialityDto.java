package ca.ehealthsask.rmsdemo.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SpecialityDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;
}

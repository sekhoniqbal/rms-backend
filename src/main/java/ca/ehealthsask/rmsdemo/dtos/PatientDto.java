package ca.ehealthsask.rmsdemo.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PatientDto {

    @NotBlank(message = "Name cannot be blank")
    @Length(min = 2, max = 30, message = "Name can only be 2 to 30 characters long")
    private String name;
}

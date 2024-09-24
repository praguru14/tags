package org.pg.medtag.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedInfoDTO {
    private Long id;

    @NotBlank(message = "Medication must not be blank")
    @Size(max = 255, message = "Medication name must not exceed 255 characters")
    private String medication;

    @NotBlank(message = "Dosage must not be blank")
    private String dosage;

    public MedInfoDTO(Long id, @NotBlank @Size(max = 255) String medication, @NotBlank String dosage) {
        this.id = id;
        this.medication = medication;
        this.dosage = dosage;
    }
}

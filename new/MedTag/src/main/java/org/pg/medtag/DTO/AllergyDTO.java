package org.pg.medtag.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AllergyDTO {
    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    private String severity;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;  // New field

    // Constructor to initialize fields
    public AllergyDTO(Long id, @NotBlank @Size(max = 100) String name, String severity, String description) {
        this.id = id;
        this.name = name;
        this.severity = severity;
        this.description = description;  // Initialize new field
    }
}

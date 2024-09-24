package org.pg.medtag.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmergencyContactDTO {
    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    @NotBlank(message = "Phone must not be blank")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "\\d{10}", message = "Phone number must contain exactly 10 digits")
    private String phone;

    private String relationship;

    // Constructor with parameters to initialize fields
    public EmergencyContactDTO(Long id, String name, String phone, String relationship) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.relationship = relationship;
    }
}

package org.pg.medtag.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.pg.medtag.Model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] imageData;

    @NotBlank
    @Size(max = 120)
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 10)
    @Pattern(regexp = "\\d{10}", message = "Phone number must contain exactly 10 digits")
    private String phone;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Column(name = "blood_group")
    @Pattern(regexp = "^(A\\+|A\\-|B\\+|B\\-|O\\+|O\\-|AB\\+|AB\\-|N/A)$", message = "Invalid blood group")
    private String bloodGroup;

    private LocalDate dob;

    @Pattern(regexp = "^[MFO]$", message = "Gender must be 'M', 'F', or 'O'")
    private String gender;

    // One-to-Many relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore // Prevent serialization of the list in User
    private List<EmergencyContact> emergencyContacts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore // Prevent serialization of the list in User
    private List<Allergy> allergies;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore // Prevent serialization of the list in User
    private List<MedInfo> medInfos;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore // Prevent serialization of the list in User
    private List<InsuranceInformation> insuranceInformation;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore // Prevent serialization of the list in User
    private List<MedicalCondition> medicalConditions;

    // Other fields
    @Column(name = "registration_dttm")
    private LocalDateTime registrationDttm;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "last_login_dttm")
    private LocalDateTime lastLoginDttm;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @Column(name = "user_type")
    private String userType;
}

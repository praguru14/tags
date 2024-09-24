package org.pg.medtag.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String phone;
    private String name;
    private String bloodGroup;
    private LocalDate dob;
    private String gender;
    private LocalDateTime registrationDttm;
    private Boolean isVerified;
    private Boolean isActive;
    private LocalDateTime lastLoginDttm;
    private Boolean isBanned;
    private String userType;

    // Lists of related entities
    private List<EmergencyContactDTO> emergencyContacts;
    private List<AllergyDTO> allergies;
    private List<MedInfoDTO> medInfos;
    private List<InsuranceInformationDTO> insuranceInformation;
    private List<MedicalConditionDTO> medicalConditions;
}


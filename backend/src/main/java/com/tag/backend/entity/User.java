package com.tag.backend.entity;

import com.tag.backend.model.UserModel;
import jakarta.persistence.*;


import jakarta.validation.constraints.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.*;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.math.BigDecimal;


@Entity
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 3)
    @Pattern(regexp = "^(A\\+|A\\-|B\\+|B\\-|O\\+|O\\-|AB\\+|AB\\-)$", message = "Invalid blood group")
    private String bloodGroup;

    private Date dob;
    
    @Pattern(regexp = "^[MFO]$", message = "Gender must be 'M', 'F', or 'O'")
    private String gender;

    private String hairColor;

    private String eyeColor;

    private BigDecimal height;

    private BigDecimal weight;

    private String identificationMark;

    private String ecName;

    private String ecRelationship;

    @Size(min = 10, max = 10)
    @Pattern(regexp = "\\d{10}", message = "Phone number must contain exactly 10 digits")
    private String ecNumber;

    private String address;

    private String city;

    private String state;

    private String zip;

    private String country;

    private String allergyName;

    private String allergyNotes;

    private String medicineName;

    private String medicineNotes;

    private BigDecimal medicineDosage;

    private String medicineDosageUnit;

    private Integer medicineFrequency;

    private String medicineFrequencyType;

    private String insuranceName;

    private String insuranceNotes;

    private String medicalConditionName;

    private String medicalConditionNotes;

    private Date registrationDttm;

    private Boolean isVerified;

    private Boolean isActive;

    private String profilePhoto;

    private Date lastLoginDttm;

    private Boolean isBanned;

    private String middleName;

    private String userType;

    public UserModel convertToModel(UserModel userModel) {
        BeanUtils.copyProperties(this, userModel);
        return userModel;
    }
}
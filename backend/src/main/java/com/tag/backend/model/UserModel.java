package com.tag.backend.model;

import com.tag.backend.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class UserModel {
    private long id;

    private String email;

    private String phone;

    private String name;

    private String bloodGroup;

    private Date dob;

    private String gender;

    private String hairColor;

    private String eyeColor;

    private BigDecimal height;

    private BigDecimal weight;

    private String identificationMark;

    private String ecName;

    private String ecRelationship;

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

    public User convertToEntity(User user) {
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
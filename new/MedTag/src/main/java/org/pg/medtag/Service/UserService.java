package org.pg.medtag.Service;

import jakarta.validation.Valid;
import org.pg.medtag.DTO.*;

import org.pg.medtag.Entity.*;
import org.pg.medtag.Model.*;
import org.pg.medtag.Repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private MedInfoRepository medInfoRepository;

    @Autowired
    private InsuranceInformationRepository insuranceInformationRepository;

    @Autowired
    private MedicalConditionRepository medicalConditionRepository;

    @Scheduled(cron = "*/30 * * * * *")
    @Transactional
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        logger.info("Scheduled task executed: {} users retrieved", users.size());
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO createUser(@Valid UserDTO userDTO) {
        User user = mapToEntity(userDTO);
        User savedUser = userRepository.save(user);

        saveRelatedEntities(userDTO, savedUser);

        userDTO.setId(savedUser.getId());
        return userDTO;
    }

    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setName(userDTO.getName());
        user.setBloodGroup(userDTO.getBloodGroup());
        user.setDob(userDTO.getDob());
        user.setGender(userDTO.getGender());
        user.setRegistrationDttm(userDTO.getRegistrationDttm());
        user.setIsVerified(userDTO.getIsVerified());
        user.setIsActive(userDTO.getIsActive());
        user.setLastLoginDttm(userDTO.getLastLoginDttm());
        user.setIsBanned(userDTO.getIsBanned());
        user.setUserType(userDTO.getUserType());
        return user;
    }

    private void saveRelatedEntities(UserDTO userDTO, User savedUser) {
        saveEmergencyContacts(userDTO.getEmergencyContacts(), savedUser);
        saveAllergies(userDTO.getAllergies(), savedUser);
        saveMedInfos(userDTO.getMedInfos(), savedUser);
        saveInsuranceInformation(userDTO.getInsuranceInformation(), savedUser);
        saveMedicalConditions(userDTO.getMedicalConditions(), savedUser);
    }

    private void saveEmergencyContacts(List<EmergencyContactDTO> emergencyContacts, User savedUser) {
        if (emergencyContacts != null) {
            emergencyContacts.forEach(ec -> {
                EmergencyContact emergencyContact = new EmergencyContact();
                emergencyContact.setName(ec.getName());
                emergencyContact.setPhone(ec.getPhone());
                emergencyContact.setRelationship(ec.getRelationship());
                emergencyContact.setUser(savedUser);
                emergencyContactRepository.save(emergencyContact);
            });
        }
    }

    private void saveAllergies(List<AllergyDTO> allergies, User savedUser) {
        if (allergies != null) {
            allergies.forEach(allergy -> {
                Allergy allergyEntity = new Allergy();
                allergyEntity.setName(allergy.getName());
                allergyEntity.setSeverity(allergy.getSeverity());
                allergyEntity.setUser(savedUser);
                allergyRepository.save(allergyEntity);
            });
        }
    }

    private void saveMedInfos(List<MedInfoDTO> medInfos, User savedUser) {
        if (medInfos != null) {
            medInfos.forEach(medInfo -> {
                MedInfo medInfoEntity = new MedInfo();
                medInfoEntity.setMedication(medInfo.getMedication());
                medInfoEntity.setDosage(medInfo.getDosage());
                medInfoEntity.setUser(savedUser);
                medInfoRepository.save(medInfoEntity);
            });
        }
    }

    private void saveInsuranceInformation(List<InsuranceInformationDTO> insuranceInformation, User savedUser) {
        if (insuranceInformation != null) {
            insuranceInformation.forEach(info -> {
                InsuranceInformation insuranceInfoEntity = new InsuranceInformation();
                insuranceInfoEntity.setInsuranceName(info.getInsuranceName());
                insuranceInfoEntity.setInsuranceNotes(info.getInsuranceNotes());
                insuranceInfoEntity.setUser(savedUser);
                insuranceInformationRepository.save(insuranceInfoEntity);
            });
        }
    }

    private void saveMedicalConditions(List<MedicalConditionDTO> medicalConditions, User savedUser) {
        if (medicalConditions != null) {
            medicalConditions.forEach(condition -> {
                MedicalCondition medicalConditionEntity = new MedicalCondition();
                medicalConditionEntity.setConditionName(condition.getConditionName());
                medicalConditionEntity.setSeverity(condition.getSeverity());
                medicalConditionEntity.setDescription(condition.getDescription());
                medicalConditionEntity.setUser(savedUser);
                medicalConditionRepository.save(medicalConditionEntity);
            });
        }
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setName(user.getName());
        userDTO.setBloodGroup(user.getBloodGroup());
        userDTO.setDob(user.getDob());
        userDTO.setGender(user.getGender());
        userDTO.setRegistrationDttm(user.getRegistrationDttm());
        userDTO.setIsVerified(user.getIsVerified());
        userDTO.setIsActive(user.getIsActive());
        userDTO.setLastLoginDttm(user.getLastLoginDttm());
        userDTO.setIsBanned(user.getIsBanned());
        userDTO.setUserType(user.getUserType());

        userDTO.setEmergencyContacts(user.getEmergencyContacts().stream()
                .map(ec -> new EmergencyContactDTO(ec.getId(), ec.getName(), ec.getPhone(), ec.getRelationship()))
                .collect(Collectors.toList()));

        userDTO.setAllergies(user.getAllergies().stream()
                .map(a -> new AllergyDTO(a.getId(), a.getName(), a.getSeverity()))
                .collect(Collectors.toList()));

        userDTO.setMedInfos(user.getMedInfos().stream()
                .map(m -> new MedInfoDTO(m.getId(), m.getMedication(), m.getDosage()))
                .collect(Collectors.toList()));

        userDTO.setInsuranceInformation(user.getInsuranceInformation().stream()
                .map(i -> new InsuranceInformationDTO(i.getId(), i.getInsuranceName(), i.getInsuranceNotes()))
                .collect(Collectors.toList()));

        userDTO.setMedicalConditions(user.getMedicalConditions().stream()
                .map(mc -> new MedicalConditionDTO(mc.getId(), mc.getConditionName(), mc.getSeverity(), mc.getDescription()))
                .collect(Collectors.toList()));

        return userDTO;
    }
}

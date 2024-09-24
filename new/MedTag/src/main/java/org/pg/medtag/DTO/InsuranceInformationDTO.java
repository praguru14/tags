package org.pg.medtag.DTO;

import lombok.Data;

@Data
public class InsuranceInformationDTO {
    private Long id;
    private String insuranceName;
    private String insuranceNotes;

    public InsuranceInformationDTO(Long id, String insuranceName, String insuranceNotes) {
        this.id = id;
        this.insuranceName = insuranceName;
        this.insuranceNotes = insuranceNotes;
    }
}

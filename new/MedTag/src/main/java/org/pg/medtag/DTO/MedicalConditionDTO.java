package org.pg.medtag.DTO;

import lombok.Data;

@Data
public class MedicalConditionDTO {
    private Long id;
    private String conditionName;
    private String severity;
    private String description;

    public MedicalConditionDTO(Long id, String conditionName, String severity, String description) {
        this.id = id;
        this.conditionName = conditionName;
        this.severity = severity;
        this.description = description;
    }
}

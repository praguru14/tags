package org.pg.medtag.Repository;

import org.pg.medtag.Model.MedicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long> {
}
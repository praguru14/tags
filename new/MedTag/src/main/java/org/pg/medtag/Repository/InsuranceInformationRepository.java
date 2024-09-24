package org.pg.medtag.Repository;

import org.pg.medtag.Model.InsuranceInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceInformationRepository extends JpaRepository<InsuranceInformation, Long> {
}
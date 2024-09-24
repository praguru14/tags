package org.pg.medtag.Repository;

import org.pg.medtag.Model.MedInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedInfoRepository extends JpaRepository<MedInfo, Long> {
}
package org.pg.medtag.Repository;

import org.pg.medtag.Entity.User;
import org.pg.medtag.Model.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {
}

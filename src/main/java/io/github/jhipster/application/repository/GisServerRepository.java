package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.GisServer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GisServer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GisServerRepository extends JpaRepository<GisServer, Long> {

}

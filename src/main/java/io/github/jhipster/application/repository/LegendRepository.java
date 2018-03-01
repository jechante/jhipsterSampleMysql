package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Legend;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Legend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LegendRepository extends JpaRepository<Legend, Long> {

}

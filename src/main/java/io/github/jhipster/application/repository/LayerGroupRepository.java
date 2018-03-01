package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.LayerGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LayerGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LayerGroupRepository extends JpaRepository<LayerGroup, Long> {

}

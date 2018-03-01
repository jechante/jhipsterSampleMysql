package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Layer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Layer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LayerRepository extends JpaRepository<Layer, Long> {

}

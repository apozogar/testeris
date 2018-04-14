package com.everis.testeris.repository;

import com.everis.testeris.domain.Paso;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Paso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PasoRepository extends JpaRepository<Paso, Long> {

}

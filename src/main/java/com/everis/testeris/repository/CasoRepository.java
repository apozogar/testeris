package com.everis.testeris.repository;

import com.everis.testeris.domain.Caso;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Caso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CasoRepository extends JpaRepository<Caso, Long> {

}

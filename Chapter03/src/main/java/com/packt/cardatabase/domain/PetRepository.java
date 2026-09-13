//Professor Henry Kang | CS 4010
// Gabriel J ~ Updated: Sep 7, 2026

// JPA repository for Pet table queries

package com.packt.cardatabase.domain;

import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends
        CrudRepository<Pet, Long> {

}

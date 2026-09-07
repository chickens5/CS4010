//Professor Henry Kang | CS 4010
// Gabriel J ~ Updated: Sep 7, 2026
package com.packt.cardatabase.domain;

import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends
        CrudRepository<Pet, Long> {

}

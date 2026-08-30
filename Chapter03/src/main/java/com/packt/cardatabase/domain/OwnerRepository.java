//Professor Henry Kang | CS 4010
// Gabriel J ~ Lat updated: August 30, 2026

package com.packt.cardatabase.domain;

import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends
        CrudRepository<Owner, Long> {
}

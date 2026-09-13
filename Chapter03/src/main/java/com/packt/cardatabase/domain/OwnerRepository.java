//Professor Henry Kang | CS 4010
// Gabriel J ~ Lat updated: August 30, 2026

// JPA repository for Owner table queries

package com.packt.cardatabase.domain;

import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends
        CrudRepository<Owner, Long> {
}

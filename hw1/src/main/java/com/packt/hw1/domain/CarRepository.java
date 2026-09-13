//Professor Henry Kang | CS 4010
// Gabriel J ~ Last updated: Sep 13, 2026

// JPA repository for Car table queries

package com.packt.hw1.domain;

import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends
        CrudRepository<Car, Long> {

}

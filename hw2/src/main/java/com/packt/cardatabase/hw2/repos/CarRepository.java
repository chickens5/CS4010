// Gabriel J 9/25/26 ~ 1200

package com.packt.cardatabase.hw2.repos;

import java.util.List;

import com.packt.cardatabase.hw2.models.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAll();
}

// Gabriel J 9/25/26 ~ 1200

package com.packt.cardatabase.hw2;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAll();
    List<Car> findByBrand(String brand);
    List<Car> findByColor(String color);
//    List<Car> findByModel(String model);
//    List<Car> findByModelYear(int modelYear);
}

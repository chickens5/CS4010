// Gabriel J 9/25/26 ~ 1200

package com.packt.cardatabase.hw2;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
    List<Owner> findAll();
}

// Gabriel J 10/25/26 ~ 1600 | Modified from Chp. 5

package com.cs4010.school.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
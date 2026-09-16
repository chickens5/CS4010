// Gabriel J 9/16/26 ~ 1630
package com.packt.cardatabase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@RestController
public class OwnerController {
    private final OwnerRepository repository;

    public OwnerController(OwnerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/owners")
    public Iterable<Owner> getOwner() {

        return repository.findAll();
        }

    }

// Gabriel J 9/27/26 ~ 1730

package com.packt.cardatabase.hw2.services;

import com.packt.cardatabase.hw2.models.Owner;
import com.packt.cardatabase.hw2.repos.OwnerRepository;
import com.packt.cardatabase.hw2.dto.OwnerRequest;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// OwnerService logic for Owner entity management
// Injected with OwnerRepository for CRUD operations
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // ====== CRUD Service Methods (uses OwnerRequest record standardization)=======
    // Get all owners & by id
    public List<Owner> getOwners() {
        return ownerRepository.findAll();
    }
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }
    // Creates Owner if id is unused
    public Owner addOwner(OwnerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("[addOwner] Owner request cannot be null");
        }
        if (request.id() != null && ownerRepository.existsById(request.id())) {
            throw new IllegalArgumentException("[addOwner] Owner with id " + request.id() + " already exists");
        }
        Owner owner = new Owner(request.firstname(), request.lastname());
        return ownerRepository.save(owner);
    }
    // Updates Owner if id matches
    public Owner updateOwner(Long id, OwnerRequest request) {
        if (id == null) {
            throw new IllegalStateException("[updateOwner] owner does not exist");
        }
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("[updateOwner] owner does not exist"));

        if (request != null) {
            if (request.firstname() != null) {
                existingOwner.setFirstname(request.firstname());
            }
            if (request.lastname() != null) {
                existingOwner.setLastname(request.lastname());
            }
        }

        return ownerRepository.save(existingOwner);
    }
    // Deletes Owner if id exists
    @Transactional
    public void deleteOwner(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new IllegalStateException("[deleteOwner] owner with id " + id + " does not exist");
        }
        ownerRepository.deleteById(id);
    }
}

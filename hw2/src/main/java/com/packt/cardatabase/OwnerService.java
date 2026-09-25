// Gabriel J 9/25/26 ~ 1200

package com.packt.cardatabase;

import com.packt.cardatabase.hw2.Owner;
import com.packt.cardatabase.hw2.OwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Owner Service layer class injected with ownerRepository for data access/service
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> getOwners() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    public Owner addOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Owner updateOwner(Long id, Owner owner) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("[updateOwner] owner with id " + id + " does not exist"));

        if (owner.getFirstname() != null) {
            existingOwner.setFirstname(owner.getFirstname());
        }
        if (owner.getLastname() != null) {
            existingOwner.setLastname(owner.getLastname());
        }

        return ownerRepository.save(existingOwner);
    }

    @Transactional
    public void deleteOwner(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new IllegalStateException("[deleteOwner] owner with id " + id + " does not exist");
        }
        ownerRepository.deleteById(id);
    }
}

// Gabriel J 9/22/26 ~ 1630

package com.packt.cardatabase.hw2.controllers;
import com.packt.cardatabase.hw2.models.Owner;

import com.packt.cardatabase.hw2.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// OwnerController handles HTTP REST requests for the Owner resource (/owners)
// Injected with OwnerService to delegate CRUD logic
@RestController
@RequestMapping(path = {"/owners"})
@CrossOrigin
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // ====== GET ==============
    @GetMapping
    public ResponseEntity<List<Owner>> getOwners() {
        List<Owner> owners = ownerService.getOwners();
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        Optional<Owner> owner = ownerService.getOwnerById(id);
        return owner.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // ==== POST, PUT, & DELETE =======
    @PostMapping
    public ResponseEntity<Owner> addOwner(@RequestBody Owner owner) {
        Owner createdOwner = ownerService.addOwner(owner);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        try {
            Owner updatedOwner = ownerService.updateOwner(id, owner);
            return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        try {
            ownerService.deleteOwner(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

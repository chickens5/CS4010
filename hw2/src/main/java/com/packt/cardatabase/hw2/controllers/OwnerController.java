// Gabriel J 9/27/26 ~ 1730

package com.packt.cardatabase.hw2.controllers;

import com.packt.cardatabase.hw2.models.Owner;
import com.packt.cardatabase.hw2.services.OwnerService;
import com.packt.cardatabase.hw2.dto.OwnerRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// Handles HTTP REST requests for Owner entity and
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

//====== CRUD Mappings return OwnerService method result as ResponseEntity =====

    // --- GET---
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

    //--- POST, PUT & DEL ---
    @PostMapping
    public ResponseEntity<?> addOwner(@RequestBody OwnerRequest request) {
        try {
            Owner createdOwner = ownerService.addOwner(request);
            return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @RequestBody OwnerRequest request) {
        try {
            Owner updatedOwner = ownerService.updateOwner(id, request);
            return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
        } catch (IllegalStateException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        try {
            ownerService.deleteOwner(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

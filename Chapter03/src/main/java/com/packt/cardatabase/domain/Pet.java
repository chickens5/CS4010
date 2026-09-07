// Gabriel J ~ updated: Sep 7, 2026
package com.packt.cardatabase.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Pet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name, species, dob;

	public Pet() {
	}

	public Pet(String name, String species, String dob, Owner owner) {
		super();
		this.name = name;
		this.species = species;
		this.dob  = dob;
		this.owner = owner;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	private Owner owner;

	// Getter and setter
	public Owner getOwner() {

		return owner;
	}

	public void setOwner(Owner owner) {

		this.owner = owner;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getSpecies() {

		return species;
	}

	public void setSpecies(String species) {

		this.species = species;
	}

	public String getDob() {

		return dob;
	}

	public void setDob(String dob) {

		this.dob = dob;
	}
}

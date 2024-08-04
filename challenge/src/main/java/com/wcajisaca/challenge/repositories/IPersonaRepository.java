package com.wcajisaca.challenge.repositories;

import com.wcajisaca.challenge.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface IPersonaRepository extends JpaRepository<Person, Serializable> {
}
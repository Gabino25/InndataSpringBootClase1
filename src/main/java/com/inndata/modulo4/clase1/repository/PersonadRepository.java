package com.inndata.modulo4.clase1.repository;

import com.inndata.modulo4.clase1.entity.PersonaD;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PersonadRepository extends JpaRepository<PersonaD, Integer> {

    public List<PersonaD> findByNombreEquals(String nombre);

}

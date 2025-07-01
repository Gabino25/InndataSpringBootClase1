package com.inndata.modulo4.clase1.repository;

import com.inndata.modulo4.clase1.entity.PersonadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PersonadRepository extends JpaRepository<PersonadEntity, Integer> {

    public List<PersonadEntity> findByNombreEquals(String nombre);

}

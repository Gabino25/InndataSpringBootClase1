package com.inndata.modulo4.clase1.repository;

import com.inndata.modulo4.clase1.entity.PersonadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PersonadRepository extends JpaRepository<PersonadEntity, Integer> {

    public List<PersonadEntity> findByNombreEquals(String nombre);

    // Realizar como tarea
    @Query(value = "select * from personad where nombre like %:nombre%", nativeQuery = true)
    public List<PersonadEntity> buscarPorNombre(String nombre);
}

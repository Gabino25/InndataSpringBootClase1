package com.inndata.modulo4.clase1.service;

import com.inndata.modulo4.clase1.entity.PersonadEntity;

import java.util.List;
import java.util.Optional;

public interface IPersonadService {

    // Metodos Read
    public List<PersonadEntity> readAll();

    public Optional<PersonadEntity> readById(Integer id);

    // Metodo create
    public PersonadEntity create(PersonadEntity personadEntity);

    // Metodo update
    public PersonadEntity update(PersonadEntity personadEntity);

    // Metodo Delete
    public String deletebyId(Integer id);

    // Metodo para buscar personas por nombre
    public List<PersonadEntity> findByNombreEquals(String nombre);

}

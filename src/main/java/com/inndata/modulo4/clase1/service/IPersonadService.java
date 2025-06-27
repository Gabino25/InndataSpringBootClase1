package com.inndata.modulo4.clase1.service;

import com.inndata.modulo4.clase1.entity.PersonaD;

import java.util.List;
import java.util.Optional;

public interface IPersonadService {

    // Metodos Read
    public List<PersonaD> readAll();

    public Optional<PersonaD> readById(Integer id);

    // Metodo create
    public PersonaD create(PersonaD personaD);

    // Metodo update
    public PersonaD update(PersonaD personaD);

    // Metodo Delete
    public String deletebyId(Integer id);

    // Metodo para buscar personas por nombre
    public List<PersonaD> findByNombreEquals(String nombre);
    
}

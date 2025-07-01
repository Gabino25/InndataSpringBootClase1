package com.inndata.modulo4.clase1.service.impl;

import com.inndata.modulo4.clase1.entity.PersonadEntity;
import com.inndata.modulo4.clase1.repository.PersonadRepository;
import com.inndata.modulo4.clase1.service.IPersonadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonadService implements IPersonadService {
    // Implementación de los métodos de IPersonadService

    @Autowired
    PersonadRepository personadRepository;

    @Override
    public List<PersonadEntity> readAll() {
        // Lógica para leer todas las personas
        return personadRepository.findAll(); // Reemplazar con la implementación real
    }

    @Override
    public Optional<PersonadEntity> readById(Integer id) {
        // Lógica para leer una persona por ID
        return personadRepository.findById(id); // Reemplazar con la implementación real
    }

    @Override
    public PersonadEntity create(PersonadEntity personadEntity) {
        // Lógica para crear una nueva persona

        if (personadEntity.getActivo() == null) {
            personadEntity.setActivo(true); // Asignar valor por defecto si no se especifica
        }
        personadRepository.save(personadEntity);

        return null; // Reemplazar con la implementación real
    }

    @Override
    public PersonadEntity update(PersonadEntity personadEntity) {
        // Lógica para actualizar una persona existente
        return personadRepository.save(personadEntity);
    }

    @Override
    public String deletebyId(Integer id) {
        // Lógica para eliminar una persona por ID

        Optional<PersonadEntity> persona = personadRepository.findById(id);
        if (persona.isPresent()) {
            PersonadEntity personaValor = persona.get();
            personaValor.setActivo(false); // Cambiamos el estado a inactivo Borrado Lógico
            personadRepository.save(personaValor);
            // personadRepository.deleteById(id); // Descomentar si se desea eliminar físicamente el registro
            return("La persona se elimino");
        }else{
            return("No existia la persona");
        }

    }

    @Override
    public List<PersonadEntity> findByNombreEquals(String nombre) {
        // Lógica para buscar personas por nombre
        return personadRepository.findByNombreEquals(nombre)
                                 .stream()
                                 .filter(persona -> persona.getActivo())
                                 .toList();
    }


}

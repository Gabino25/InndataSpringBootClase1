package com.inndata.modulo4.clase1.service.impl;

import com.inndata.modulo4.clase1.entity.DepartamentoEntity;
import com.inndata.modulo4.clase1.entity.PersonadEntity;
import com.inndata.modulo4.clase1.repository.DepartamentoRepository;
import com.inndata.modulo4.clase1.repository.PersonadRepository;
import com.inndata.modulo4.clase1.response.PersonadResponse;
import com.inndata.modulo4.clase1.service.IPersonadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PersonadService implements IPersonadService {
    // Implementación de los métodos de IPersonadService

    @Autowired
    PersonadRepository personadRepository;

    @Autowired
    DepartamentoRepository departamentoRepository;

    @Override
    public List<PersonadResponse> readAll() {

        Function<PersonadEntity, PersonadResponse> mapToResponse = new Function<PersonadEntity, PersonadResponse>() {
            @Override
            public PersonadResponse apply(PersonadEntity personadEntity) {
                PersonadResponse personadResponse = new PersonadResponse();
                personadResponse.setId(personadEntity.getId());
                personadResponse.setNombre(personadEntity.getNombre());
                personadResponse.setEdad(personadEntity.getEdad());
                personadResponse.setIdDepartamento(personadEntity.getIdDepartamento().getId());
                personadResponse.setPrecio(personadEntity.getIdDepartamento().getPrecio());
                return personadResponse;
            }
        };

        // Lógica para leer todas las personas
        return personadRepository.findAll()
                                 .stream()
                                 .filter(persona -> persona.getActivo())
                                 .map(mapToResponse)
                                 .toList(); // Reemplazar con la implementación real
    }

    @Override
    public Optional<PersonadEntity> readById(Integer id) {
        // Lógica para leer una persona por ID
        return personadRepository.findById(id); // Reemplazar con la implementación real
    }

    @Override
    public PersonadResponse create(PersonadEntity personadEntity) {
        // Lógica para crear una nueva persona

        try{
            if(personadEntity.getIdDepartamento()!=null){
                Optional<DepartamentoEntity> optDep = departamentoRepository.findById(personadEntity.getIdDepartamento().getId());
                if(optDep.isEmpty()){
                    System.out.println("Departamento no encontrado");
                }
                personadEntity.setIdDepartamento(optDep.get());
            }
            PersonadEntity personaGuardada = personadRepository.save(personadEntity);
        } catch (Exception e) {
            System.out.println("Error al guardar la persona: " + e.getMessage());
            return null; // O manejar el error de otra manera
        }

        if (personadEntity.getActivo() == null) {
            personadEntity.setActivo(true); // Asignar valor por defecto si no se especifica
        }
        PersonadEntity personadEntitySaved =  personadRepository.save(personadEntity);

        PersonadResponse personadResponse = new PersonadResponse(personadEntitySaved.getId(),
                                                                 personadEntitySaved.getNombre(),
                                                                 personadEntitySaved.getEdad(),
                                                                 personadEntitySaved.getIdDepartamento().getId(),
                                                                 personadEntitySaved.getIdDepartamento().getPrecio()
                                                                 );

        return personadResponse; // Reemplazar con la implementación real
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

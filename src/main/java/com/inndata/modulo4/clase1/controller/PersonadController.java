package com.inndata.modulo4.clase1.controller;

import com.inndata.modulo4.clase1.entity.PersonadEntity;
import com.inndata.modulo4.clase1.service.impl.PersonadService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PersonadController {

    @Autowired
    PersonadService personadService;

    // Aquí puedes agregar métodos para manejar las solicitudes HTTP relacionadas con PersonaD
    // Por ejemplo, métodos para leer, crear, actualizar y eliminar personas

    // Ejemplo de método para leer todas las personas

     @GetMapping("/personas")
     public List<PersonadEntity> readAll() {
         return personadService.readAll();
     }

    // Puedes agregar más métodos según sea necesario, como create, update, delete, etc.
    // Asegúrate de que los métodos en PersonadService estén implementados correctamente

    // Ejemplo de método para leer una persona por ID
     @GetMapping("/personas/{id}")
     public Optional<PersonadEntity> readById(@PathVariable Integer id) {
         return personadService.readById(id);
     }

     //Ejemplo de método para crear una nueva persona
     @PostMapping("/personas")
     public PersonadEntity create(@RequestBody PersonadEntity personadEntity) {
         return personadService.create(personadEntity);
     }

     // Ejemplo de método para actualizar una persona existente
     @PutMapping("/personas")
     public PersonadEntity update(@RequestBody PersonadEntity personadEntity) {
         return personadService.update(personadEntity);
     }

     // Ejemplo de método para eliminar una persona por ID
     @DeleteMapping("/personas/{id}")
     public String delete(@PathVariable Integer id) {
         return personadService.deletebyId(id);
     }

    // Ejemplo de método para buscar personas por nombre
    @GetMapping("/personas/nombre")
    public List<PersonadEntity> findByNombreEquals(@PathParam("nombre") String nombre) {
        return personadService.findByNombreEquals(nombre);
    }


}
